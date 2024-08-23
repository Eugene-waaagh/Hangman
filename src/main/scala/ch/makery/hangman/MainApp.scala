package ch.makery.hangman

import ch.makery.hangman.model.Word
import ch.makery.hangman.util.Database
import ch.makery.hangman.view.HangmanWordEditDialogController

import scalafx.collections.ObservableBuffer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.Alert
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}


object MainApp extends JFXApp {

  private val rootResource = getClass.getResource("view/RootLayout.fxml")

  private val loader = new FXMLLoader(rootResource, NoDependencyResolver)

  loader.load()

  private val roots = loader.getRoot[jfxs.layout.BorderPane]

  stage = new PrimaryStage {
    title = "Hangman"
    //icons += new Image()
    scene = new Scene {
      root = roots
      }
    }

  def showWelcome(): Unit = {
    val resource = getClass.getResource("view/Welcome.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showHangmanGame(): Unit = {
    val resource = getClass.getResource("view/HangmanGame.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //I tried making it show HangmanRulesDialog.fxml but cannot work, so I change to alert
  def showRules(): Unit = {
    val rules =
      """
        |Hangman Game
        |
        |Rules:
        |1. Objective is to guess the hidden word
        |2. Gameplay is to click on the button on the screen one at a time
        |3. Win condition is when you successfully guessed the word
        |4. Lose condition is when you have no more chances left
        |5. Mechanics: Guess correct will reveal the character, no chance deducted
        |                       Guess wrong will deduct 1 chance from total chances
        |""".stripMargin

    val rulesInfo = new Alert(Alert.AlertType.Information)
    rulesInfo.setTitle("Hangman Game Rules")
    rulesInfo.setContentText(rules)
    rulesInfo.setHeaderText("Hangman Game Rules")
    rulesInfo.showAndWait()
  }



  def showDatabaseOverview(): Unit = {
    val resource = getClass.getResource("view/HangmanDatabaseOverview.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showWordEditDialog(word: Word): Boolean = {
    val resource = getClass.getResourceAsStream("view/HangmanWordEditDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[HangmanWordEditDialogController#Controller]

    val dialog = new Stage() {
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }
    control.dialogStage = dialog
    control.word = word
    dialog.showAndWait()
    control.okClicked
  }


  Database.setupDB()
  val wordData = new ObservableBuffer[Word]()

  wordData ++= Word.getAllWords


  showWelcome()



}