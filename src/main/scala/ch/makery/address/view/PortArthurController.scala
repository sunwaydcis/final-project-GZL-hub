package ch.makery.address.view

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, Label}
import javafx.scene.image.{Image, ImageView}
import javafx.stage.Stage
import ch.makery.address.controller.MarketController
import ch.makery.address.model.{Character, GameState, SelectedCharacter}

class PortArthurController {

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
      cargoSizeLabel.setText(s"Cargo Size: ${character.caravan.currentSize}/${character.caravan.maxSize}")
      // Set the avatar image
      val imagePath = s"/assets/${character.name.toLowerCase}.png" // Use character name for the image path
      val imageStream = getClass.getResourceAsStream(imagePath)
      println(s"Loading image from path: $imagePath")
      if (imageStream != null) {
        val avatarImage = new Image(imageStream)
        avatarImageView.setImage(avatarImage)
      } else {
        println(s"Image not found: $imagePath")
      }
    }
  }

  @FXML
  private def handleGoToBank(): Unit = {
    GameState.updateMarketPrices("Port Arthur")
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
    GameState.updateMarketPrices("Port Arthur")
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
    GameState.updateMarketPrices("Port Arthur")
    updateMarketPricesUI()
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/market.fxml"))
      val root = loader.load[Parent]
      val stage = setCourseButton.getScene.getWindow.asInstanceOf[Stage]
      val scene = stage.getScene
      scene.setRoot(root)

      // Pass the updated prices to the MarketController
      val marketController = loader.getController[MarketController]
      marketController.setMarketPrices(GameState.getCurrentMarketPrices)
    } catch {
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
}