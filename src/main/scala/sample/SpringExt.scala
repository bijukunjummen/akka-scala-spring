package sample

import akka.actor._

import org.springframework.context.ApplicationContext


object SpringExt extends AbstractExtensionId[SpringExtension] 
    with ExtensionIdProvider {
  
  /**
   * Is used by Akka to instantiate the Extension identified by this
   * ExtensionId, internal use only.
   */
  override def createExtension(system: ExtendedActorSystem) = new SpringExtension

  /**
   * Retrieve the SpringExt extension for the given system.
   */
  override def lookup() = SpringExt

}

