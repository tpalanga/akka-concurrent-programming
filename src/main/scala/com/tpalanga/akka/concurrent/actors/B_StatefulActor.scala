package com.tpalanga.akka.concurrent.actors

import akka.actor.{Actor, Props}

object B_StatefulActor {
  def props: Props = Props(new A_EchoActor)
}

class B_StatefulActor extends Actor {
  private var subscribers: Set[String] = Set.empty

  override def receive = {
    case Subscribe(id) =>
      subscribers += id
      sender() ! Subscribed

    case Unsubscribe(id) =>
      subscribers -= id
      sender() ! Unsubscribed
  }
}
