package com.onkar.accounts

import akka.actor.{ActorSystem, Inbox, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}


/**
  * Created by onkar on 3/8/17.
  */

object AccountApp extends App {

  val system = ActorSystem("banking-demo")

  val onkar = system.actorOf(Props(new Account("Onkar", 2000)), "onky")

  val akshay = system.actorOf(Props(new Account("Akshay", 1000)), "akki")

  println(onkar)

  println(akshay)

  val inbox = Inbox.create(system)

  implicit val timeout = Timeout(11 seconds)
  val future = onkar ? GetBalance

  future.onComplete {
    case Success(value) => println(s"Got the callback, balance = $value")
    case Failure(e) => e.printStackTrace
  }

  inbox.send(akshay, GetBalance)
  val balance2 = inbox.receive(5.seconds)
  println(s"Akshay's Balance: $balance2")

  onkar.tell(Transfer(200, akshay), onkar)

  inbox.send(onkar, GetBalance)
  // Wait 5 seconds for the reply
  val balance3 = inbox.receive(5.seconds)
  println(s"Onkar's Balance: $balance3")

  inbox.send(akshay, GetBalance)
  val balance4 = inbox.receive(5.seconds)
  println(s"Akshay's Balance: $balance4")

  onkar ! PoisonPill
  akshay ! PoisonPill
  system.terminate()
}
