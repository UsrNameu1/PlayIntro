package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  val form = Form[String](
    "message" -> text
  )

  def index = Action {
    Ok(views.html.index.render("Write some words: ", form))
  }

  def send = Action(implicit request =>
    form.bindFromRequest().fold(
      errors => BadRequest(views.html.index.render("Error: ", form)),
      success = text => {
        Ok(views.html.index.render("Write some words: " + text, form))
      }
    )
  )
}