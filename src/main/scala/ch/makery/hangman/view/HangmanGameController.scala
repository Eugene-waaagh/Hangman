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
                             private val hangmanField : TextArea,

                             private val buttonA: javafx.scene.control.Button,
                             private val buttonB: javafx.scene.control.Button,
                             private val buttonC: javafx.scene.control.Button,
                             private val buttonD: javafx.scene.control.Button,
                             private val buttonE: javafx.scene.control.Button,
                             private val buttonF: javafx.scene.control.Button,
                             private val buttonG: javafx.scene.control.Button,
                             private val buttonH: javafx.scene.control.Button,
                             private val buttonI: javafx.scene.control.Button,
                             private val buttonJ: javafx.scene.control.Button,
                             private val buttonK: javafx.scene.control.Button,
                             private val buttonL: javafx.scene.control.Button,
                             private val buttonM: javafx.scene.control.Button,
                             private val buttonN: javafx.scene.control.Button,
                             private val buttonO: javafx.scene.control.Button,
                             private val buttonP: javafx.scene.control.Button,
                             private val buttonQ: javafx.scene.control.Button,
                             private val buttonR: javafx.scene.control.Button,
                             private val buttonS: javafx.scene.control.Button,
                             private val buttonT: javafx.scene.control.Button,
                             private val buttonU: javafx.scene.control.Button,
                             private val buttonV: javafx.scene.control.Button,
                             private val buttonW: javafx.scene.control.Button,
                             private val buttonX: javafx.scene.control.Button,
                             private val buttonY: javafx.scene.control.Button,
                             private val buttonZ: javafx.scene.control.Button
                           ) {

  val buttons = List(buttonA, buttonB, buttonC, buttonD, buttonE, buttonF, buttonG, buttonH, buttonI, buttonJ, buttonK,
    buttonL, buttonM, buttonN, buttonO, buttonP, buttonQ, buttonR, buttonS, buttonT, buttonU, buttonV, buttonW, buttonX, buttonY, buttonZ)

  def enableAllButtons(): Unit = {
    buttons.foreach(_.setDisable(false))
  }

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
      handleLabelField()
    }

  //The button to call the function above
    def handleGameStart(actionEvent: ActionEvent): Unit = {
      startGame()
      enableAllButtons()
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
      wordLabel.text.unbind()
      wordLabel.text = hiddenWord.value
      hangmanField.text = drawHangman(wrongs)
      chancesLabel.text = (chances - wrongs).toString
      firstHintLabel.text = currentWord.firstHint.value
      secondHintLabel.text = currentWord.secondHint.value
    }


    def handleButtonPress(actionEvent: ActionEvent): Unit = {
      val button = actionEvent.getSource.asInstanceOf[javafx.scene.control.Button]
      val guessedLetter = button.getText.charAt(0)

      //Disable the button (unable to press again) after it is pressed
      button.setDisable(true)

      if(currentWord.word.value.contains(guessedLetter.toString)) {
        for(i <- 0 until currentWord.word.value.length) {
          if(currentWord.word.value.charAt(i).toString == guessedLetter.toString) {
            hiddenWord.value = hiddenWord.value.updated(i, guessedLetter).toString
          }
        }
      } else {
        wrongs += 1
        chancesLabel.text = (chances - wrongs).toString
        hangmanField.text = drawHangman(wrongs)
      }

      handleLabelField()
      if (wrongs == chances) {
        new Alert(AlertType.Warning, s"You lost! The word is ${currentWord.word.value}").showAndWait()
        startGame()
        enableAllButtons()
      } else if (hiddenWord.value == currentWord.word.value) {
        new Alert(AlertType.Information, "You won!").showAndWait()
        startGame()
        enableAllButtons()
      }
    }

    //Start the game straightaway, when moved from welcomepage to HangmanGame
    def initialize(): Unit = {
      startGame()
    }

    initialize()
}
