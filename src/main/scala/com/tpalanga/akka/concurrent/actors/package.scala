package com.tpalanga.akka.concurrent

package object actors {
  sealed trait Request
  case class Subscribe(id: String) extends Request
  case class Unsubscribe(id: String) extends Request

  sealed trait Response
  case object Subscribed extends Response
  case object SubscribeError extends Response
  case object Unsubscribed extends Response
}
