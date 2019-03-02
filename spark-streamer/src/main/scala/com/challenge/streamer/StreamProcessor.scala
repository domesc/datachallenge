package com.challenge.streamer
import com.challenge.commonspark.Schemas
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.OutputMode

class StreamProcessor(spark: SparkSession, settings: Settings) {

  def process(): Unit = {
    import spark.implicits._

    val inputDf = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", settings.kafkaBrokers)
      .option("subscribe", settings.kafkaTopic)
      .option("failOnDataLoss", false)
      .load()

    val eventDf = inputDf
      .selectExpr("CAST(value AS STRING)")
      .select(from_json($"value", Schemas.rawEventSchema).as("event"))
      .selectExpr("event.event_id", "event.region", "event.area", "event.market", "event.ipcode", "event.quantity", "event.timestamp")
      .withColumn("date", from_unixtime($"timestamp", "yyyyMMdd"))

    val kafkaOutput = eventDf
      .writeStream
      .partitionBy("date", "region", "area", "market")
      .format("parquet")
      .option("checkpointLocation", settings.checkpointLocation)
      .option("path", settings.outputPath)
      .outputMode(OutputMode.Append)
      .start()

    kafkaOutput.awaitTermination()

  }

}
