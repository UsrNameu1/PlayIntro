package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index.render("This is first : ", "This is second : ", "This is last : "))
  }

}