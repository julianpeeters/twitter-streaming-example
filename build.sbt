name := "twitter-streaming-example"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.4"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.7.2" % "test"

cancelable in Global := true
