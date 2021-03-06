package models

import doobie._
import doobie.implicits._
import doobie.util.fragment.Fragment

case class Snippet(sha256: String, content: String)

object Snippet {
  val TableName = Fragment.const("snippet")
  val Columns = "sha256" :: "content" :: Nil
  val DeclColumns = Fragment.const(Columns.mkString("(", ",", ")"))

  def findBySha256(sha256: String) =
    (sql"select content from " ++ TableName ++ sql" where sha256 like ${sha256 + "%"}").query[String].option

  def findAll =
    (sql"select snippet from " ++ TableName).query[String].to[List]

  def create(sha256: String, content: String) = {
    (sql"insert into " ++ TableName ++ DeclColumns ++ sql" values (${sha256}, ${content})").update
  }
}
