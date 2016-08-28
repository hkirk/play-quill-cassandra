-- description: creates song and playlist tables
-- authoredAt: 201403202239
-- up:

CREATE TABLE IF NOT EXISTS playlist (
  id uuid,
  title text,
  album text, 
  artist text,
  song_id uuid,
  PRIMARY KEY (id, title, album, artist)
)

-- down:

DROP TABLE playlist