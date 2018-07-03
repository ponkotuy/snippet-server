package models

import doobie.implicits._

case class Snippet(sha256: String, content: String)

object Snippet {
  def findAll =
    sql"select snippet from snippet".query[String].to[List]
}
