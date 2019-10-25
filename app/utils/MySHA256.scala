package utils

import java.security.MessageDigest
import java.util.Base64

object MySHA256 {
  val instance = new ThreadLocal[MessageDigest] {
    override def initialValue(): MessageDigest = {
      MessageDigest.getInstance("SHA-256")
    }
  }

  val base64 = Base64.getUrlEncoder

  def digest(bytes: Array[Byte]): Array[Byte] = instance.get.digest(bytes)
  def digest(str: String): Array[Byte] = digest(str.getBytes("UTF-8"))
}
