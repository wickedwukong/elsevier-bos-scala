name := "elsevier-bos-scala"

organization := "elsevier"

version := "0.0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.7.2",
  "org.scalaz" %% "scalaz-core" % "7.2.8",
  "org.specs2" %% "specs2-core" % "3.8.3" % "test"
)


scalacOptions in Test ++= Seq("-Yrangepos")

// Read here for optional dependencies:
// https://etorreborre.github.io/specs2/guide/SPECS2-3.8.3/org.specs2.guide.Installation.html#other-dependencies

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

initialCommands := "import elsevier.elsevierbosscala._"
