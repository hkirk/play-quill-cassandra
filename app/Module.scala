import com.google.inject.AbstractModule
import pillar.CassandraInitializer

class Module extends AbstractModule {
  def configure() = {
    bind(classOf[CassandraInitializer]).asEagerSingleton()
  }
}