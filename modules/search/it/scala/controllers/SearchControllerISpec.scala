package controllers

import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.Application
import play.api.http.Status
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.test.Helpers.{await, _}
import play.api.test.{Helpers, Injecting}

class SearchControllerISpec extends PlaySpec with GuiceOneServerPerSuite with Injecting with BeforeAndAfterEach {

  override def fakeApplication(): Application = {
    new GuiceApplicationBuilder()
      .configure("play.http.router" -> "search.Routes").build()
  }

  "search" should {
    "return search results" in {
      val url = s"http://localhost:$port/?query=mi&country=de"
      val response = await(inject[WSClient]
        .url(url)
        .addHttpHeaders(("COUNTRY", "default"))
        .get()
      )

      response.status mustBe Status.OK
      Json.parse(response.body) mustEqual Json.obj("results" -> Seq("Milk", "Milky Bar", "Milk"))
    }
  }
}
