package models.database.repositories

import java.util.UUID

import com.datastax.driver.core.{ResultSet, Row}
import com.datastax.driver.core.utils.UUIDs
import com.google.inject.Inject
import models.database.Quill
import models.domain.Song
import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

class SongsRepository @Inject() (quill: Quill) {
  val ctx = quill.getAsync
  import ctx._

  def getAll(implicit ctxt: ExecutionContext): Future[List[Song]] = {
    val q = quote {
      query[Song]
    }
    ctx.run(q)
  }

  def getById(id: UUID)(implicit ctxt: ExecutionContext): Future[Option[Song]] = {
    val q = quote {
      query[Song].filter(s => s.id == lift(id))
    }
    ctx.run(q).map {
      case head :: tail => Some(head)
      case _ => None
    }
  }

  def insert(title: String, album: String, artist: String)(implicit ctxt: ExecutionContext): Future[UUID] = {
    val song = Song(UUIDs.timeBased, title, album, artist)
    val q = quote (
      query[Song].insert(lift(song))
    )

    def parseRow(row: Row): Option[UUID] = {
      if (row == null)
        None
      else
        try {
          Some(UUID.fromString(row.getString(0)))
        } catch {
          case e: Exception => None
        }
    }

    ctx.run(q) map (_ => song.id)
  }
}

object JsonFormats {

  /**
   * Deserializer for java.util.UUID, from latest play Reads (was added on 2014-03-01 to master,
   * see https://github.com/playframework/playframework/pull/2428)
   */
  private def uuidReader(checkUuuidValidity: Boolean = false): Reads[java.util.UUID] = new Reads[java.util.UUID] {
    import java.util.UUID

    import scala.util.Try
    def check(s: String)(u: UUID): Boolean = (u != null && s == u.toString())
    def parseUuid(s: String): Option[UUID] = {
      val uncheckedUuid = Try(UUID.fromString(s)).toOption

      if (checkUuuidValidity) {
        uncheckedUuid filter check(s)
      } else {
        uncheckedUuid
      }
    }

    def reads(json: JsValue) = json match {
      case JsString(s) => {
        parseUuid(s).map(JsSuccess(_)).getOrElse(JsError(Seq(JsPath() -> Seq(ValidationError("error.expected.uuid")))))
      }
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("error.expected.uuid"))))
    }
  }

  private implicit val uuidReads: Reads[java.util.UUID] = uuidReader()
  private implicit val uuidWrites: Writes[UUID] = Writes { uuid => JsString(uuid.toString) }

  implicit val songFormat: Format[Song] = Json.format[Song]
  implicit val songDataReads = (
    (__ \ 'title).read[String] and
    (__ \ 'album).read[String] and
    (__ \ 'artist).read[String]) tupled
}