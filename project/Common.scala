import sbt._
import Keys._


object Common {

  val akkaV = "2.3.6"
  val sprayV = "1.3.2"

  val libraryDependencies = Seq(
      "io.spray" %% "spray-client" % sprayV,
      "io.spray" %% "spray-can" % sprayV,
      "io.spray" %% "spray-routing" % sprayV,
      "io.spray" %%  "spray-json" % "1.3.1",
      "io.spray" %% "spray-testkit" % sprayV % "test",
      "com.typesafe.akka" %% "akka-actor" % akkaV,
      "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
      "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
    )


}
