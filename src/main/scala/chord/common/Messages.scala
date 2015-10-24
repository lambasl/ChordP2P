package chord.common

object Messages {
  case class Join(identity: Int)
  case object Lookup
  case class FindSuccessor(node: Int, ftNumber: Int)
  case class FindPredecessor(node: Int, ftNumber: Int)
}