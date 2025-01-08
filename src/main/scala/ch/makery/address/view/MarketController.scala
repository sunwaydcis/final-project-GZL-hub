package ch.makery.address.controller

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{Button, Label, TableColumn, TableView, TextField}
import javafx.collections.ObservableList
import javafx.scene.Parent
import javafx.stage.Stage
import ch.makery.address.model.{Character, GameState, Item, MarketState, SelectedCharacter}
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

  def setMarketPrices(prices: Map[String, Double]): Unit = {
    items.clear()
    prices.foreach { case (name, price) =>
      items.add(new Item(name, price, 0)) // Assuming quantity is 0 initially
    }
    itemTableView.refresh()
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
        if (character.cash >= totalCost.toInt) {
          if (selectedItem.quantity >= quantity) {
            if (character.caravan.currentSize + quantity <= character.caravan.maxSize) {
              val updatedItem = selectedItem.copy(quantity = selectedItem.quantity - quantity)
              items.set(items.indexOf(selectedItem), updatedItem)
              val itemInCaravan = character.caravan.items.find(_.name == selectedItem.name)
              val updatedCaravanItems = itemInCaravan match {
                case Some(item) => character.caravan.items.map {
                  case i if i.name == item.name => i.copy(quantity = i.quantity + quantity)
                  case i => i
                }
                case None => character.caravan.items :+ selectedItem.copy(quantity = quantity)
              }
              val updatedCharacter = character.copy(
                cash = character.cash - totalCost.toInt,
                caravan = character.caravan.copy(
                  items = updatedCaravanItems,
                  currentSize = character.caravan.currentSize + quantity
                )
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
      selectedCharacter.foreach { character =>
        val itemInCaravan = character.caravan.items.find(_.name == selectedItem.name)
        itemInCaravan match {
          case Some(item) if item.quantity >= quantity =>
            val updatedItem = selectedItem.copy(quantity = selectedItem.quantity + quantity)
            items.set(items.indexOf(selectedItem), updatedItem)
            val updatedCharacter = character.copy(
              cash = character.cash + (selectedItem.price * quantity).toInt,
              caravan = character.caravan.copy(
                items = character.caravan.items.map {
                  case i if i.name == item.name => i.copy(quantity = i.quantity - quantity)
                  case i => i
                },
                currentSize = character.caravan.currentSize - quantity
              )
            )
            SelectedCharacter.character = Some(updatedCharacter)
            selectedCharacter = Some(updatedCharacter)
            updateCharacterStats()
            itemTableView.refresh()
            errorMessageLabel.setText("") // Clear any previous error message
          case Some(_) =>
            errorMessageLabel.setText(s"Not enough ${selectedItem.name} in stock.")
            errorMessageLabel.setStyle("-fx-text-fill: red;")
          case None =>
            errorMessageLabel.setText(s"No ${selectedItem.name} in stock.")
            errorMessageLabel.setStyle("-fx-text-fill: red;")
        }
      }
    }
  }

  @FXML
  def handleBack(): Unit = {
    GameState.goBack()
    val fxmlFile = GameState.getCurrentCity match {
      case "Port Arthur" => "/ch/makery/address/view/portArthur.fxml"
      case "Tai-Pan" => "/ch/makery/address/view/taiPan.fxml"
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
}