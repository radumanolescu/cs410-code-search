ThisBuild / scalaVersion := "2.12.6"
ThisBuild / organization := "edu.illinois.mcsds.cs410"

lazy val hello = (project in file("."))
  .settings(
    name := "data_proc"
    , libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.1"
    , libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
    , libraryDependencies += "org.jsoup" % "jsoup" % "1.11.3"
      // https://mvnrepository.com/artifact/de.rototor.snuggletex/snuggletex-core
    , libraryDependencies += "de.rototor.snuggletex" % "snuggletex-core" % "1.3.0"
  )
