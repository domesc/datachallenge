package com.challenge.queryservice
import com.challenge.commonspark.Schemas
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

object Main extends App {

  val conf = ConfigFactory.load()

  implicit val spark = SparkSession.builder()
    .appName("queryService")
    .master("local[*]")
    .getOrCreate()

  val df = spark.read.schema(Schemas.enrichedEventSchema).parquet(conf.getString("inputPath"))

  while(true) {
    println("Choose the metric")
    println("1 - Top 5 markets by sales")
    println("2 - Top 5 areas by sales")
    println("3 - Sales by region in descending order")
    println("4 - Sum of sales globally")
    val choice = scala.io.StdIn.readLine().toInt

    println("Write the date for which you would like to see the dashboards (format yyytMMdd)")
    val date = scala.io.StdIn.readLine()

    if(choice == 1) {
      val result = QueryProcessor.topMarketsBySales(df, date)
      Dashboards.topAreasBySales(result.map(r => (r.getAs[String]("market"), r.getAs[Long]("total_sales"))))
    } else if(choice == 2) {
      val result = QueryProcessor.topAreasBySales(df, date)
      Dashboards.topAreasBySales(result.map(r => (r.getAs[String]("area"), r.getAs[Long]("total_sales"))))
    } else if(choice == 3) {
      val result = QueryProcessor.salesByRegion(df, date)
      Dashboards.salesByRegionDesc(result.map(r => (r.getAs[String]("region"), r.getAs[Long]("total_sales"))))
    } else if(choice == 4) {
      val result = QueryProcessor.sumOfSalesGlobally(df, date)
      Dashboards.sumOfSalesGlobally(result)
    }
  }
}
