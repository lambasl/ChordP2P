package chord.actors

import akka.actor.Actor
import chord.common._
import chrod.Main

/**
 * @author user
 */
class Peer (val identity: Int) extends Actor {
  var fingerTable: Array[FingerTableEntry] = new Array[FingerTableEntry](Utils.m)
  var successor: Int = 0


  def receive = {

    case Messages.Join(networkCount: Int) => {
      //Initialize its own finger table entries.
      for (i <- 0 to Utils.m - 1) {
        fingerTable(i).start = identity + 2 ^ i
        fingerTable(i).stop = identity + 2 ^ (i + 1)
      }      
      if (networkCount == 0) {
        for (i <- 0 to Utils.m - 1) {
          fingerTable(i).successor = identity
        }
      }
      else {
        for (i <- 0 to Utils.m - 1) {
         // fingerTable(i).successor = Main.findSuccessor(fingerTable(i).start)
        }
      }
      //Update all other finger tables
      //Move all the appropritae keys to the new node
      
    }

    case Messages.Lookup(file : String) => {

    }


  }

}