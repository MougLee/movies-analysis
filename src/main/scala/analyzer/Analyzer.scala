package analyzer


import analyzer.dataProviders._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.{Dataset, SparkSession}

import java.time.YearMonth
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext}

class Analyzer(spark: SparkSession, tmdbDataProvider: TheMovieDatabaseDataProvider, imdbDataProvider: ImdbDataProvider)(implicit val ec: ExecutionContext) {

  import spark.implicits._

  // Which movies (top 5) had the greatest popularity change (i.e., the greatest difference between min and max value) during the given month
  def getTopMoviesWithHighestPopularityDiff(yearMonth: YearMonth, top: Int = 5): Dataset[MoviePopularityChange] = {
    val changesFut = tmdbDataProvider.fetchMovieChangesForMonth(yearMonth)
    val changes = Await.result(changesFut, 5.seconds)
    val resultDF = spark.read.json(changes.toDS())
    val changedMovieIds = resultDF.select(explode($"results")).select("col.id").distinct()

    val dailyExports = tmdbDataProvider.fetchDailyExportsForMonth(YearMonth.of(2021, 4))
    val changedMovies = dailyExports.join(changedMovieIds, "id") // only movies that were changed are interesting -> reduce number of items to

    val mostChanged = changedMovies.groupBy("id")
      .agg(
        min("popularity").as("min_pop"),
        max("popularity").as("max_pop"),
      )
      .withColumn("popularity_diff", col("max_pop") - col("min_pop"))
      .orderBy(col("popularity_diff").desc)
      .limit(top)

    val top5 = dailyExports.join(mostChanged, "id")
    val maxResults = top5.as("r1").join(top5.as("r2"), $"r1.popularity" === $"r2.max_pop" and $"r1.id" === $"r2.id", "left_semi")
      .withColumnRenamed("date", "max_pop_date")
      .select("id", "original_title", "popularity_diff", "max_pop", "max_pop_date")

    val minResults = top5.as("r1").join(top5.as("r2"), $"r1.popularity" === $"r2.min_pop" and $"r1.id" === $"r2.id", "left_semi")
      .withColumnRenamed("date", "min_pop_date")
      .withColumnRenamed("date", "min_pop_date")
      .select("id", "min_pop", "min_pop_date")

    maxResults.join(minResults, "id").as[MoviePopularityChange]
  }

  // From the top 10 movies available on IMDb with more than 400k votes, select the actor with the highest popularity rating.
  def mostPopularActor(top: Int = 10): Dataset[MostPopularActorAndMovie] = {
    val imdbTitles = imdbDataProvider.fetchTitles.filter(_.titleType == "movie")

    val imdbRatings = imdbDataProvider.fetchRatings.filter(_.numVotes.toLong > 400000)
    val titlePrincipals = imdbDataProvider.fetchPrincipals
    val actors = imdbDataProvider.fetchActors

    val imdbTitlesWithRating = imdbTitles.join(imdbRatings, "tconst")
      .orderBy(col("averageRating").cast(IntegerType).desc)
      .as[ImdbTitlesWithRating]
      .limit(top)

    val candidatesForMostPopularActor = imdbTitlesWithRating
      .join(titlePrincipals, "tconst")
      .filter(col("category") === "actor")
      .join(actors, "nconst")
      .select("nconst", "tconst", "primaryTitle", "averageRating", "primaryName")
      .orderBy("averageRating")

    val actorIds = candidatesForMostPopularActor.select(col("nconst")).collect().map(_.getString(0)).toSet
    val tmdbActors = tmdbDataProvider.fetchActors(actorIds)

    val mostPopularCandidate = tmdbActors.toSeq.toDS().orderBy(col("popularity").desc).limit(1)

    candidatesForMostPopularActor.join(mostPopularCandidate, "nconst")
      .select(
        col("nconst").as("actor_id"),
        col("primaryName").as("actor_name"),
        col("popularity").as("actor_popularity"),
        col("primaryTitle").as("movie_title"),
        col("averageRating").as("movie_rating")
      )
      .orderBy(col("averageRating").cast(IntegerType).desc)
      .limit(1)
      .as[MostPopularActorAndMovie]
  }
}

case class ImdbTitlesWithRating(tconst: String, averageRating: String, numVotes: String)

case class MoviePopularityChange(id: Long, original_title: String, popularity_diff: Double, max_pop: Double, max_pop_date: String, min_pop: Double, min_pop_date: String)

case class MostPopularActorAndMovie(actor_id: String, actor_name: String, actor_popularity: String, movie_title: String, movie_rating: String)
