-- description: populates song and playlist
-- authoredAt: 201403202240
-- up:

INSERT INTO song (id, title, album, artist, tags)
  VALUES (
    756716f7-2e54-4715-9f00-91dcbea6cf50,
    'La Petite Tonkinoise',
    'Bye Bye Blackbird',
    'Joséphine Baker',
    {'jazz', '2013'}
  )

-- down:

TRUNCATE song