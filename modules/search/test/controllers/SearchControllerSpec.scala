package controllers

import org.scalatest.BeforeAndAfterEach
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.Configuration
import play.api.test.{FakeRequest, Helpers}
import org.mockito.Mockito.{reset, when}
import play.api.libs.json.Json
import play.api.test.Helpers._

class SearchControllerSpec extends PlaySpec with MockitoSugar with BeforeAndAfterEach {
  private val mockConfiguration: Configuration = mock[Configuration]
  val searchController = new SearchController(Helpers.stubControllerComponents(), mockConfiguration)

  override protected def beforeEach(): Unit = {
    when(mockConfiguration.get[Seq[String]]("products")).thenReturn(Seq("Milk", "Choclate", "Milk", "Milky bar"))
    when(mockConfiguration.getOptional[Boolean]("de.search.deDuplicate")).thenReturn(Some(true))
  }

  override protected def afterEach(): Unit = {
    reset(mockConfiguration)
  }

  "search" should {
    "deduplicate search results" in {
      val result = searchController.search("mi", "de")(FakeRequest())

      contentAsJson(result) mustBe Json.obj("results" -> Seq("Milk", "Milky bar"))
    }

    "not deduplicate search results" in {
      when(mockConfiguration.getOptional[Boolean]("de.search.deDuplicate")).thenReturn(Some(false))

      val result = searchController.search("mi", "de")(FakeRequest())

      contentAsJson(result) mustBe Json.obj("results" -> Seq("Milk", "Milk", "Milky bar"))
    }
  }
}
