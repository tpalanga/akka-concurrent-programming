package com.tpalanga.akka.concurrent.actors

import akka.actor.{Actor, ActorLogging, Props}

object A_EchoActor {
  case object Ping
  case object Pong
  case class Echo(value: String)
  case class EchoResponse(value: String)

  def props: Props = Props(new A_EchoActor)
}

class A_EchoActor extends Actor with ActorLogging {
  import A_EchoActor._

  override def receive = {
    case Ping =>
      log.info("Received Ping")
      sender() ! Pong

    case Echo(str) =>
      sender() ! EchoResponse(str)
  }
}
