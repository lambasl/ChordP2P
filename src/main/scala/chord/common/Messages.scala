package chord.common

/**
 * @author user
 */
object Messages {
  
  case class join(ipAddr : String)
  case class lookup(file : String)
}