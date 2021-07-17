package dataProviders

import analyzer.SparkSessionTestWrapper
import analyzer.dataProviders.{ImdbDataProvider, ImdbTitles}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class ImdbDataProviderTest extends AnyFlatSpec with MockFactory with SparkSessionTestWrapper {

  "data provider" should "read titles data from s3" in {
    val provider = new ImdbDataProvider(spark, "s3a://moviesanalysistestdata/")

    val expected = Array(
      ImdbTitles("tt0000001", "short", "Carmencita"),
      ImdbTitles("tt0011397", "short", "Let 'er Go"),
      ImdbTitles("tt0011398", "movie", "Let's Be Fashionable"),
      ImdbTitles("tt0011399", "movie", "El leÃ³n"),
      ImdbTitles("tt0011400", "movie", "Liebestaumel"),
      ImdbTitles("tt0011401", "movie", "The Life of the Party"),
      ImdbTitles("tt0011402", "movie", "Life's Twist"),
      ImdbTitles("tt0011403", "movie", "Lifting Shadows"),
      ImdbTitles("tt0011404", "movie", "Little Dorrit"),
      ImdbTitles("tt0011405", "movie", "The Little Grey Mouse"),
      ImdbTitles("tt0011406", "movie", "Little Miss Rebellion"),
      ImdbTitles("tt0011407", "movie", "The Little Wanderer"),
      ImdbTitles("tt0011408", "movie", "La llaga"),
      ImdbTitles("tt0011409", "movie", "Lo, die Kokette"),
      ImdbTitles("tt0011410", "movie", "Locked Lips")
    )

    provider.fetchTitles.sort("tconst").collect should contain theSameElementsAs expected
  }

}
