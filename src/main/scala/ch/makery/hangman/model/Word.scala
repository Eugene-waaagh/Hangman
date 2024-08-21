package ch.makery.hangman.model

import ch.makery.hangman.util.Database
import scalafx.beans.property.StringProperty
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class Word(val wordS: String) extends Database {
  def this() = this("")

  var word = new StringProperty(wordS)
  var firstHint = new StringProperty("some hint")
  var secondHint = new StringProperty("some hint")

  def save(): Try[Int] = {
    if (!(isExist)) {
      Try(DB autoCommit { implicit session =>
        sql"""
             insert into word (word, firstHint, secondHint) values
             (${word.value}, ${firstHint.value}, ${secondHint.value})
           """.update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
             update word
             set
              word = ${word.value},
              firstHint = ${firstHint.value},
              secondHint = ${secondHint.value}
           """.update.apply()
      })
    }
  }

  def delete(): Try[Int] = {
    if (isExist) {
      Try(DB autoCommit { implicit session =>
        sql"""
             delete from word where
             word = ${word.value}
          """.update.apply()
      })
    } else
      throw new Exception("Word doesn't exist")}

    def isExist : Boolean = {
      DB readOnly { implicit session =>
        sql"""
            select * from word where
            word = ${word.value}
         """.map(rs => rs.string("word")).single.apply()
      } match {
        case Some(x) => true
        case None => false
      }
    }
}


object Word extends Database {
  def apply (
              wordS: String,
              firstHintS: String,
              secondHintS: String
            ) : Word  = {

    new Word(wordS) {
      word.value = wordS
      firstHint.value = firstHintS
      secondHint.value = secondHintS
    }
  }

  def initializeTable(): Boolean = {
    DB autoCommit { implicit session =>
      sql"""
           create table word (
            id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
            word varchar(64),
            firstHint varchar(64),
            secondHint varchar(64)
           )
         """.execute.apply()
    }
  }

  def getAllWords : List[Word] = {
    DB readOnly { implicit session =>
      sql"select * from word".map(
            rs => Word(rs.string("word"),
            rs.string("firstHint"),
            rs.string("secondHint"))).list.apply()
    }
  }
}