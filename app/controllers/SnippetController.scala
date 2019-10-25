package controllers

import doobie._
import doobie.implicits._
import forms.CreateSnippet
import javax.inject.Inject
import javax.xml.bind.DatatypeConverter
import models.Snippet
import monix.eval.Task
import monix.execution.Scheduler
import play.api.db._
import play.api.mvc.{InjectedController, Result}
import utils.MySHA256

import scala.concurrent.{ExecutionContext, Future}


class SnippetController @Inject()(db: Database, ec: ExecutionContext) extends InjectedController {
  import Responses._

  implicit val scheduler: Scheduler = Scheduler(ec)

  def xa = Transactor.fromConnection[Task](db.getConnection())

  def index() = Action.async {
    Snippet.findAll.transact(xa).map { results =>
      Ok(results.mkString("\n"))
    }.runAsync
  }

  def show(sha256: String) = Action.async {
    Snippet.findBySha256(sha256).transact(xa).map { opt =>
      opt.fold[Result](NotFound(s"Not found ${sha256}"))(Ok(_))
    }.runAsync
  }

  def create() = Action.async { implicit req =>
    CreateSnippet.form.bindFromRequest().fold(f => Future.successful(parseError(f)), { body =>
      val content = body.snippet
      val digest = DatatypeConverter.printHexBinary(MySHA256.digest(content))
      Snippet.create(digest, content).run.transact(xa)
          .map(_ => Ok(digest)).runAsync

    })
  }
}
