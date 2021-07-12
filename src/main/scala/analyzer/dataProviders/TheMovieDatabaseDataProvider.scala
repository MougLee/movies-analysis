package analyzer.dataProviders

import org.apache.spark.SparkContext
import org.apache.spark.sql.functions.{input_file_name, regexp_replace}
import org.apache.spark.sql.{Dataset, SparkSession}
import org.asynchttpclient.AsyncHttpClient
import upickle.default._

import java.io.File
import java.net.URL
import java.time.format.DateTimeFormatter
import java.time.{LocalDate, YearMonth}
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.language.postfixOps
import scala.sys.process._

class TheMovieDatabaseDataProvider(spark: SparkSession, asyncHttpClient: AsyncHttpClient, apiKey: String)(implicit val ec: ExecutionContext) {

  val MovieDbApiEndpoint = "https://api.themoviedb.org/3"

  def fetchMovieChangesForMonth(yearMonth: YearMonth): Future[Seq[String]] = {
    // there is a bug they have since 2019 -> pagination doesn't work https://www.themoviedb.org/talk/5c740e97c3a3685a40197326
    // I will make my life easier and just request all the data with one call
    // per day to simulate multiple requests needed, without checking if I need to do
    // multiple calls per day to fetch all the pages.
    val daysInMonth = generateDaysForMonth(yearMonth)
    val futures = for ((start, end) <- daysInMonth) yield fetchMovieChanges(start, end)
    Future.sequence(futures)
  }

  private def fetchMovieChanges(startDate: LocalDate, endDate: LocalDate)(implicit ec: ExecutionContext): Future[String] = {
    val p = Promise[String]

    val movieChangesApiEndpoint = MovieDbApiEndpoint + "/movie/changes"
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val listenableFut = asyncHttpClient.prepareGet(movieChangesApiEndpoint)
      .addQueryParam("api_key", apiKey)
      .addQueryParam("start_date", startDate.format(formatter))
      .addQueryParam("end_date", endDate.format(formatter))
      .execute()

    listenableFut.addListener(() => p.success(listenableFut.get().getResponseBody), null)

    p.future
  }

  def fetchDailyExportsForMonth(yearMonth: YearMonth): Dataset[MovieDailyChange] = {
    import spark.implicits._

    val sparkVolumePath = "/opt/spark-data/"
    val moviesDailyExportApiEndpoint = "http://files.tmdb.org/p/exports/"
    val fileNames = generateDaysForMonth(yearMonth).map { case (start, _) =>
      s"movie_ids_${start.format(DateTimeFormatter.ofPattern("MM_dd_yyyy"))}.json.gz"
    }

    fileNames.foreach { name =>
      val file = new File(sparkVolumePath + name)
      if(!file.exists()) new URL(moviesDailyExportApiEndpoint + name) #> new File(sparkVolumePath + name) !!
    }

    val paths = fileNames.map(fileName => s"file://$sparkVolumePath$fileName")
    val filenameToDateRegex = s"""(file://${sparkVolumePath}movie_ids_)(\\d{2}_\\d{2}_\\d{4})(\\.json\\.gz)"""
    spark.read
      .json(paths: _*)
      .withColumn("date", regexp_replace(input_file_name(), filenameToDateRegex, "$2"))
      .as[MovieDailyChange]
  }

  private def generateDaysForMonth(yearMonth: YearMonth): IndexedSeq[(LocalDate, LocalDate)] = {
    (1 to yearMonth.lengthOfMonth).map { day =>
      val startDate = LocalDate.of(yearMonth.getYear, yearMonth.getMonth, day)
      val endDate = startDate.plusDays(1)

      (startDate, endDate)
    }
  }

  def fetchActors(actorIds: Set[String]): Set[TmdbActor] = {
    implicit val tmdbActorsList: ReadWriter[TmdbActorResult] = macroRW
    implicit val tmdbActorResult: ReadWriter[TmdbActorResults] = macroRW
    val tmdbActorFutures = actorIds.map { actorImdbId =>
      fetchActor(actorImdbId).map { jsonResult =>
        val result = upickle.default.read[TmdbActorResults](jsonResult)
        result.person_results.map(r => TmdbActor(actorImdbId, r.id, r.popularity)).headOption
      }
    }

    Await.result(Future.sequence(tmdbActorFutures), 5.seconds).flatten
  }

  private def fetchActor(actorId: String): Future[String] = {
    val p = Promise[String]

    val MovieChangesApiEndpoint = s"$MovieDbApiEndpoint/find/$actorId"
    val listenableFut = asyncHttpClient.prepareGet(MovieChangesApiEndpoint)
      .addQueryParam("api_key", apiKey)
      .addQueryParam("language", "en-US")
      .addQueryParam("external_source", "imdb_id")
      .execute()

    listenableFut.addListener(() => p.success(listenableFut.get().getResponseBody), null)

    p.future
  }
}

case class MovieDailyChange(id: Long, original_title: String, popularity: Double, date: String)

case class TmdbActor(nconst: String, id: Int, popularity: Double)

case class TmdbActorResult(id: Int, popularity: Double)

case class TmdbActorResults(person_results: Seq[TmdbActorResult])
