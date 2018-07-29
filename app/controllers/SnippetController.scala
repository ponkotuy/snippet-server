package controllers

import doobie._
import doobie.implicits._
import forms.CreateSnippet
import javax.inject.Inject
import models.Snippet
import monix.eval.Task
import monix.execution.Scheduler
import org.apache.commons.codec.digest.DigestUtils
import play.api.db._
import play.api.mvc.{InjectedController, Result}

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
      val sha256 = DigestUtils.sha256Hex(content)
      Snippet.create(sha256, content).run.transact(xa)
          .map(_ => Ok(sha256)).runAsync

    })
  }
}
