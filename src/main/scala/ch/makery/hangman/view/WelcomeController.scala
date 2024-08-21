package ch.makery.hangman.view

import ch.makery.hangman.MainApp
import javafx.scene.control.TextField
import scalafxml.core.macros.sfxml

@sfxml
class WelcomeController() {
  def handleGetStarted(): Unit = {
    MainApp.showHangmanGame()
  }

  def handleRules(): Unit = {

  }
}