name := "shapeless-play-json"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

scalacOptions += "-Xexperimental"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2",
  "com.typesafe.play" %% "play-json" % "2.5.10",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val shapeless_play_json = project in file(".")