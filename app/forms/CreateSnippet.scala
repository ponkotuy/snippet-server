package forms

import play.api.data.Form
import play.api.data.Forms._

case class CreateSnippet(content: String)

object CreateSnippet {
  val form = Form(
    mapping(
      "content" -> nonEmptyText
    )(CreateSnippet.apply)(CreateSnippet.unapply)
  )
}
