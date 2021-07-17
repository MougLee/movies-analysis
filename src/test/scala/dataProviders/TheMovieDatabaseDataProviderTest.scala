package dataProviders

import analyzer.SparkSessionTestWrapper
import analyzer.dataProviders.TheMovieDatabaseDataProvider
import com.google.common.util.concurrent.SettableFuture
import org.asynchttpclient.{AsyncHttpClient, BoundRequestBuilder, Response}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

import java.time.YearMonth
import scala.concurrent.ExecutionContext

class TheMovieDatabaseDataProviderTest extends AnyFlatSpec with MockFactory with SparkSessionTestWrapper {

//  "the movie db api call" should "fetch movie changes in a month" in {
//
//    implicit val ec: ExecutionContext = ExecutionContext.Implicits.global
//
//    val httpClientMock = mock[AsyncHttpClient]
//    val requestBuilder = mock[BoundRequestBuilder]
//    val responseMock = mock[Response]
//
//    (responseMock.getResponseBody: () => String).expects().returning("""{"results": [{ "id": 412683, "adult": false }, { "id": 412685, "adult": false }, {"id": 75258, "adult": false}, {"id": 60308, "adult": false}]}""")
//
//    val settableFuture = SettableFuture.create[Response]()
//    settableFuture.set(responseMock)
//
//    (httpClientMock.prepareGet _).expects(*).repeat(30)
//    (requestBuilder.execute[Response] _).expects(*).repeat(30).returning(settableFuture)
//
//    val provider = new TheMovieDatabaseDataProvider(spark, httpClientMock, "")
//    provider.fetchMovieChangesForMonth(YearMonth.of(2021, 4))
//  }

}
