package ch.makery.hangman.view

import ch.makery.hangman.model.Word
import scalafx.scene.control.{Alert, Label, TableColumn, TableView, TextArea, TextField}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.StringProperty
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.control.Alert.AlertType
import javafx.scene.control.Button


import scala.util.Random

@sfxml
class HangmanGameController(
                             private val wordLabel : Label,
                             private val firstHintLabel : Label,
                             private val secondHintLabel : Label,
                             private val chancesLabel : Label,
                             private val hangmanField : TextArea,

                             private val buttonA: Button,
                             private val buttonB: Button,
                             private val buttonC: Button,
                             private val buttonD: Button,
                             private val buttonE: Button,
                             private val buttonF: Button,
                             private val buttonG: Button,
                             private val buttonH: Button,
                             private val buttonI: Button,
                             private val buttonJ: Button,
                             private val buttonK: Button,
                             private val buttonL: Button,
                             private val buttonM: Button,
                             private val buttonN: Button,
                             private val buttonO: Button,
                             private val buttonP: Button,
                             private val buttonQ: Button,
                             private val buttonR: Button,
                             private val buttonS: Button,
                             private val buttonT: Button,
                             private val buttonU: Button,
                             private val buttonV: Button,
                             private val buttonW: Button,
                             private val buttonX: Button,
                             private val buttonY: Button,
                             private val buttonZ: Button
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
      val button = actionEvent.getSource.asInstanceOf[Button]
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
      checkGameStatus()
    }

    def checkGameStatus(): Unit = {
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
