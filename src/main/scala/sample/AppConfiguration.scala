package sample

import akka.actor.ActorSystem
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan

@Configuration
@ComponentScan(Array("sample.service", "sample.actor"))
class AppConfiguration {

  @Autowired
  implicit var ctx: ApplicationContext = _;
  
  /**
   * Actor system singleton for this application.
   */
  @Bean
  def actorSystem() = {
    val system = ActorSystem("AkkaScalaSpring")
    // initialize the application context in the Akka Spring Extension
    SpringExt(system)
    system    
  }
}