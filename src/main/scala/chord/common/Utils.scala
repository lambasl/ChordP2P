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
  def consistentHash(input: String): String ={
    val sha = MessageDigest.getInstance("SHA-256")
    var hashedString: String = sha.digest(input.getBytes).foldLeft("")((in: String, b: Byte) => in + Character.forDigit((b & 0xf0) >> 4, 16) + Character.forDigit(b & 0x0f, 16))

    return ""
  }
}