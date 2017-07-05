name := "shapeless-play-json"

version := "0.2-SNAPSHOT"

scalaVersion := "2.12.2"

crossScalaVersions := Seq("2.11.11", "2.12.2")

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, y)) if y == 11 => Seq("-Xexperimental")
    case Some((2, y)) if y == 12 => Seq("-Yrecursion", "1")
    case _ => Seq.empty[String]
  }
}

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.6.1",
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val shapeless_play_json = project in file(".")