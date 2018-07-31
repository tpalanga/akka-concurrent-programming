package com.tpalanga.akka.concurrent.actors

import akka.actor.{Actor, Props}

object C_BetterStatefulActor {
  def props: Props = Props(new C_BetterStatefulActor)
}

class C_BetterStatefulActor extends Actor {

  override def receive = receiving(Set.empty)

  private def receiving(subscribers: Set[String]): Receive = {
    case Subscribe(id) =>
      context.become(receiving(subscribers + id))
      sender() ! Subscribed

    case Unsubscribe(id) =>
      context.become(receiving(subscribers - id))
      sender() ! Unsubscribed
  }
}
