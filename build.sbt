ThisBuild / scalaVersion     := "3.3.7"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.adamnfish"
ThisBuild / organizationName := "discs"

lazy val root = (project in file("."))
  .settings(
    name := "discs",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
