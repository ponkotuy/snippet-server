name := """snippet-server"""
organization := "com.ponkotuy"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

val doobieVersion = "0.5.3"

libraryDependencies ++= Seq(
  jdbc,
  guice,
  "org.postgresql" % "postgresql" % "42.2.2",
  "org.tpolecat" %% "doobie-core"  % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion
)
