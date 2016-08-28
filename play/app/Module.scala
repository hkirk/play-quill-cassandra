import com.google.inject.AbstractModule
import io.getquill.{CassandraAsyncContext, SnakeCase}
import pillar.CassandraInitializer

class Module extends AbstractModule {
  def configure() = {
    bind(classOf[CassandraInitializer]).asEagerSingleton()

//    bind(classOf[CassandraAsyncContext[SnakeCase]]).toInstance {
//      import com.typesafe.config.ConfigFactory
//      import io.getquill.CassandraContextConfig
//
//      val configuration = ConfigFactory.load
//      val config = configuration.getConfig("ctx")//.getOrElse(throw new IllegalArgumentException("CTX should be present in configuration file"))
//      new CassandraAsyncContext[SnakeCase](CassandraContextConfig(config))//underlying))
//    }
  }
}