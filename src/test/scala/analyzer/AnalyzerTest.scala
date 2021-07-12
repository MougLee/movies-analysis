package analyzer

import analyzer.dataProviders.{ImdbDataProvider, TheMovieDatabaseDataProvider}
import dataGenerators.{ImdbDataGenerator, TheMovieDatabaseDataGenerator}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers._

import scala.concurrent.ExecutionContext

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

    implicit val ec: ExecutionContext = ExecutionContext.Implicits.global
    val analyzer = new Analyzer(spark, tmdbDataProviderMock, imdbDataProviderMock)

    analyzer.mostPopularActor().collect().head should be(expected)
  }


  // TODO: add test for movies with highest popularity change
}
