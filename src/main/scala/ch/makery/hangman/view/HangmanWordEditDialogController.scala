package ch.makery.hangman.view

import ch.makery.hangman.model.Word
import scalafx.event.ActionEvent
import scalafx.scene.control.TextField
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml
import scalafx.scene.control.{Alert, Label, TableColumn, TextField}
import scalafx.scene.control.Alert.AlertType
import java.util.regex.Pattern

@sfxml
class HangmanWordEditDialogController (
                                      private val wordField : TextField,
                                      private val firstHintField : TextField,
                                      private val secondHintField : TextField
                                      ){

  var         dialogStage : Stage  = null
  private var _word       : Word   = null
  var         okClicked            = false

  def word = _word

  def word_=(x : Word) {
    _word = x

    wordField.text = _word.word.value
    firstHintField.text = _word.firstHint.value
    secondHintField.text = _word.secondHint.value
  }

  def handleOk(action :ActionEvent){

    if (isInputValid()) {
      _word.word.value = wordField.text.value.toUpperCase
      _word.firstHint.value = firstHintField.text.value
      _word.secondHint.value = secondHintField.text.value

      okClicked = true;
      dialogStage.close()
    }
  }

  def isInputValid() : Boolean = {
    var errorMessage = ""

    if (wordField.text.value == null || wordField.text.value.length == 0) {
      errorMessage += "No valid word!\n"
    }
    if (firstHintField.text.value == null || firstHintField.text.value.length == 0) {
      errorMessage += "No valid first hint!\n"
    }
    if (secondHintField.text.value == null || secondHintField.text.value.length == 0) {
      errorMessage += "No valid second hint!\n"
    }

    val userInput: String = wordField.text.value
    if (!(Pattern.matches("[a-zA-Z]+", userInput))) {
      errorMessage += "Word must contain only letters!\n"
    }

    if (errorMessage.length == 0) {
      return true
    } else {
      // Show the error message.
      val alert = new Alert(AlertType.Error){
        initOwner(dialogStage)
        title       = "Invalid Fields"
        headerText  = "Please correct invalid fields"
        contentText = errorMessage
      }.showAndWait()

      return false
    }
  }

  def handleCancel(action :ActionEvent) {
    dialogStage.close();
  }

}