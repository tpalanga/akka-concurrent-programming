scalaVersion := "2.12.6"

name := "akka-concurrent-programming"

val akkaVersion = "2.5.14"
val scalaTestVersion = "3.0.5"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,

  "org.scalactic" %% "scalactic" % scalaTestVersion % "test",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)
