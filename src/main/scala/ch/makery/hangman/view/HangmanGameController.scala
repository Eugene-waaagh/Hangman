package ch.makery.hangman.view

import ch.makery.hangman.model.Word

import scalafx.scene.control.{Alert, Label, TableColumn, TableView, TextField}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.StringProperty
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.control.Alert.AlertType

class HangmanGameController(
                             private val wordLabel : Label,
                             private val firstHintLabel : Label,
                             private val secondHintLabel : Label,
                             private val chancesLabel : Label,
                             private val hangmanField : TextField
                           ) {

  //Initializing the game things
  val chances: Int = 6
  val wrongs: Int = 0
  var currentWord: Word = _
  //This one is for hiding the word, when the alphabet is not revealed
  var hiddenWord: StringProperty = new StringProperty("")
  val alphabetButtons = List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

  wordLabel.text <== hiddenWord

  def handleGameStart(actionEvent: ActionEvent) = {
    //Get word from the database
    val words = Word.getAllWords


  }

  def drawHangman(wrongCounter: Int): String = {
    wrongCounter match {
      case 0 => "  +---+\n  |   |\n      |\n      |\n      |\n      |\n========="
      case 1 => "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n========="
      case 2 => "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n========="
      case 3 => "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n========="
      case 4 => "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n========="
      case 5 => "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n========="
      case 6 => "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="
    }
  }

}

//