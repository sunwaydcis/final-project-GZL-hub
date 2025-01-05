package ch.makery.address.controller

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{Alert, Button, Label, ListView, TextField}
import javafx.collections.{FXCollections, ObservableList}
import javafx.scene.Parent
import javafx.stage.Stage
import ch.makery.address.model.{Character, Item, SelectedCharacter}

class MarketController {

  @FXML
  private var itemListView: ListView[Item] = _
  @FXML
  private var itemNameLabel: Label = _
  @FXML
  private var itemPriceLabel: Label = _
  @FXML
  private var itemQuantityField: TextField = _
  @FXML
  private var buyButton: Button = _
  @FXML
  private var sellButton: Button = _

  @FXML
  private var cashLabel: Label = _
  @FXML
  private var bankLabel: Label = _
  @FXML
  private var debtLabel: Label = _
  @FXML
  private var cargoLabel: Label = _

  @FXML
  private var backButton: Button = _

  @FXML
  private var errorMessageLabel: Label = _

  private val items: ObservableList[Item] = FXCollections.observableArrayList(
    Item("Tea", 10, 2500),
    Item("Fish", 20, 2000),
    Item("Parchment", 100, 1000),
    Item("Silk", 500, 1000),
    Item("Spices", 1250, 500),
    Item("Gunpowder", 5000, 500),
    Item("Angel Dust", 10000, 100)
  )

  private var selectedCharacter: Option[Character] = _

  def initialize(): Unit = {
    selectedCharacter = SelectedCharacter.character
    updateCharacterStats()

    itemListView.setItems(items)
    itemListView.getSelectionModel.selectedItemProperty.addListener((_, _, selectedItem) => {
      if (selectedItem != null) {
        itemNameLabel.setText(selectedItem.name)
        itemPriceLabel.setText(s"Price: ${selectedItem.price}")
      }
    })
  }

  private def updateCharacterStats(): Unit = {
    selectedCharacter.foreach { character =>
      cashLabel.setText(s"Cash: ${character.cash}")
      bankLabel.setText(s"Bank: ${character.bank}")
      debtLabel.setText(s"Debt: ${character.debt}")
      cargoLabel.setText(s"Cargo: ${character.caravan.currentSize}/${character.caravan.maxSize}")
    }
  }

  @FXML
  private def handleBuy(): Unit = {
    val selectedItem = itemListView.getSelectionModel.getSelectedItem
    val quantity = itemQuantityField.getText.toInt
    if (selectedItem != null && quantity > 0) {
      selectedCharacter.foreach { character =>
        val totalCost = selectedItem.price * quantity
        if (character.cash >= totalCost) {
          if (selectedItem.quantity >= quantity) {
            if (character.caravan.currentSize + quantity <= character.caravan.maxSize) {
              val updatedItem = selectedItem.copy(quantity = selectedItem.quantity - quantity)
              items.set(items.indexOf(selectedItem), updatedItem)
              val updatedCharacter = character.copy(
                cash = character.cash - totalCost,
                caravan = character.caravan.copy(currentSize = character.caravan.currentSize + quantity)
              )
              SelectedCharacter.character = Some(updatedCharacter)
              selectedCharacter = Some(updatedCharacter)
              updateCharacterStats()
              itemListView.refresh()
              errorMessageLabel.setText("") // Clear any previous error message
            } else {
              errorMessageLabel.setText("Not enough cargo space available.")
              errorMessageLabel.setStyle("-fx-text-fill: red;")
            }
          } else {
            errorMessageLabel.setText("Not enough stock available.")
            errorMessageLabel.setStyle("-fx-text-fill: red;")
          }
        } else {
          errorMessageLabel.setText("You do not have enough cash to make this purchase.")
          errorMessageLabel.setStyle("-fx-text-fill: red;")
        }
      }
    }
  }

  @FXML
  private def handleSell(): Unit = {
    val selectedItem = itemListView.getSelectionModel.getSelectedItem
    val quantity = itemQuantityField.getText.toInt
    if (selectedItem != null && quantity > 0) {
      val updatedItem = selectedItem.copy(quantity = (selectedItem.quantity + quantity).min(200))
      items.set(items.indexOf(selectedItem), updatedItem)
      selectedCharacter.foreach { character =>
        val updatedCharacter = character.copy(
          cash = character.cash + (selectedItem.price * quantity),
          caravan = character.caravan.copy(currentSize = (character.caravan.currentSize - quantity).max(0))
        )
        SelectedCharacter.character = Some(updatedCharacter)
        selectedCharacter = Some(updatedCharacter)
        updateCharacterStats()
      }
      itemListView.refresh()
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