package com.tpalanga.akka.concurrent.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Status}

import scala.concurrent.{ExecutionContextExecutor, Future}
import akka.pattern.pipe

object D_FutureActor {
  sealed trait BackendResponse
  case class Done(theSender: ActorRef, id: String) extends BackendResponse
  case class Failed(theSender: ActorRef, id: String, error: String) extends BackendResponse

  private def doBackendRequest(theSender: ActorRef, id: String)(implicit ec: ExecutionContextExecutor): Future[BackendResponse] = Future {
    println(s"Subscribing $id")
    Done(theSender, id)
  }


  def props: Props = Props(new D_FutureActor)
}

class D_FutureActor extends Actor with ActorLogging {
  import D_FutureActor._
  import context.dispatcher

  private var subscribers: Set[String] = Set.empty

  override def receive = {
    case Subscribe(id) =>
      doBackendRequest(sender(), id) pipeTo self

    case Done(theSender, id) =>
      subscribers += id
      sender() ! Subscribed

    case Failed(theSender, id, error) =>
      theSender ! SubscribeError

    case Status.Failure(th) =>
      log.error(th, "Backend request failed")
      sender() ! SubscribeError // problem - not the original sender

  }

}
