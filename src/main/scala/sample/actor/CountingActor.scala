package sample.actor

import akka.actor.Actor
import sample.service.CountingService
import sample.SpringExtension._
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import akka.actor.ActorRef

@Component("countingActor")
@Scope("prototype")
class CountingActor @Autowired()(countingService: CountingService) extends Actor {

  import sample.messages._

  private var count = 0

  def receive = {
    case COUNT => count = countingService.increment(count)
    case GET(requester: ActorRef) => requester ! RESULT(count)
  }
  
}
