package models

import java.sql.Timestamp
import play.api.db.slick.Config.driver.simple._

/**
 * Created by yad on 15/04/25.
 */
case class Message(id: Long, name: String, mail: String, message: String, postDate: Timestamp)

class MessageTable(tag: Tag) extends Table[Message](tag, "messages") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def mail = column[String]("mail", O.NotNull)
  def message = column[String]("message", O.NotNull)
  def postDate = column[Timestamp]("postDate", O.NotNull)
  def * = (id, name, mail, message, postDate) <> (Message.tupled, Message.unapply)
}