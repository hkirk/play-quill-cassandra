package controllers

import java.util.UUID

import com.google.inject.Inject
import models.database.repositories.SongsRepository
import play.api.libs.json.{JsError, Json}
import play.api.mvc._

import scala.concurrent.Future

class Application @Inject()(songsRepo: SongsRepository) extends Controller {

  import models.database.repositories.JsonFormats._
  import play.api.libs.concurrent.Execution.Implicits.defaultContext

  def index = Action.async {
    songsRepo.getAll.map(songs => Ok(Json.toJson(songs)))
  }

  def createSong = Action.async(parse.json) { implicit request =>
    // Json Format defined in models.JsonFormats.songDataReads
    request.body.validate[(String, String, String)].map {
      case (title, album, artist) => {
        songsRepo.insert(title, album, artist).map( id =>
          Created.withHeaders("Location" -> routes.Application.songById(id.toString).absoluteURL(secure = false))
        )
      }
    }.recoverTotal {
      e => Future.successful(BadRequest("Detected error:" + JsError.toFlatForm(e)))
    }
  }

  def songById(id: String) = Action.async {
    songsRepo.getById(UUID.fromString(id)).map(song => Ok(Json.toJson(song)))
  }

}