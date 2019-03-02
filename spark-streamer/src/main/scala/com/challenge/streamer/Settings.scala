package com.challenge.streamer
import com.typesafe.config.Config

class Settings(conf: Config) {
  val kafkaBrokers = conf.getString("kafka.brokers")
  val kafkaTopic = conf.getString("kafka.topic")
  val checkpointLocation = conf.getString("spark.checkpointLocation")
  val outputPath = conf.getString("outputPath")
}
