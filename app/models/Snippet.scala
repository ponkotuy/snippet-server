package models

import doobie.implicits._
import doobie.util.fragment.Fragment
import org.apache.commons.codec.digest.DigestUtils

case class Snippet(sha256: String, snippet: String)

object Snippet {
  val TableName = Fragment.const("snippet")
  val Columns = "sha256" :: "snippet" :: Nil
  val DeclColumns = Fragment.const(Columns.mkString("(", ",", ")"))

  def findAll =
    (sql"select snippet from " ++ TableName).query[String].to[List]

  def create(content: String) = {
    val sha256 = DigestUtils.sha256Hex(content)
    (sql"insert into " ++ TableName ++ DeclColumns ++ sql"values (${sha256}, ${content})").update
  }
}
