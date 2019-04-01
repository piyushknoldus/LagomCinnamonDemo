package com.example.hello.impl

import akka.actor.{Actor, ActorSystem}

class DBLogic extends Actor with CinnamonMetricsFactory {

  implicit val system: ActorSystem = context.system

  def dummyInsert(): Unit = {
    getCustomCinnamonMetrics(system).totalHitsRateMark()
    //("inserting into DB Logic")
  }

  override def receive: Receive = {
    case "insert" => dummyInsert()
  }
}
