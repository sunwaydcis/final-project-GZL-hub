package ch.makery.address.controller

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{Button, Label}
import javafx.scene.Parent
import javafx.stage.Stage
import ch.makery.address.model.{Character, SelectedCharacter}

class PlayerUIController {

  @FXML
  private var cashLabel: Label = _
  @FXML
  private var bankLabel: Label = _
  @FXML
  private var debtLabel: Label = _

  private var selectedCharacter: Option[Character] = _

  def initialize(): Unit = {
    // Retrieve the selected character from the singleton object
    selectedCharacter = SelectedCharacter.character
    updateCharacterStats()
  }

  private def updateCharacterStats(): Unit = {
    selectedCharacter.foreach { character =>
      cashLabel.setText(s"Cash: ${character.cash}")
      bankLabel.setText(s"Bank: ${character.bank}")
      debtLabel.setText(s"Debt: ${character.debt}")
    }
  }

  @FXML
  def handleGoToMarket(): Unit = {
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/market.fxml"))
      val root: Parent = loader.load()
      val stage = cashLabel.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  @FXML
  def handleGoToMoneylender(): Unit = {
    // Implement the logic to go to the moneylender
  }

  @FXML
  def handleGoToBank(): Unit = {
    // Implement the logic to go to the bank
  }
}