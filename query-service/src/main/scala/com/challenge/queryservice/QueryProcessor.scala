package com.challenge.queryservice
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object QueryProcessor {

  def topMarketsBySales(df: DataFrame, dateFilter: String)(implicit spark: SparkSession): Array[Row] = {
    import spark.implicits._
    df.filter($"date" <= dateFilter)
      .groupBy($"market")
      .agg(sum($"quantity").as("total_sales"))
      .orderBy(desc("total_sales"))
      .take(5)
  }

  def topAreasBySales(df: DataFrame, dateFilter: String)(implicit spark: SparkSession): Array[Row] = {
    import spark.implicits._
    df.filter($"date" <= dateFilter)
      .groupBy($"area")
      .agg(sum($"quantity").as("total_sales"))
      .orderBy(desc("total_sales"))
      .take(5)
  }

  def salesByRegion(df: DataFrame, dateFilter: String)(implicit spark: SparkSession): Array[Row] = {
    import spark.implicits._
    df.filter($"date" <= dateFilter)
      .groupBy($"region")
      .agg(sum($"quantity").as("total_sales"))
      .orderBy(desc("total_sales"))
      .collect()
  }

  def sumOfSalesGlobally(df: DataFrame, dateFilter: String)(implicit spark: SparkSession): DataFrame = {
    import spark.implicits._
    df.filter($"date" <= dateFilter)
      .agg(sum($"quantity").as("total_sales"))
      .select("total_sales")
  }

}
