lazy val root = (project in file(".")).settings(
  scalaVersion := "2.11.7",
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
lazy val scalazVersion = "7.2.2"
Seq(
    "org.scalaz" %% "scalaz-core" % scalazVersion,
    "org.scalaz" %% "scalaz-effect" % scalazVersion
  )
}

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.7.1")
