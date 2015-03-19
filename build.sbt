scalaVersion := "2.11.2"


lazy val specRest =
  project.in( file(".") )
    .aggregate(renderer, runner)

lazy val common = project

lazy val renderer = project.dependsOn(common)

lazy val runner = project.dependsOn(common)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")



Revolver.settings


