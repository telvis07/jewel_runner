name := "jewel_runner"

version := "1.0"

scalaVersion := "2.11.6"


resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "edu.cmu.sphinx" % "sphinx4-core" % "1.0-SNAPSHOT"

libraryDependencies += "edu.cmu.sphinx" % "sphinx4-data" % "1.0-SNAPSHOT"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.4.4"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"

libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.4.4"