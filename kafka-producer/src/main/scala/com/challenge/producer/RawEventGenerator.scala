package com.challenge.producer
import java.time.Instant

import com.challenge.common.RawEvent

import scala.util.Random

sealed trait Region { def name: String }
case object Europe extends Region { val name = "EUROPE" }
case object Asia extends Region { val name = "ASIA" }
case object NorthAmerica extends Region { val name = "NORTH AMERICA" }
case object SouthAmerica extends Region { val name = "SOUTH AMERICA" }
case object Africa extends Region { val name = "AFRICA" }
case object Oceania extends Region { val name = "OCEANIA"}

object RawEventGenerator {

  private val ipcodeRan = new Random(42)
  private val quantityRan = new Random(57)

  private val regions = List(Europe, Asia, NorthAmerica, SouthAmerica, Africa, Oceania)
  private val europeanAreas = List("SOUTHERN EUROPE")
  private val asianAreas = List("CENTRAL ASIA")
  private val africanAreas = List("NORTH AFRICA")
  private val oceaniaAreas = List("AUSTRALIA")
  private val northAmericaAreas = List("NORTH AMERICA")
  private val southAmericaAreas = List("SOUTH AMERICA")
  private val europeannMarket = List("ITALY", "SPAIN", "FRANCE", "PORTUGAL")
  private val asianMarket = List("CHINA")
  private val africanMarket = List("MOROCCO", "EGYPT", "TUNISIA")
  private val oceaniaMarket = List("AUSTRALIA", "NEW ZEALAND")
  private val northAmericaMarket = List("USA", "CANADA")
  private val southAmericaMarket = List("BRAZIL", "ARGENTINA", "COLOMBIA")

  def nextEvent(): RawEvent = {
    val randomRegion = Random.shuffle(regions).head
    val (randomArea, randomMarket) = randomRegion match {
      case Europe => (Random.shuffle(europeanAreas).head, Random.shuffle(europeannMarket).head)
      case Asia => (Random.shuffle(asianAreas).head, Random.shuffle(asianMarket).head)
      case NorthAmerica => (Random.shuffle(northAmericaAreas).head, Random.shuffle(northAmericaMarket).head)
      case SouthAmerica => (Random.shuffle(southAmericaAreas).head, Random.shuffle(southAmericaMarket).head)
      case Africa => (Random.shuffle(africanAreas).head, Random.shuffle(africanMarket).head)
      case Oceania => (Random.shuffle(oceaniaAreas).head, Random.shuffle(oceaniaMarket).head)
    }
    RawEvent(
      id = java.util.UUID.randomUUID.toString, //considering we are on one only machine and not in a distributed environment
      region = randomRegion.name,
      area = randomArea,
      market = randomMarket,
      ipcode = ipcodeRan.nextInt(2000000).toString,
      quantity = quantityRan.nextInt(1000),
      timestamp = Instant.now.getEpochSecond
    )
  }

}
