package controllers

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc._
import utils.ConfigurationHelper.CountryConfiguration

@Singleton
class PaymentController @Inject()(cc: ControllerComponents, configuration: Configuration) extends AbstractController(cc) {

  def get(country: String) = Action {
    Ok(Json.obj(
      "id" -> "someId",
      "details" -> Json.obj(
        "status" -> "captured",
        "invoice_id" -> "someId",
        "amount" -> 5000,
        "currency" -> configuration.getForCountry[String](country, "currency", false)
      )
    )
    )
  }

}
