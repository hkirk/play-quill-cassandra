package pillar

import java.io.File

import com.google.inject.Inject
import models.database.CassandraClient
import play.api.Logger

/**
  * Initialize and migrate the dataStore configured in application.conf.
  */
class CassandraInitializer @Inject()(client: CassandraClient) {
    val file = new File(getClass.getResource("/pillar/migrations").toURI)
    client.migrate(file)
}
