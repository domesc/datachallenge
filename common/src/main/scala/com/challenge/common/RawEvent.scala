package com.challenge.common
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class RawEvent(
  id: String,
  region: String,
  area: String,
  market: String,
  ipcode: String,
  quantity: Long,
  timestamp: Long)

object RawEvent {
  implicit val eventReads: Reads[RawEvent] = (
    (JsPath \ "event_id").read[String] and
      (JsPath \ "region").read[String] and
      (JsPath \ "area").read[String] and
      (JsPath \ "market").read[String] and
      (JsPath \ "ipcode").read[String] and
      (JsPath \ "quantity").read[Long] and
      (JsPath \ "timestamp").read[Long]
    )(RawEvent.apply _)

  implicit val eventWrites: Writes[RawEvent] = (
    (JsPath \ "event_id").write[String] and
      (JsPath \ "region").write[String] and
      (JsPath \ "area").write[String] and
      (JsPath \ "market").write[String] and
      (JsPath \ "ipcode").write[String] and
      (JsPath \ "quantity").write[Long] and
      (JsPath \ "timestamp").write[Long]
    )(unlift(RawEvent.unapply))
}