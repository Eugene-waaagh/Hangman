package ch.makery.hangman

import ch.makery.hangman.model.Word
import ch.makery.hangman.util.Database
import ch.makery.hangman.view.HangmanRulesDialogController
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.collections.ObservableBuffer
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}


object MainApp extends JFXApp {

  val rootResource = getClass.getResource("view/RootLayout.fxml")

  val loader = new FXMLLoader(rootResource, NoDependencyResolver)

  loader.load()

  val roots = loader.getRoot[jfxs.layout.BorderPane]

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

  def showRules(): Boolean = {
    val resource = getClass.getResourceAsStream("view/HangmanRulesDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[HangmanRulesDialogController#Controller]

    val dialog = new Stage() {
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene {
        //stylesheets += getClass.getResource("view/DarkTheme.css").toString
        root = roots2
      }
    }
    control.dialogStage = dialog
    dialog.showAndWait()
    control.okClicked
  }

  Database.setupDB()
  val wordData = new ObservableBuffer[Word]()

  wordData ++= Word.getAllWords


  showWelcome()

}