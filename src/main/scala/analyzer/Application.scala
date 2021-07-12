package analyzer

import analyzer.dataProviders.{ImdbDataProvider, TheMovieDatabaseDataProvider}
import org.apache.spark.sql.SparkSession
import org.asynchttpclient.{AsyncHttpClient, Dsl}

import java.time.YearMonth
import scala.concurrent.ExecutionContext


object Application extends App {

  // TODO: add more tests, add user to docker
  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global

  val spark = SparkSession.builder()
    .appName("Imdb Analyzer")
    .getOrCreate()

  val asyncHttpClient: AsyncHttpClient = Dsl.asyncHttpClient(Dsl.config().setConnectTimeout(500))
  val analyzer = new Analyzer(spark, new TheMovieDatabaseDataProvider(spark, asyncHttpClient: AsyncHttpClient, sys.env.getOrElse("THE_MOVIE_DB_API_KEY", "")), new ImdbDataProvider(spark))

  analyzer.getTopMoviesWithHighestPopularityDiff(YearMonth.of(2021, 4)).show()

  analyzer.mostPopularActor().show()

  asyncHttpClient.close()
}

