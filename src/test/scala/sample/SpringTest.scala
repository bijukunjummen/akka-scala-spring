package sample

import akka.actor.{ ActorRef, ActorSystem }
import sample.SpringExtension._
import scala.concurrent._
import scala.util._
import scala.concurrent.duration._
import org.scalatest.FunSuite
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import akka.actor.Inbox

class SpringTest extends FunSuite {
  test("Fire 3 COUNT ") {
    implicit val ctx = new AnnotationConfigApplicationContext(classOf[AppConfiguration])

    import Config._
    import messages._

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
    
    assert(count === 3)

    // shut down the actor system
    system.shutdown();
    system.awaitTermination();
  }

}
