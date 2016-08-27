name := "play2-scala-cassandra-sample"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  cache,
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.1.0",
  "io.getquill" %% "quill-cassandra" % "0.9.1-SNAPSHOT",
  //https://github.com/Galeria-Kaufhof/pillar
  "de.kaufhof" %% "pillar" % "3.2.0"
)

// The Typesafe repository
resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Ivy Snapshots Repository" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

routesGenerator := InjectedRoutesGenerator
