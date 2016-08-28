import sbt.Project.projectToRef

name := "play2-scala-cassandra-lagom-sample"

version := "1.0-SNAPSHOT"
lazy val scalaV = "2.11.8"

lazy val frontend = project("play")
  .settings(
    name := "Frontend",
    scalaVersion := scalaV,
    routesGenerator := InjectedRoutesGenerator,
    resolvers ++= Seq(
      "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Typesafe Ivy Snapshots Repository" at "https://oss.sonatype.org/content/repositories/snapshots/"
    ),
    libraryDependencies ++= Seq(
      cache,
      "com.datastax.cassandra" % "cassandra-driver-core" % "3.1.0",
      "io.getquill" %% "quill-cassandra" % "0.9.1-SNAPSHOT",
      "de.kaufhof" %% "pillar" % "3.2.0"
    )
  )
  .enablePlugins(PlayScala, LagomPlay)

// See https://github.com/FasterXML/jackson-module-parameter-names
lazy val jacksonParameterNamesJavacSettings = Seq(
  javacOptions in compile += "-parameters"
)

def project(id: String) = Project(id, base = file(id))
//  .settings(eclipseSettings: _*)
  .settings(javacOptions in compile ++= Seq("-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation"))
  .settings(jacksonParameterNamesJavacSettings: _*) // applying it to every project even if not strictly needed.

lagomCassandraCleanOnStart in ThisBuild := false

// loads the Play project at sbt startup
onLoad in Global := (Command.process("project play", _: State)) compose (onLoad in Global).value
