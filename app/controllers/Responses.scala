package controllers

import play.api.data.Form
import play.api.mvc.Result
import play.api.mvc.Results._

object Responses {
  def parseError[T](form: Form[T]): Result = {
    BadRequest(form.errors.mkString("\n"))
  }
}
