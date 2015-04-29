package controllers

import java.sql.Date

import models._
import play.api.db.slick._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  val messageForm = Form(
    mapping(
      "name" -> text,
      "mail" -> text,
      "message" -> text(maxLength = 100)
    )(
        (name: String, mail: String, message: String) =>
          new Message(0, name, mail, message, new Date(System.currentTimeMillis()))
    )(
        (message: Message) => Some((message.name, message.mail, message.message))
    )
  )

  def index = DBAction { implicit session =>
    Ok(views.html.index.render("Database Sample", MessageDAO.readAll))
  }

  def add = Action {
    Ok(views.html.add.render("Post Message", messageForm))
  }

  def create = DBAction { implicit session =>
    messageForm.bindFromRequest().fold(
      errors => BadRequest(views.html.add.render("Error", messageForm)),
      message => {
        MessageDAO.create(message)
        Redirect("/")
      }
    )
  }
}