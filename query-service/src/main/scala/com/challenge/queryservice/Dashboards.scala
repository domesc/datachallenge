package com.challenge.queryservice


import org.apache.spark.sql.DataFrame
import vegas._
import vegas.sparkExt._


object Dashboards {

  def topMarketsBySales(data: Array[(String, Long)]): Unit = {
    val plot = Vegas("Top 5 markets by sales").
      withData(
        data.map{
          case (market, sales) =>  Map("market" -> market, "total_sales" -> sales)
        }
      ).
      encodeX("market", Nom).
      encodeY("total_sales", Quant).
      mark(Bar)

    plot.show
  }

  def topAreasBySales(data: Array[(String, Long)]): Unit = {

    val plot = Vegas("Top 5 areas by sales").
      withData(
        data.map{
          case (area, sales) =>  Map("area" -> area, "total_sales" -> sales)
        }
      ).
      encodeX("area", Nom).
      encodeY("total_sales", Quant).
      mark(Bar)

    plot.show
  }

  def salesByRegionDesc(data: Array[(String, Long)]): Unit = {

    val plot = Vegas("Sales by region in descending order").
      withData(
        data.map{
          case (area, sales) =>  Map("region" -> area, "total_sales" -> sales)
        }
      )
      .encodeX("region", Nom, sortOrder = SortOrder.None)
      .encodeY("total_sales", Quant, sortOrder = SortOrder.None).
      mark(Bar)

    plot.show
  }

  def sumOfSalesGlobally(df: DataFrame): Unit = {

    val plot = Vegas("Sum of Sales globally").
      withDataFrame(df)
      .encodeY("total_sales", Quant)
      .mark(Bar)

    plot.show
  }

}
