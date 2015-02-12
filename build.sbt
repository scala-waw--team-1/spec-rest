name := "spec-rest"

scalaVersion := "2.10.4"

val SPRAY_VERSION = "1.3.1"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.2.3",
"io.spray" % "spray-can" % SPRAY_VERSION,
"io.spray" % "spray-routing" % SPRAY_VERSION,
"io.spray" % "spray-client" % SPRAY_VERSION,
"io.spray" % "spray-http" % SPRAY_VERSION,
"io.spray" % "spray-httpx" % SPRAY_VERSION,
"io.spray" %% "spray-json" % SPRAY_VERSION,
"io.spray" % "spray-util" % SPRAY_VERSION,
"org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)