ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.adamnfish"
ThisBuild / organizationName := "discs"

lazy val root = (project in file("."))
  .settings(
    name := "discs",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % Test
  )
