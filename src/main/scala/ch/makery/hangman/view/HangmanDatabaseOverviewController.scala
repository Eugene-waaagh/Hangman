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


  def handleDeleteWord(action : ActionEvent): Unit = {

  }

  def handleEditWord(action : ActionEvent): Unit = {

  }

  def handleNewWord(action : ActionEvent): Unit = {

  }

//  def handleDeleteWord(action : ActionEvent) = {
//    val selectedIndex = wordTable.selectionModel().selectedIndex.value
//    if (selectedIndex >= 0) {
//      wordTable.items().remove(selectedIndex);
//    } else {
//      // Nothing selected.
//      val alert = new Alert(AlertType.Error){
//        initOwner(MainApp.stage)
//        title       = "No Selection"
//        headerText  = "No Person Selected"
//        contentText = "Please select a person in the table."
//      }.showAndWait()
//    }
//  }

//  def handleNewWord(action : ActionEvent) = {
//    val person = new Person("","")
//    val okClicked = MainApp.showPersonEditDialog(Word);
//    if (okClicked) {
//      MainApp.personData += person
//    }
//  }
//  def handleEditPerson(action : ActionEvent) = {
//    val selectedPerson = personTable.selectionModel().selectedItem.value
//    if (selectedPerson != null) {
//      val okClicked = MainApp.showPersonEditDialog(selectedPerson)
//
//      if (okClicked) showPersonDetails(Some(selectedPerson))
//
//    } else {
//      // Nothing selected.
//      val alert = new Alert(Alert.AlertType.Warning){
//        initOwner(MainApp.stage)
//        title       = "No Selection"
//        headerText  = "No Person Selected"
//        contentText = "Please select a person in the table."
//      }.showAndWait()
//    }
//  }

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