package controllers

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc._
import utils.ConfigurationHelper.CountryConfiguration

@Singleton
class SearchController @Inject()(cc: ControllerComponents, configuration: Configuration) extends AbstractController(cc) {

  def search(query: String, country: String): Action[AnyContent] = Action {
    val products = configuration.get[Seq[String]]("products").filter(_.toLowerCase.contains(query))
    val deDuplicate = configuration.getForCountry[Boolean](country, "search.deDuplicate", true)
    val result = if(deDuplicate) products.distinct else products
    Ok(Json.obj("results" -> result))
  }

}
