package ch.makery.address.view

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, Label, TextField}
import javafx.stage.Stage
import ch.makery.address.model.{GameState, Moneylender, SelectedCharacter}

class MoneylenderController {

  @FXML
  private var availableFundsLabel: Label = _
  @FXML
  private var borrowAmountField: TextField = _
  @FXML
  private var repayAmountField: TextField = _
  @FXML
  private var errorMessageLabel: Label = _
  @FXML
  private var cashLabel: Label = _
  @FXML
  private var bankLabel: Label = _
  @FXML
  private var debtLabel: Label = _
  @FXML
  private var cargoSizeLabel: Label = _
  @FXML
  private var backButton: Button = _

  private var moneylender = Moneylender(availableFunds = 10000.0) // Example initial funds

  @FXML
  private def initialize(): Unit = {
    updateAvailableFunds()
    updateCharacterStats()
  }

  @FXML
  private def handleBorrow(): Unit = {
    val amountToBorrow = borrowAmountField.getText.toDouble
    SelectedCharacter.character.foreach { character =>
      if (character.debt > 0) {
        errorMessageLabel.setText("You cannot borrow more money until your existing debt is repaid.")
      } else if (amountToBorrow > 0 && amountToBorrow <= moneylender.availableFunds) {
        moneylender = moneylender.copy(availableFunds = moneylender.availableFunds - amountToBorrow)
        val updatedCharacter = character.copy(
          cash = character.cash + amountToBorrow.toInt,
          debt = character.debt + amountToBorrow.toInt
        )
        SelectedCharacter.character = Some(updatedCharacter)
        updateAvailableFunds()
        updateCharacterStats()
        errorMessageLabel.setText("") // Clear any previous error message
      } else {
        errorMessageLabel.setText("The moneylender does not have enough funds.")
      }
    }
  }

  @FXML
  private def handleRepay(): Unit = {
    val amountToRepay = repayAmountField.getText.toDouble
    SelectedCharacter.character.foreach { character =>
      if (amountToRepay > 0 && amountToRepay <= character.cash && amountToRepay <= character.debt) {
        moneylender = moneylender.copy(availableFunds = moneylender.availableFunds + amountToRepay)
        val updatedCharacter = character.copy(
          cash = character.cash - amountToRepay.toInt,
          debt = character.debt - amountToRepay.toInt
        )
        SelectedCharacter.character = Some(updatedCharacter)
        updateAvailableFunds()
        updateCharacterStats()
        errorMessageLabel.setText("") // Clear any previous error message
      } else {
        errorMessageLabel.setText("Invalid repayment amount.")
      }
    }
  }

  @FXML
  def handleBack(): Unit = {
    GameState.goBack()
    val fxmlFile = GameState.getCurrentCity match {
      case "Port Arthur" => "/ch/makery/address/view/portArthur.fxml"
      case "Tai-Pan" => "/ch/makery/address/view/taiPan.fxml"
      case "Edamame" => "/ch/makery/address/view/edamame.fxml"
      case "Lama-Sut" => "/ch/makery/address/view/lamaSut.fxml"
      case "Kingston" => "/ch/makery/address/view/kingston.fxml"
      case _ => "/ch/makery/address/view/playerUI.fxml"
    }
    try {
      val loader = new FXMLLoader(getClass.getResource(fxmlFile))
      val root: Parent = loader.load()
      val stage = backButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  private def updateAvailableFunds(): Unit = {
    availableFundsLabel.setText(f"${moneylender.availableFunds}%.2f")
  }

  private def updateCharacterStats(): Unit = {
    SelectedCharacter.character.foreach { character =>
      cashLabel.setText(s"${character.cash}")
      bankLabel.setText(s"${character.bank}")
      debtLabel.setText(s"${character.debt}")
      cargoSizeLabel.setText(s"${character.caravan.currentSize}/${character.caravan.maxSize}")
    }
  }
}