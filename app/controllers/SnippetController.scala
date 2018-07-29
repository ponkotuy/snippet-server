package controllers

import doobie._
import doobie.implicits._
import forms.CreateSnippet
import javax.inject.Inject
import models.Snippet
import monix.eval.Task
import monix.execution.Scheduler
import play.api.db._
import play.api.mvc.InjectedController

import scala.concurrent.ExecutionContext


class SnippetController @Inject()(db: Database, ec: ExecutionContext) extends InjectedController {
  implicit val scheduler: Scheduler = Scheduler(ec)

  def xa = Transactor.fromConnection[Task](db.getConnection())

  def index() = Action.async {
    Snippet.findAll.transact(xa).map { results =>
      Ok(results.mkString("\n"))
    }.runAsync
  }

  def create() = Action.async(parse.form(CreateSnippet.form)) { implicit req =>
    Snippet.create(req.body.content).run.transact(xa)
        .map(_ => Ok("Success")).runAsync
  }
}
