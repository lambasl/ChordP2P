package chord.common

import java.security.MessageDigest
import scala.util.Random

/**
 * @author user
 */
object Utils {
  
  val m = 8; // size of the ring is 2^8 = 256

  /**
   * hash(input) calculates the hash of the given input string. Take m bits from sha hash
   * of the string and take m bits from it
   */
  def consistentHash(input: String): Int = {
    val sha = MessageDigest.getInstance("SHA-256")
    var hash = sha.digest(input.getBytes)
    var value: Int = 0;
    for (i <- 0 to hash.length - 5) {
      value += (hash(i) & 0xff) << (8 * i);
    }
    //scala returns negative mod if the number is negative.
    if (value < 0) {
      value = -1 * value;
    }
    println(value)
    val finalVal = value % Math.pow(2, m)
    return finalVal.toInt

  }
  
  def getM(): Int={
    return m;
  }
  
  def generateIpAddreses(n: Int): Array[String] ={
    var addresses = new Array[String](n)
    var rand = new Random;
     for(i <- 0 to n - 1){
        var pos1 = rand.nextInt(255) + 1;
        var pos2 = rand.nextInt(256);
        var pos3 = rand.nextInt(256);
        var pos4 = rand.nextInt(256);
        var ip = pos1 + "." + pos2 + "." + pos3 + "." + pos4
        addresses(i) = ip
     }
    
    return addresses
  }
  
  
}