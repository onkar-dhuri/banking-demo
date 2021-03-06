name := """banking-demo"""

version := "1.0"

scalaVersion := "2.11.7"

lazy val akkaVersion = "2.4.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)

libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.4.3"

libraryDependencies += "org.jsoup" % "jsoup" % "1.10.2"

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
