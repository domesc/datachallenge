package com.challenge.commonspark
import org.apache.spark.sql.types.{DataTypes, StructType}

object Schemas {

  lazy val rawEventSchema = new StructType()
    .add("event_id", DataTypes.StringType)
    .add("region", DataTypes.StringType)
    .add("area", DataTypes.StringType)
    .add("market", DataTypes.StringType)
    .add("ipcode", DataTypes.StringType)
    .add("quantity", DataTypes.LongType)
    .add("timestamp", DataTypes.LongType)

  lazy val enrichedEventSchema = rawEventSchema.add("date", DataTypes.StringType)

}
