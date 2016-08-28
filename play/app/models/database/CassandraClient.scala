package models.database

import java.io.File

import com.datastax.driver.core.{Cluster, Metadata, ResultSetFuture}
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.google.inject.Inject
import de.kaufhof.pillar.{Migrator, Registry}
import pillar.LoggerReporter
import play.api.{Configuration, Logger}

import scala.collection.JavaConversions._

/**
 * Simple cassandra client, following the datastax documentation
 * (http://www.datastax.com/documentation/developer/java-driver/2.0/java-driver/quick_start/qsSimpleClientCreate_t.html).
 */
class CassandraClient @Inject()(configuration: Configuration) {
  private val node = configuration.getString("cassandra.node").getOrElse(throw new IllegalArgumentException("No 'cassandra.node' config found."))
  private val cluster = Cluster.builder().addContactPoint(node).build()
  log(cluster.getMetadata)
  val session = cluster.connect("simplex")

  private def log(metadata: Metadata): Unit = {
    Logger.info(s"Connected to cluster: ${metadata.getClusterName}")
    for (host <- metadata.getAllHosts) {
      Logger.info(s"Datatacenter: ${host.getDatacenter}; Host: ${host.getAddress}; Rack: ${host.getRack}")
    }
  }

  def migrate(dir: File): Unit = {
    val registry = Registry.fromDirectory(dir)
    val migrator = Migrator(registry, new LoggerReporter)
    migrator.initialize(session, "simplex")
    migrator.migrate(session)
  }

  def close() {
    session.close()
    cluster.close()
  }
}
