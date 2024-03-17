name := "WordCounter"

version := "0.1"

scalaVersion := "2.13.13"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.slf4j" % "slf4j-api" % "1.7.26",
  "org.slf4j" % "slf4j-simple" % "1.7.26",
  "org.scalatest" %% "scalatest" % "3.2.18" % "test"
)
