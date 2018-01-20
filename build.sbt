name := "shapeless-play-json"

version := "0.7"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, y)) if y == 11 => Seq("-Xexperimental")
    case Some((2, y)) if y == 13 => Seq("-Yrecursion", "1")
    case _ => Seq.empty[String]
  }
}

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.6.8",
  "com.chuusai" %% "shapeless" % "2.3.3",
  "org.scalatest" %% "scalatest" % "3.0.4" % Test
)

lazy val shapeless_play_json = project in file(".")

licenses += ("WTFPL", url("http://www.wtfpl.net"))

bintrayOrganization := Some("danslapman")

bintrayReleaseOnPublish in ThisBuild := false