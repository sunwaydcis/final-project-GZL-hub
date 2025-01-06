package ch.makery.address.controller

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{Button, Label, TextField}
import javafx.scene.Parent
import javafx.stage.Stage
import ch.makery.address.model.{Character, SelectedCharacter}
import scala.util.Random

class BankController {

  @FXML
  private var cashLabel: Label = _
  @FXML
  private var bankLabel: Label = _
  @FXML
  private var amountField: TextField = _
  @FXML
  private var messageLabel: Label = _
  @FXML
  private var dialogueLabel: Label = _
  @FXML
  private var backButton: Button = _

  private var selectedCharacter: Option[Character] = _

  def initialize(): Unit = {
    selectedCharacter = SelectedCharacter.character
    updateCharacterStats()
    generateDialogue()
  }

  private def updateCharacterStats(): Unit = {
    selectedCharacter.foreach { character =>
      cashLabel.setText(s"Cash: ${character.cash}")
      bankLabel.setText(s"Bank: ${character.bank}")
    }
  }

  private def generateDialogue(): Unit = {
    val dialogues = Seq(
      "Welcome to the Myzora Bank!",
      "How can I assist you today?",
      "Need to deposit or withdraw money?",
      "I heard you're a skilled trader.",
      "This city has a high demand for silk in December."
    )
    dialogueLabel.setText(dialogues(Random.nextInt(dialogues.length)))
  }

  @FXML
  def handleDeposit(): Unit = {
    val amount = amountField.getText.toInt
    selectedCharacter.foreach { character =>
      if (character.cash >= amount) {
        val updatedCharacter = character.copy(cash = character.cash - amount, bank = character.bank + amount)
        SelectedCharacter.character = Some(updatedCharacter)
        selectedCharacter = Some(updatedCharacter)
        updateCharacterStats()
        messageLabel.setText("Deposit successful.")
      } else {
        messageLabel.setText("Not enough cash to deposit.")
      }
    }
  }

  @FXML
  def handleWithdraw(): Unit = {
    val amount = amountField.getText.toInt
    selectedCharacter.foreach { character =>
      if (character.bank >= amount) {
        val updatedCharacter = character.copy(bank = character.bank - amount, cash = character.cash + amount)
        SelectedCharacter.character = Some(updatedCharacter)
        selectedCharacter = Some(updatedCharacter)
        updateCharacterStats()
        messageLabel.setText("Withdrawal successful.")
      } else {
        messageLabel.setText("Not enough money in the bank to withdraw.")
      }
    }
  }

  @FXML
  def handleBack(): Unit = {
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/playerUI.fxml"))
      val root: Parent = loader.load()
      val stage = backButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}