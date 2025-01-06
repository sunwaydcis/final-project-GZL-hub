package ch.makery.address.model

import javafx.collections.{FXCollections, ObservableList}

object MarketState {
  val items: ObservableList[Item] = FXCollections.observableArrayList(
    Item("Tea", 15, 1000),
    Item("Iron Ore", 50, 1500),
    Item("Parchment", 150, 1000),
    Item("Silk", 900, 1000),
    Item("Gunpowder", 2500, 850),
    Item("Spices", 3500, 700),
    Item("Angel Dust", 10000, 500)
  )
}