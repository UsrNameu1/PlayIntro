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
      "id" -> longNumber,
      "name" -> text,
      "mail" -> email,
      "message" -> text(maxLength = 100),
      "createDate" -> default(sqlDate, new Date(System.currentTimeMillis()))
    )(Message.apply)(Message.unapply)
  )

  val idForm = Form("id" -> longNumber)

  def index = DBAction { implicit session =>
    Ok(views.html.index.render("Database Sample", MessageDAO.readAll))
  }

  def add = Action {
    Ok(views.html.add.render("Post Message", messageForm))
  }

  def create = DBAction { implicit session =>
    messageForm.bindFromRequest().fold(
      errors => BadRequest(views.html.add.render("Error: " + errors.errors, messageForm)),
      message => {
        MessageDAO.create(message)
        Redirect("/")
      }
    )
  }

  def setitem = Action {
    Ok(views.html.item.render("Input item id number ", idForm))
  }

  def edit = DBAction { implicit session =>
    idForm.bindFromRequest().fold(
      errors => BadRequest(views.html.item.render("Input error: " + errors.errors, idForm)),
      id => MessageDAO.read(id) match {
        case Some(messageFound) =>
          val form = messageForm.fill(messageFound)
          Ok(views.html.edit.render("Edit message with ID = " + id, id, form))
        case None =>
          Ok(views.html.item.render("Not Found error: ", idForm))
      }
    )
  }

  def update = DBAction { implicit session =>
    messageForm.bindFromRequest().fold(
      errors => Ok(views.html.edit.render("Input error: " + errors.errors, 0, messageForm)),
      message => {
        MessageDAO.update(message)
        Redirect("/")
      }
    )
  }

  def delete = Action {
    Ok(views.html.delete.render("Delete item id number ", idForm))
  }

  def remove = DBAction { implicit session =>
    idForm.bindFromRequest().fold(
      errors => BadRequest(views.html.delete.render("Input error: " + errors.errors, idForm)),
      id => MessageDAO.read(id) match {
        case Some(messageFound) =>
          MessageDAO.delete(id)
          Redirect("/")
        case None =>
          Ok(views.html.delete.render("Not Found error: ", idForm))
      }
    )
  }
}