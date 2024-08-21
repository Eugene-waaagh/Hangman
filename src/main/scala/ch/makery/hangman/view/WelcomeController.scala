package ch.makery.hangman.view

import ch.makery.hangman.MainApp
import javafx.scene.control.TextField
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class WelcomeController() {
  def handleGetStarted(): Unit = {
    MainApp.showHangmanGame()
  }

  def handleRules(action : ActionEvent): Unit = {
    val okClicked = MainApp.showRules()
  }
}