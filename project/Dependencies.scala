import sbt._

object Version {
  val akka         = "2.3.5"
  val logback      = "1.1.2"
  val scala        = "2.11.1"
  val scalaParsers = "1.0.1"
  val scalaTest    = "2.2.0"


  val httpCore     = "0.4"
  val akkaStream   = "0.4"
}

object Library {
  val akkaActor       = "com.typesafe.akka"      %% "akka-actor"                    % Version.akka
  val akkaCluster     = "com.typesafe.akka"      %% "akka-cluster"                  % Version.akka
  val akkaContrib     = "com.typesafe.akka"      %% "akka-contrib"                  % Version.akka
  val akkaPersistence = "com.typesafe.akka"      %% "akka-persistence-experimental" % Version.akka
  val akkaSlf4j       = "com.typesafe.akka"      %% "akka-slf4j"                    % Version.akka
  val akkaTestkit     = "com.typesafe.akka"      %% "akka-testkit"                  % Version.akka
  val logbackClassic  = "ch.qos.logback"         %  "logback-classic"               % Version.logback
  val scalaParsers    = "org.scala-lang.modules" %% "scala-parser-combinators"      % Version.scalaParsers
  val scalaTest       = "org.scalatest"          %% "scalatest"                     % Version.scalaTest

  val httpCore        = "com.typesafe.akka"      %% "akka-http-core-experimental"   % Version.httpCore
  val akkaStream      = "com.typesafe.akka"      %% "akka-stream-experimental"      % Version.akkaStream
}

object Dependencies {

  import Library._

  val restapi = List(
    akkaCluster,
    akkaContrib,
    akkaPersistence,
    akkaSlf4j,
    httpCore,
    akkaStream,
    logbackClassic,
    scalaParsers,
    akkaTestkit % "test",
    scalaTest   % "test"
  )
}