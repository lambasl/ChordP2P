package chord.actors

import akka.actor._
import chord.common._
import chrod._

class Peer(identity: Int) extends Actor {
  var fingerTable: Array[FingerTableEntry] = new Array[FingerTableEntry](Utils.m)
  var successor: Int = 0
  
  def receive = {
    case Messages.Join(joinIdentity) => {
      for (i <- 1 to (Utils.m)) {
        var ft = new FingerTableEntry
        ft.start = joinIdentity + (2 ^ (i - 1))
        ft.stop = joinIdentity + (2 ^ i)
        self ! Messages.FindSuccessor(joinIdentity, i)
        Main.networkRing(joinIdentity).asInstanceOf[Peer].fingerTable(i - 1) = ft
      }
    }
    case Messages.Lookup => {

    }
    case Messages.FindSuccessor(node, ftNumber) => {
      self ! Messages.FindPredecessor(node, ftNumber)
    }
    case Messages.FindPredecessor(node, ftNumber) => {
      if (((node > identity) && (node <= this.fingerTable(0).successor)) || ((this.fingerTable(0).successor < identity)) && ((node > identity) || (node <= this.fingerTable(0).successor))) {
        var predecessor = identity
        Main.networkRing(node).asInstanceOf[Peer].fingerTable(ftNumber-1).successor = this.fingerTable(0).successor
        this.fingerTable(0).successor = node
      }
      else {
        var max1: Int = 0
        var max2: Int = 0
        var count: Int = 0
        for (i <- 0 to (Utils.m - 1)) {
          if (this.fingerTable(i).successor < node) {
            count = count + 1
            if (this.fingerTable(i).successor > max1) {
              max1 = this.fingerTable(i).successor
            }
          }
          if (this.fingerTable(i).successor > max2) {
            max2 = this.fingerTable(i).successor
          }
        }
        if (count == 0) {
          Main.networkRing(max2) ! Messages.FindPredecessor (node, ftNumber) //To be checked
        }
        else{
          Main.networkRing(max1) ! Messages.FindPredecessor (node, ftNumber) //To Be Checked
        }
      }
    }
  }
}