package com.tpalanga.akka.concurrent

import akka.actor.ActorSystem
import com.tpalanga.akka.concurrent.actors.A_EchoActor
import com.tpalanga.akka.concurrent.actors.A_EchoActor.Ping
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object Bootstrap extends App {
  implicit val system = ActorSystem("actor-system")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(3.seconds)

  val echoActor = system.actorOf(A_EchoActor.props)

  (echoActor ? Ping).onComplete(_ => println("Received Pong"))

}
