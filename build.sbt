lazy val root = (project in file(".")).settings(
  scalaVersion := "2.11.8",
  scalacOptions := compilerOptions,
  libraryDependencies := dependencies
)

lazy val compilerOptions = Seq(
  "-unchecked",
  "-deprecation",
  "-target:jvm-1.8",
  "-feature",
  "-language:implicitConversions",
  "-language:higherKinds"
)

lazy val dependencies = {
  lazy val scalazVersion = "7.2.8"
  Seq(
    "org.scalaz" %% "scalaz-core" % scalazVersion,
    "org.scalaz" %% "scalaz-effect" % scalazVersion,
    "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  )
}

