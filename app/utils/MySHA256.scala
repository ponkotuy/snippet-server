package utils

import java.nio.charset.Charset
import java.security.MessageDigest

// non-threadsafe
object MySHA256 {
  private[this] val instance = new ThreadLocal[MessageDigest] {
    MessageDigest.getInstance("SHA-256")
  }
  def digestBytes(bytes: Array[Byte]): Array[Byte] = instance.get.digest(bytes)
  def digestString(str: String): String = new String(instance.get.digest(str.getBytes("UTF-8")))
}
