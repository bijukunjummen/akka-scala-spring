package sample

import akka.actor.ActorRef

object messages {
  object COUNT
  case class GET(requester: ActorRef)
  case class RESULT(count: Int)
}