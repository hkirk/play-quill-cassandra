-- description: creates songs and playlists tables
-- authoredAt: 201403202239
-- up:

CREATE KEYSPACE IF NOT EXISTS simplex WITH replication = {'class':'SimpleStrategy', 'replication_factor':3};

-- down:

DROP KEYSPACE simplex
