package chord.actors

import akka.actor.Actor
import chord.common.Messages

/**
 * @author user
 */
class Peer extends Actor{
  
  def receive = {
    
    case "Hello" =>
      
    case Messages.join(ipAddr : String) =>{
      //finds appropriate  node  and forward the message to it 
      //and add to it's finger table, move any files if required 
    }
    
    case Messages.lookup(file : String) => {
      
    }
    
    //maintain finger tables and files in some datastructure
      
  }
  
}