val sparkVersion = "2.4.0"

lazy val commonSettings = Seq(
  organization := "com.github.domesc",
  version := "0.1",
  scalaVersion := "2.11.12",
  test in assembly := {}
)

lazy val commonLibs = Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "org.slf4j" % "slf4j-simple" % "1.6.4",
  "com.typesafe" % "config" % "1.3.2"
)

lazy val kafkaLibs = Seq(
  "org.apache.kafka" %% "kafka" % "2.1.1"
)

lazy val sparkLibs = Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion
)

lazy val jsonOverrides = Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.6.7",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.6.7"
)

lazy val common = (project in file("common"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq("com.typesafe.play" %% "play-json" % "2.6.10"))

lazy val `common-spark` = (project in file("common-spark"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq("org.apache.spark" %% "spark-sql" % sparkVersion))

lazy val `kafka-producer` = (project in file("kafka-producer"))
  .dependsOn(common)
  .settings(commonSettings: _*)
  .settings(assemblyJarName in assembly := "kafka.jar")
  .settings(libraryDependencies ++= kafkaLibs ++ commonLibs)

lazy val `spark-streamer` = (project in file("spark-streamer"))
  .dependsOn(common, `common-spark`)
  .settings(commonSettings: _*)
  .settings(assemblyJarName in assembly := "streamer.jar")
  .settings(libraryDependencies ++= commonLibs ++ sparkLibs)
  .settings(dependencyOverrides ++= jsonOverrides)

lazy val `query-service` = (project in file("query-service"))
  .dependsOn(common, `common-spark`)
  .settings(commonSettings: _*)
  .settings(assemblyJarName in assembly := "query-service.jar")
  .settings(libraryDependencies ++= commonLibs ++ Seq(
    "org.apache.spark" %% "spark-sql" % sparkVersion,
    "org.vegas-viz" %% "vegas" % "0.3.11",
    "org.vegas-viz" %% "vegas-spark" % "0.3.11"
  ))
  .settings(dependencyOverrides ++= jsonOverrides)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(name := "datachallenge")
  .aggregate(`kafka-producer`)
