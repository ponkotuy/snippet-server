package forms

import play.api.data.Form
import play.api.data.Forms._

case class CreateSnippet(snippet: String)

object CreateSnippet {
  val form = Form(
    mapping(
      "snippet" -> nonEmptyText
    )(CreateSnippet.apply)(CreateSnippet.unapply)
  )
}
