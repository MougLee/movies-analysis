package analyzer

import analyzer.dataProviders.{ImdbDataProvider, TheMovieDatabaseDataProvider}
import dataGenerators.{ImdbDataGenerator, TheMovieDatabaseDataGenerator}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers._

import java.time.YearMonth
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

class AnalyzerTest extends AnyFlatSpec with MockFactory with SparkSessionTestWrapper {

  import spark.implicits._

  "mostPopularActors" should "find most popular actor" in {
    val imdbDataProviderMock = mock[ImdbDataProvider]
    (imdbDataProviderMock.fetchTitles _).expects().returns(ImdbDataGenerator.titles.toDS)
    (imdbDataProviderMock.fetchRatings _).expects().returns(ImdbDataGenerator.ratings.toDS)
    (imdbDataProviderMock.fetchPrincipals _).expects().returns(ImdbDataGenerator.principals.toDS)
    (imdbDataProviderMock.fetchActors _).expects().returns(ImdbDataGenerator.actors.toDS)

    val tmdbDataProviderMock = mock[TheMovieDatabaseDataProvider]
    (tmdbDataProviderMock.fetchActors _).expects(*).returns(TheMovieDatabaseDataGenerator.actors.toSet)

    val expected = MostPopularActorAndMovie("nm0005458", "Jason Statham", "45.565", "Snatch", "8.3")
    val analyzer = new Analyzer(spark, tmdbDataProviderMock, imdbDataProviderMock)

    analyzer.mostPopularActor().collect().head should be(expected)
  }

  "movies" should "find movies with highest popularity diff" in {
    val tmdbDataProviderMock = mock[TheMovieDatabaseDataProvider]
    (tmdbDataProviderMock.fetchDailyExportsForMonth _).expects(*).returns(TheMovieDatabaseDataGenerator.movieDailyExports.toDS())
    (tmdbDataProviderMock.fetchMovieChangesForMonth _).expects(*).returns(Future {
      TheMovieDatabaseDataGenerator.changes
    })

    val analyzer = new Analyzer(spark, tmdbDataProviderMock, mock[ImdbDataProvider])

    val expected = Array(
      MoviePopularityChange(791373, "Zack Snyder's Justice League", 8.8, 9.9, "04_02_2021", 1.1, "04_30_2021"),
      MoviePopularityChange(399566, "Godzilla vs. Kong", 8.700000000000001, 9.9, "04_24_2021", 1.2, "04_04_2021"),
      MoviePopularityChange(460465, "Mortal Kombat", 8.5, 9.8, "04_28_2021", 1.3, "04_18_2021"),
      MoviePopularityChange(412656, "Chaos Walking", 8.299999999999999, 9.7, "04_13_2021", 1.4, "04_11_2021"),
      MoviePopularityChange(615457, "Nobody", 8.2, 9.6, "04_26_2021", 1.4, "04_06_2021"),
    )

    analyzer.getTopMoviesWithHighestPopularityDiff(YearMonth.of(2021, 4)).collect should contain theSameElementsAs expected
  }
}
