package chrod

import akka.actor._
import chord.common._
import chord.actors.Peer

object Main extends App {
  var m = Utils.getM()
  var numberOfNodes: Int = args(0).toInt
  var numberOfRequests: Int = args(1).toInt
  val networkSystem = ActorSystem("networkSystem")  
  var networkRing: Array[ActorRef] = new Array[ActorRef](2 ^ (Utils.m))
  var randomIPs = Utils.generateIpAddreses(numberOfNodes)
  
  //Creating an initialnode
  
  var identity: Int = Utils.consistentHash(randomIPs(0))
  networkRing(identity) = networkSystem.actorOf(Props(new Peer(identity)), name = 0.toString)
  for (i <- 1 to (Utils.m)) {
    var ft = new FingerTableEntry
    ft.start = identity + (2 ^ (i - 1))
    ft.stop = identity + (2 ^ i)
    ft.successor = identity
    networkRing(identity).asInstanceOf[Peer].fingerTable(i-1) = ft
    networkRing(identity).asInstanceOf[Peer].successor = identity
  }
  
  var initialNode = networkRing(identity)
  
  //Creating Other Nodes
  
  for (i <- 1 to numberOfNodes - 1) {
    identity = Utils.consistentHash(randomIPs(i))
    networkRing(identity) = networkSystem.actorOf(Props(new Peer(identity)), name = 0.toString)
    initialNode ! Messages.Join(identity)
  }
  
}