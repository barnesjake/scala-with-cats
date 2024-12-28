val scala3Version = "3.6.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-with-cats",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= dependencies

  )

lazy val dependencies: Seq[ModuleID] = Seq(
  "org.typelevel" %% "cats-core" % "2.9.0",
  "org.scalameta" %% "munit"     % "1.0.0" % Test
)