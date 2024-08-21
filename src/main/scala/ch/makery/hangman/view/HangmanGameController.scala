package ch.makery.hangman.view

import scalafx.scene.control.{Alert, Label, TableColumn, TableView, TextField}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.StringProperty
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.control.Alert.AlertType

class HangmanGameController(
                           private val wordLabel : Label,
                           private val firstHintLabel : Label,
                           private val secondHintLabel : Label,
                           private val chancesLabel : Label,
                           private val hangmanField : TextField
                           )