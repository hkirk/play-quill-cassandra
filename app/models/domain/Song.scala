package models.domain

import java.util.UUID

/**
  * Created by henrik on 22-Aug-16.
  */
case class Song(id: UUID, title: String, album: String, artist: String)
