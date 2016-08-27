package pillar

import java.util.Date

import com.datastax.driver.core.Session
import de.kaufhof.pillar.{Migration, ReplicationStrategy, Reporter}
import play.api.Logger

class LoggerReporter extends Reporter {

  def initializing(session: Session, keyspace: String, replicationStrategy: ReplicationStrategy) {
    Logger.info(s"Initializing ${keyspace} data store")
  }

  def migrating(session: Session, dateRestriction: Option[Date]) {
    Logger.info(s"Migrating ${session} data store")
  }

  def applying(migration: Migration) {
    Logger.info(s"Applying ${migration.authoredAt.getTime}: ${migration.description}")
  }

  def reversing(migration: Migration) {
    Logger.info(s"Reversing ${migration.authoredAt.getTime}: ${migration.description}")
  }

  def destroying(session: Session, keyspace: String) {
    Logger.info(s"Destroying ${session} data store")
  }
}