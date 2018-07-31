package com.tpalanga.akka.concurrent.actors

object Examples {

  def processMessage(message: Request): String = message match {
    case Subscribe(id) =>
      s"Subscribe - $id"

    case Unsubscribe(id) =>
      s"Unsubscribe - $id"
  }

}
