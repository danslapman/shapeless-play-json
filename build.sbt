name := "shapeless-play-json"

version := "0.2-SNAPSHOT"

scalaVersion := "2.11.8"

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, y)) if y == 11 => Seq("-Xexperimental")
    case Some((2, y)) if y == 12 => Seq("-Yrecursion", "1")
    case _ => Seq.empty[String]
  }
}

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2",
  "com.typesafe.play" %% "play-json" % "2.6.0-M1",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val shapeless_play_json = project in file(".")