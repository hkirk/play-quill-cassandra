-- description: creates song and playlist tables
-- authoredAt: 201403202239
-- up:

CREATE TABLE IF NOT EXISTS song (
  id uuid PRIMARY KEY,
  title text,
  album text,
  artist text,
  tags set<text>,
  data blob
)

-- down:

DROP TABLE song
