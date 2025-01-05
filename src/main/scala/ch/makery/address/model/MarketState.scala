package ch.makery.address.model

import javafx.collections.{FXCollections, ObservableList}

object MarketState {
  val items: ObservableList[Item] = FXCollections.observableArrayList(
    Item("Apple", 10, 100),
    Item("Banana", 5, 200),
    Item("Orange", 8, 150),
    Item("Grapes", 12, 80),
    Item("Mango", 15, 50)
  )
}