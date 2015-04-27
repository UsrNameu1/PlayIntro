package controllers

import models.MessageDAO
import play.api.db.slick._
import play.api.mvc._

object Application extends Controller {

  def index = DBAction { implicit request =>
    Ok(views.html.index.render("Database Sample", MessageDAO.readAll))
  }
}