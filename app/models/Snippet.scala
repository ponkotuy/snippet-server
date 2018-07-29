package models

import doobie.implicits._
import doobie.util.fragment.Fragment

case class Snippet(sha256: String, snippet: String)

object Snippet {
  val TableName = Fragment.const("snippet")
  val Columns = "sha256" :: "snippet" :: Nil
  val DeclColumns = Fragment.const(Columns.mkString("(", ",", ")"))

  def findBySha256(sha256: String) =
    (sql"select snippet from " ++ TableName ++ sql"where sha256 = ${sha256}").query[String].option

  def findAll =
    (sql"select snippet from " ++ TableName).query[String].to[List]

  def create(sha256: String, content: String) = {
    (sql"insert into " ++ TableName ++ DeclColumns ++ sql"values (${sha256}, ${content})").update
  }
}
