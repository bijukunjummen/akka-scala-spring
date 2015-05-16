package sample


import akka.actor.{ActorRef, ActorSystem}
import sample.SpringExtension._
import scala.concurrent.duration._
import scala.concurrent._
import scala.util._
import sample.messages._
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import akka.actor.Inbox


object Main extends App {
  // create a spring context
  implicit val ctx = new AnnotationConfigApplicationContext(classOf[AppConfiguration])

  import Config._

  // get hold of the actor system
  val system = ctx.getBean(classOf[ActorSystem])

  val inbox = Inbox.create(system)
  
  val prop = SpringExtension(system).props("countingCoordinatingActor")

  // use the Spring Extension to create props for a named actor bean
  val countingCoordinator = system.actorOf(prop, "counter")

  // tell it to count three times
  inbox.send(countingCoordinator, COUNT)
  inbox.send(countingCoordinator, COUNT)
  inbox.send(countingCoordinator, COUNT)
  
  inbox.send(countingCoordinator, GET(inbox.getRef()))
  
  val RESULT(count) = inbox.receive(5.seconds)

  println(s"Got $count")
  system.shutdown
  system.awaitTermination
}
