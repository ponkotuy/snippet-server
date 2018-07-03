package controllers

import cats.effect.{Effect, IO}
import doobie._
import doobie.implicits._
import javax.inject.Inject
import models.Snippet
import play.api.db._
import play.api.mvc.InjectedController


class SnippetController @Inject()(db: Database) extends InjectedController {
  def index() = Action {
    val xa = Transactor.fromConnection[IO](db.getConnection())
    val results = Snippet.findAll.transact(xa).unsafeRunSync()
    Ok(results.mkString("\n"))
  }
}
