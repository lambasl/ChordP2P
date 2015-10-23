package chrod

import chord.actors.Peer
import chord.common._
import scala.collection.mutable.ArrayBuffer
import akka.actor._

object Main extends App {
  var numberOfNodes: Int = args(0).toInt
  var numberOfRequests: Int = args(1).toInt

  var networkRing: Array[ActorRef] = new Array[ActorRef](2 ^ (Utils.m))
  var networkNodes = ArrayBuffer[ActorRef]()
  var networkCount = 0
  val networkSystem = ActorSystem("networkSystem")

  //Create an initial Node
   networkRing(0) = networkSystem.actorOf(Props(new Peer(0)), name = 0.toString)
  for (i <- 0 to (Utils.m) - 1) {
    var ft = new FingerTableEntry
    ft.start = 0
    networkRing(0).asInstanceOf[Peer].fingerTable(0) = ft
    
  }
  //Send Join requests for  the rest of the nodes

  for (i <- 1 to numberOfNodes - 1) {

    var hashedKey = Utils.consistentHash(i.toString)
    var identity: Int = 0 //Calculate Identity by hashing and truncating**************
    networkRing(identity) = networkSystem.actorOf(Props(new Peer(identity)), name = identity.toString )
    networkRing(0) ! Messages.Join(identity)
  }
}

/*  if (
    for (j <- 0 to numberOfRequests - 1) {
      val r = new scala.util.Random
      val sb = new StringBuilder
      for (i <- 1 to 6) {
        sb.append(r.nextPrintableChar)
      }
      networkRing(identity) ! Messages.Lookup(sb.toString())
    }
    networkCount = networkCount + 1
  }
  
  class Master extends Actor {
*/