package ch.makery.hangman.view

import scalafx.event.ActionEvent
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml


@sfxml
class HangmanRulesDialogController(){
  var dialogStage : Stage = null
  var okClicked = false

  def handleOk(action :ActionEvent): Unit = {
    dialogStage.close()
  }
}