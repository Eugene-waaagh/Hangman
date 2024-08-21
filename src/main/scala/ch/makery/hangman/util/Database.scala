package ch.makery.hangman.util

import scalikejdbc._
import ch.makery.hangman.model.Word

trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  val dbURL = "jdbc:derby:myDB;create=true;";

  //initializing JDBC driver and connection pool
  Class.forName(derbyDriverClassname)

  ConnectionPool.singleton(dbURL, "me", "mine")

  //ad-hoc session provider on the REPL
  implicit val session = AutoSession
}

object Database extends Database {
  def setupDB(): Unit = {
    if (!hasDBInitialize)
      Word.initializeTable()
  }

  def hasDBInitialize: Boolean = {
    DB getTable "Word" match {
      case Some(x) => true
      case None => false
    }
  }
}