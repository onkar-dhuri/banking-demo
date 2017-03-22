package com.onkar.accounts

import akka.actor.{Actor, ActorRef}

import scala.util.Random

/**
  * Created by onkar on 3/8/17.
  */

case class Balance(balance: Double)

case object GetBalance

case class Receive(amount: Double)

case class SendMessage(message: String)

case class Transfer(amount: Double, account: ActorRef)

class Account(var name: String, var balance: Double) extends Actor {
  val acccountNumber = Account.newAccountNum

  override def receive = {
    case GetBalance => {
      Thread.sleep(Random.nextInt(5000));
      sender ! Balance(balance)
    }
    case Receive(amount) => balance += amount
    case Transfer(amount, account) => if (amount > balance) {
      sender ! SendMessage("Insufficient Balance")
    } else {
      balance -= amount
      account ! Receive(amount)
    }
  }

  override def toString(): String = {
    return "Account Name : %s \n Account Number : %s \n Balance : %s".format(
      this.name, this.acccountNumber, this.balance)
  }
}

// companion object for account number
object Account {
  private var accountNumber = 100

  private def newAccountNum = {
    accountNumber += 1;
    accountNumber
  }
}