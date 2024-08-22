package ch.makery.hangman.view

import ch.makery.hangman.model.Word
import scalafx.scene.control.{Alert, Label, TableColumn, TableView, TextArea, TextField}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.StringProperty
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.control.Alert.AlertType

import scala.util.Random

@sfxml
class HangmanGameController(
                             private val wordLabel : Label,
                             private val firstHintLabel : Label,
                             private val secondHintLabel : Label,
                             private val chancesLabel : Label,
                             private val hangmanField : TextArea
                           ) {

  //Initializing the game things
  var chances: Int = 6
  var wrongs: Int = 0
  var currentWord: Word = _

  //This one is for hiding the word, when the alphabet is not revealed
  var hiddenWord: StringProperty = new StringProperty("")
  val alphabetButtons = List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

  wordLabel.text <== hiddenWord

  //Function to restart the game or change new word, everything reset
    def startGame() = {
      //Get word from the database
      val words = Word.getAllWords

      if(words.isEmpty){
        new Alert(AlertType.Warning, "No words in database").showAndWait()
      } else {
        //Generate random number first, then put inside the currentWord
        val random = new Random()
        currentWord = words(random.nextInt(words.length))

        //Hide the word
        hiddenWord.value = "_" * currentWord.word.value.length

        //Settings the rest of the labels
        firstHintLabel.text = currentWord.firstHint.value
        secondHintLabel.text = currentWord.secondHint.value
        chances = 6
        wrongs = 0
        chancesLabel.text = chances.toString

        hangmanField.text = drawHangman(wrongs)
      }

    }

  //The button to call the function above
    def handleGameStart(actionEvent: ActionEvent): Unit = {
      startGame()
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

    def handleLabelField(): Unit = {
      wordLabel.text = hiddenWord.value
      hangmanField.text = drawHangman(wrongs)
      chancesLabel.text = (chances - wrongs).toString
      firstHintLabel.text = currentWord.firstHint.value
      secondHintLabel.text = currentWord.secondHint.value
    }

    def checkGameStatus(): Unit = {
      if (chances - wrongs == 0) {
        new Alert(AlertType.Warning, s"You lost! The word is ${currentWord.word.value}").showAndWait()
        startGame()
      } else if (hiddenWord.value == currentWord.word.value) {
        new Alert(AlertType.None, "You won!").showAndWait()
        startGame()
      }
    }





}
