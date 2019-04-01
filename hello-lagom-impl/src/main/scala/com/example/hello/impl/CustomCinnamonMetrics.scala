package com.example.hello.impl

import akka.actor.ActorSystem
import com.lightbend.cinnamon.akka.CinnamonMetrics
import com.lightbend.cinnamon.metric.Rate

/**Handles functions for logging custom cinnamon metrics*/
class CustomCinnamonMetrics(actorSystem: ActorSystem) {

  private val hits: Rate = CinnamonMetrics.apply(actorSystem)
    .createRate("Insert_Hits")

  def totalHitsRateMark(): Unit = hits.mark()

}

trait CinnamonMetricsFactory {

  def getCustomCinnamonMetrics(system: ActorSystem): CustomCinnamonMetrics = new CustomCinnamonMetrics(system)

}

class CinnamonMetricsFactoryImpl extends CinnamonMetricsFactory

