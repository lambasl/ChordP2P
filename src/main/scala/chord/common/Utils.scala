package chord.common

import java.security.MessageDigest

/**
 * @author user
 */
object Utils {

  val m = 7; // size of the ring is 2^7

  /**
   * hash(input) calculates the hash of the given input string. Take m bits from sha hash
   * of the string and take m bits from it
   */
  def consistentHash(input: String): String = {
    val md = java.security.MessageDigest.getInstance("SHA-1")
    val hashedString: String = md.digest(input.getBytes("UTF-8")).map("%02x".format(_)).mkString
    return hashedString
  }
}