name := "shapeless-play-json"

version := "0.2-SNAPSHOT"

scalaVersion := Versions.scala

lazy val extraScalacOptions: Seq[String] =
  CrossVersion.partialVersion(Versions.scala) match {
    case Some((2, y)) if y == 11 => Seq("-Xexperimental")
    case _ => Seq()
  }

scalacOptions ++= extraScalacOptions

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2",
  "com.typesafe.play" %% "play-json" % "2.6.0-M1",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val shapeless_play_json = project in file(".")