package ch.makery.address.view

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, Label, Alert, ButtonType}
import javafx.scene.image.{Image, ImageView}
import javafx.stage.Stage
import ch.makery.address.controller.MarketController
import ch.makery.address.model.{Character, GameState, SelectedCharacter}

class TaiPanController {

  @FXML
  private var setCourseButton: Button = _

  @FXML
  private var marketPricesLabel: Label = _

  @FXML
  private var cashLabel: Label = _

  @FXML
  private var bankLabel: Label = _

  @FXML
  private var debtLabel: Label = _

  @FXML
  private var cargoSizeLabel: Label = _

  @FXML
  private var avatarImageView: ImageView = _

  @FXML
  private var turnCounterLabel: Label = _

  private var selectedCharacter: Option[Character] = _

  def initialize(): Unit = {
    selectedCharacter = SelectedCharacter.character
    updateCharacterStats()
    updateTurnCounter()
  }

  private def updateCharacterStats(): Unit = {
    selectedCharacter.foreach { character =>
      cashLabel.setText(s"Cash: ${character.cash}")
      bankLabel.setText(s"Bank: ${character.bank}")
      debtLabel.setText(s"Debt: ${character.debt}")
      cargoSizeLabel.setText(s"Cargo Size: ${character.caravan.currentSize}/${character.caravan.maxSize}")
      val imagePath = s"/assets/${character.name.toLowerCase}.png"
      val imageStream = getClass.getResourceAsStream(imagePath)
      if (imageStream != null) {
        val avatarImage = new Image(imageStream)
        avatarImageView.setImage(avatarImage)
      }
    }
  }

  private def updateTurnCounter(): Unit = {
    turnCounterLabel.setText(s"Turn: ${GameState.getTurn}")
  }

  @FXML
  private def handleGoToBank(): Unit = {
    GameState.updateMarketPrices("Tai-Pan")
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/bank.fxml"))
      val root = loader.load[Parent]
      val stage = setCourseButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  @FXML
  private def handleGoToMoneylender(): Unit = {
    GameState.updateMarketPrices("Tai-Pan")
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/moneylender.fxml"))
      val root = loader.load[Parent]
      val stage = setCourseButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  @FXML
  private def handleGoToMarket(): Unit = {
    GameState.updateMarketPrices("Tai-Pan")
    updateMarketPricesUI()
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/market.fxml"))
      val root = loader.load[Parent]
      val stage = setCourseButton.getScene.getWindow.asInstanceOf[Stage]
      val scene = stage.getScene
      scene.setRoot(root)

      val marketController = loader.getController[MarketController]
      marketController.setMarketPrices(GameState.getCurrentMarketPrices, GameState.getCurrentMarketQuantities)    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  @FXML
  private def handleSetCourse(): Unit = {
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/selectLocation.fxml"))
      val root = loader.load[Parent]
      val stage = setCourseButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  private def updateMarketPricesUI(): Unit = {
    val prices = GameState.getCurrentMarketPrices
    marketPricesLabel.setText(prices.map { case (item, price) => s"$item: $$price" }.mkString("\n"))
  }

  def showWinningAlert(): Unit = {
    val alert = new Alert(Alert.AlertType.CONFIRMATION)
    alert.setTitle("Congratulations!")
    alert.setHeaderText("You have reached a net worth of 1,000,000!")
    alert.setContentText("Do you want to continue playing or exit the game?")

    val continueButton = new ButtonType("Continue")
    val exitButton = new ButtonType("Exit")
    alert.getButtonTypes.setAll(continueButton, exitButton)

    val result = alert.showAndWait()
    if (result.isPresent && result.get == exitButton) {
      val stage = alert.getDialogPane.getScene.getWindow.asInstanceOf[Stage]
      stage.close()
    }
  }
}