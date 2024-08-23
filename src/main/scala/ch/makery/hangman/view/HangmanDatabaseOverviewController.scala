package ch.makery.hangman.view

import ch.makery.hangman.model.Word
import ch.makery.hangman.MainApp
import scalafx.scene.control.{Alert, Label, TableColumn, TableView}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.StringProperty
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.control.Alert.AlertType

@sfxml
class HangmanDatabaseOverviewController(
                                private val wordTable : TableView[Word],
                                private val wordColumn : TableColumn[Word, String],
                                private val firstHintColumn : TableColumn[Word, String],
                                private val secondHintColumn : TableColumn[Word, String],
                              ) {

  //I can't delete from database somehow, but adding works
  def handleDeleteWord(action : ActionEvent) = {
    val selectedWord = wordTable.selectionModel().selectedItem.value
    if (selectedWord != null) {
      val result = selectedWord.delete()
      wordTable.items().remove(selectedWord);
    } else {
      // Nothing selected.
      val alert = new Alert(AlertType.Error){
        initOwner(MainApp.stage)
        title       = "No Selection"
        headerText  = "No Word Selected"
        contentText = "Please select a word in the table."
      }.showAndWait()
    }
  }

  def handleNewWord(action : ActionEvent) = {
    val word = new Word("")
    val okClicked = MainApp.showWordEditDialog(word);
    if (okClicked) {
      MainApp.wordData += word
      val result = word.save()
    }
  }

  //--------------------------------------------------------------------------------
  // initialize Table View display contents model
  wordTable.items = MainApp.wordData// initialize columns's cell values
  wordColumn.cellValueFactory = {
    _.value.word
  }
  firstHintColumn.cellValueFactory = {
    _.value.firstHint
  }
  secondHintColumn.cellValueFactory = {
    _.value.secondHint
  }

}