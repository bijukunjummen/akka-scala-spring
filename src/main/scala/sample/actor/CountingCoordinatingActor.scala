package sample.actor

import akka.actor.Actor
import sample.service.CountingService
import sample.SpringExtension._
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import akka.actor.ActorRef
import sample.SpringExtension
import org.springframework.context.ApplicationContext

@Component("countingCoordinatingActor")
@Scope("prototype")
class CountingCoordinating @Autowired() (implicit ctx: ApplicationContext) extends Actor {

  import sample.messages._

  var counter: Option[ActorRef] = None

  
  def receive = {
    case COUNT => countingActor() ! COUNT
    case g:GET => countingActor() ! g
  }
  
  private def countingActor(): ActorRef = {
     if (counter.isEmpty) {
        val countingActorProp = SpringExtension(context.system).props("countingActor")
        counter = Some(context.actorOf(countingActorProp, "counter"))
     }  
     
     counter.get
  }
  
}
