package ch.makery.address.controller

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{Label, ListView}
import javafx.scene.image.{Image, ImageView}
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
  private var cargoSizeLabel: Label = _

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
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/bank.fxml"))
      val root = loader.load[Parent]
      val stage = cashLabel.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def handlePurchaseItem(itemName: String, quantity: Int): Unit = {
    selectedCharacter.foreach { character =>
      // Logic to purchase the item and update the character's cargo
      val item = character.caravan.items.find(_.name == itemName)
      item.foreach { i =>
        i.quantity += quantity
        character.caravan.currentSize += quantity
      }
      // Update the character stats to refresh the cargo size
      updateCharacterStats()
    }
  }

  def handleRemoveItem(itemName: String, quantity: Int): Unit = {
    selectedCharacter.foreach { character =>
      // Logic to remove the item and update the character's cargo
      val item = character.caravan.items.find(_.name == itemName)
      item.foreach { i =>
        i.quantity -= quantity
        character.caravan.currentSize -= quantity
        if (i.quantity <= 0) {
          character.caravan.items = character.caravan.items.filterNot(_.name == itemName)
        }
      }
      // Update the character stats to refresh the cargo size
      updateCharacterStats()
    }
  }
}