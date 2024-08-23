package ch.makery.hangman.view

import ch.makery.hangman.MainApp.{showHangmanGame, showRules, showDatabaseOverview}
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class RootLayoutController {

  def handleDatabaseClick() = {
    showDatabaseOverview()
  }

  def handleRulesClick() = {
    showRules()
  }

  def handlePlayHangman() = {
    showHangmanGame()
  }
}