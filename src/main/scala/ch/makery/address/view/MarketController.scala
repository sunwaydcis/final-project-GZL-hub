package ch.makery.address.controller

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{Button, Label, TableColumn, TableView, TextField}
import javafx.collections.ObservableList
import javafx.scene.Parent
import javafx.stage.Stage
import ch.makery.address.model.{Character, Item, MarketState, SelectedCharacter}
import javafx.beans.property.SimpleStringProperty

class MarketController {

  @FXML
  private var itemTableView: TableView[Item] = _
  @FXML
  private var itemNameColumn: TableColumn[Item, String] = _
  @FXML
  private var itemPriceColumn: TableColumn[Item, String] = _
  @FXML
  private var itemQuantityColumn: TableColumn[Item, String] = _
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

  private val items: ObservableList[Item] = MarketState.items

  private var selectedCharacter: Option[Character] = _

  def initialize(): Unit = {
    selectedCharacter = SelectedCharacter.character
    updateCharacterStats()

    itemTableView.setItems(items)
    itemNameColumn.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.name))
    itemPriceColumn.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.price.toString))
    itemQuantityColumn.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.quantity.toString))

    itemTableView.getSelectionModel.selectedItemProperty.addListener((_, _, selectedItem) => {
      if (selectedItem != null) {
        itemNameLabel.setText(selectedItem.name)
        itemPriceLabel.setText(s"Price: ${selectedItem.price}")
      }
    })
  }

  private def updateCharacterStats(): Unit = {
    selectedCharacter.foreach { character =>
      cashLabel.setText(s"${character.cash}")
      bankLabel.setText(s"Bank: ${character.bank}")
      debtLabel.setText(s"Debt: ${character.debt}")
      cargoLabel.setText(s"Cargo: ${character.caravan.currentSize}/${character.caravan.maxSize}")
    }
  }

  @FXML
  private def handleBuy(): Unit = {
    val selectedItem = itemTableView.getSelectionModel.getSelectedItem
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
              itemTableView.refresh()
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
    val selectedItem = itemTableView.getSelectionModel.getSelectedItem
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
      itemTableView.refresh()
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