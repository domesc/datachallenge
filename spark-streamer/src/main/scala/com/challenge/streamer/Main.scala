package com.challenge.streamer
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

object Main extends App {
  val conf = ConfigFactory.load()
  val settings = new Settings(conf)

  val spark = SparkSession.builder()
    .appName("streamer")
    .master("local[*]")
    .config("spark.cleaner.referenceTracking.cleanCheckpoints", "true")
    .getOrCreate()

  val processor = new StreamProcessor(spark, settings)

  processor.process()


  /*sys.addShutdownHook{
    spark.stop()
  }*/
}
