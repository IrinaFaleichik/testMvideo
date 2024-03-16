ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "test"
  )

libraryDependencies += "dev.zio" %% "zio-interop-cats" % "3.1.1.0"
