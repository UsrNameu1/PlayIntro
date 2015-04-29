package models

import java.sql.Date
import scala.slick.driver.H2Driver.simple._

case class Message(id: Long, name: String, mail: String, message: String, createDate: Date)

class MessageTable(tag: Tag) extends Table[Message](tag, "messages") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def mail = column[String]("mail", O.NotNull)
  def message = column[String]("message", O.NotNull)
  def createDate = column[Date]("createDate", O.NotNull)
  def * = (id, name, mail, message, createDate) <> (Message.tupled, Message.unapply)
}

object MessageDAO {
  lazy val messageQuery = TableQuery[MessageTable]

  def read(id: Long)(implicit session: Session): Message = {
    messageQuery.filter(_.id === id).first
  }

  def readAll(implicit session: Session): List[Message] = {
    messageQuery.list
  }

  def create(message: Message)(implicit session: Session): Unit = {
    messageQuery.insert(message)
  }

  def update(message: Message)(implicit session: Session): Unit = {
    messageQuery.filter(_.id === message.id).update(message)
  }

  def delete(id: Long)(implicit session: Session) = {
    messageQuery.filter(_.id === id).delete
  }
}
