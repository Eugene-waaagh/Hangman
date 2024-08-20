package ch.makery.hangman

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

  val rootResource = getClass.getResource("/ch/makery/hangman/view/RootLayout.fxml")

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
    val resource = getClass.getResource("/view/Welcome.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showHangmanGame(): Unit = {
//    val resource = getClass.getResource("/ch/makery/hangman/view/HangmanGame.fxml")
//    val loader = new FXMLLoader(resource, NoDependencyResolver)
//    loader.load();
//    val roots = loader.getRoot[jfxs.layout.AnchorPane]
//    this.roots.setCenter(roots)
  }

  showWelcome()
}