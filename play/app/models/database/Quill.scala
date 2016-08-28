package models.database

import com.google.inject.Inject
import io.getquill.{CassandraAsyncContext, CassandraContextConfig, SnakeCase}
import play.api.Configuration

/**
  * Created by henrik on 18-Aug-16.
  */
class Quill @Inject()(configuration: Configuration) {
  val config = configuration.getConfig("ctx").getOrElse(throw new IllegalArgumentException("CTX should be present in configuration file"))
  lazy val ctx = new CassandraAsyncContext[SnakeCase](CassandraContextConfig(config.underlying))

  def getAsync: CassandraAsyncContext[SnakeCase] = {
    ctx
  }
}
