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
import play.api.test.Injecting

class PaymentControllerISpec extends PlaySpec with GuiceOneServerPerSuite with Injecting with BeforeAndAfterEach {
  override def fakeApplication(): Application = {
    new GuiceApplicationBuilder()
      .configure("play.http.router" -> "payment.Routes").build()
  }

  "payments" should {
    "return all payments for the given country" in {
      val url = s"http://localhost:$port/?country=de"

      val expectedJson = Json.obj(
        "id" -> "someId",
        "details" -> Json.obj(
          "status" -> "captured",
          "invoice_id" -> "someId",
          "amount" -> 5000,
          "currency" -> "EUR"
        )
      )

      val response = await(inject[WSClient].url(url).get())

      response.status mustBe Status.OK

      Json.parse(response.body) mustEqual expectedJson
    }
  }
}
