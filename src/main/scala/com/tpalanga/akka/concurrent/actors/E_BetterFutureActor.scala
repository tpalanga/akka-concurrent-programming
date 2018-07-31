package com.tpalanga.akka.concurrent.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Status}

import scala.concurrent.{ExecutionContextExecutor, Future}
import akka.pattern.pipe

import scala.util.control.NonFatal

object E_BetterFutureActor {

  sealed trait BackendResponse
  case class Done(id: String) extends BackendResponse
  case class Failed(id: String, error: String) extends BackendResponse

  private def doBackendRequest(id: String)
                              (implicit ec: ExecutionContextExecutor): Future[BackendResponse] =
    Future {
      println(s"Subscribing $id")
      Done(id)
    }

  def props: Props = Props(new E_BetterFutureActor)
}

class E_BetterFutureActor extends Actor with ActorLogging {

  import E_BetterFutureActor._
  import context.dispatcher

  override def receive = receiving(Set.empty, Map.empty)

  private def receiving(subscribers: Set[String], pending: Map[String, ActorRef]): Receive = {
    case Subscribe(id) =>
      doBackendRequest(id) pipeTo self
      context.become(receiving(subscribers, pending + (id -> sender())))

    case Done(id) =>
      pending.get(id).foreach { theSender =>
        context.become(receiving(subscribers + id, pending - id))
        theSender ! Subscribed
      }

    case Failed(id, error) =>
      pending.get(id).foreach { theSender =>
        context.become(receiving(subscribers, pending - id))
        theSender ! SubscribeError
      }

    case Status.Failure(th) =>
      log.error(th, "Backend request failed")
      sender() ! SubscribeError // problem - not the original sender
  }
}
