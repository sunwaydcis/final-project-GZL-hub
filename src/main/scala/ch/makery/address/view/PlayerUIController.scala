package ch.makery.address.controller

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{Label, ListView}
import javafx.scene.image.{Image, ImageView}
import javafx.collections.FXCollections
import ch.makery.address.model.{Character, SelectedCharacter}
import javafx.scene.Parent
import javafx.stage.Stage

class PlayerUIController {

  @FXML
  private var avatarImageView: ImageView = _
  @FXML
  private var cashLabel: Label = _
  @FXML
  private var bankLabel: Label = _
  @FXML
  private var debtLabel: Label = _
  @FXML
  private var cargoListView: ListView[String] = _

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
      val cargoItems = character.caravan.items.map(item => s"${item.quantity} ${item.name}")
      cargoListView.setItems(FXCollections.observableArrayList(cargoItems: _*))
      // Set the avatar image
      val imagePath = s"/assets/avatar1.png" // Adjusted path
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
  def handleGoToMarket(): Unit = {
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/market.fxml"))
      val root = loader.load[Parent]
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