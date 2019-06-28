package controllers

import org.mockito.Mockito
import org.scalatest.BeforeAndAfterEach
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.Configuration
import play.api.libs.json.Json
import play.api.test.{FakeRequest, Helpers}
import play.api.test.Helpers._

class PaymentControllerSpec extends PlaySpec  with MockitoSugar with BeforeAndAfterEach {

  "get" should {
    "return all payments for the given country" in {
      val configuration = mock[Configuration]
      val paymentController = new PaymentController(Helpers.stubControllerComponents(), configuration)

      Mockito.when(configuration.getOptional[String]("de.currency")).thenReturn(Some("EUR"))

      val result = paymentController.get("de")(FakeRequest())

      Helpers.contentAsJson(result)mustBe Json.obj(
        "id" -> "someId",
        "details" -> Json.obj(
          "status" -> "captured",
          "invoice_id" -> "someId",
          "amount" -> 5000,
          "currency" -> "EUR"
      ))
    }
  }
}
