package com.challenge.producer

import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import play.api.libs.json.Json

object Main extends App {

  val conf = ConfigFactory.load()

  val  props = new Properties()
  props.put("bootstrap.servers", conf.getString("bootstrap-servers"))

  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val topicName = conf.getString("topic")


  while(true) {
    val rawEvent = RawEventGenerator.nextEvent()
    val record = new ProducerRecord(topicName, "key", Json.toJson(rawEvent).toString())
    producer.send(record)
    Thread.sleep(1000)
  }

  sys.addShutdownHook {
    producer.close()
  }
}
