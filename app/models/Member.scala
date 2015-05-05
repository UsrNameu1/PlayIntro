package models

import java.sql.Date
import scala.slick.driver.H2Driver.simple._

case class Member(id: Long, name: String, mail: String, tel: String, createDate: Date)

class MemberTable(tag: Tag) extends Table[Member](tag, "members") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def mail = column[String]("mail", O.NotNull)
  def tel = column[String]("tel", O.NotNull)
  def createDate = column[Date]("createDate", O.NotNull)
  def * = (id, name, mail, tel, createDate) <> (Member.tupled, Member.unapply)
}

object MemberDAO {
  lazy val memberQuery = TableQuery[MemberTable]

  def read(id: Long)(implicit session: Session) =
    memberQuery.filter(_.id === id).firstOption

  def readAll(implicit session: Session) =
    memberQuery.list

  def readNameLike(pattern: String)(implicit session: Session) =
    memberQuery.filter(_.name like pattern).list

  def create(member: Member)(implicit session: Session) =
    memberQuery.insert(member)

  def update(member: Member)(implicit session: Session) =
    memberQuery.filter(_.id === member.id).update(member)

  def delete(id: Long)(implicit session: Session) =
    memberQuery.filter(_.id === id).delete
}
