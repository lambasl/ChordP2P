package chord.common

/**
 * @author user
 */
object Messages {

  case class Join(networkCount: Int)
  case class Lookup(file: String)
}