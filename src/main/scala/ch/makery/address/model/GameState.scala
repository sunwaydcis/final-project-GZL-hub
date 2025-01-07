package ch.makery.address.model

import scala.collection.immutable.ListMap

object GameState {
  private var turn: Int = 0
  private var currentCity: String = "Starting City"
  private val marketPrices: Map[String, Map[String, Double]] = Map(
    "Port Arthur" -> ListMap(
      "Tea" -> 30.0,
      "Iron Ore" -> 100.0,
      "Parchment" -> 150.0,
      "Silk" -> 900.0,
      "Gunpowder" -> 2500.0,
      "Spices" -> 3500.0,
      "Angel Dust" -> 99999.0
    ),
    "Tai-Pan" -> ListMap("Item1" -> 15.0, "Item2" -> 25.0),
    "Edamame" -> ListMap("Item1" -> 12.0, "Item2" -> 22.0),
    "Lama-Sut" -> ListMap("Item1" -> 18.0, "Item2" -> 28.0),
    "Kingston" -> ListMap("Item1" -> 14.0, "Item2" -> 24.0)
  )

  def consumeTurn(): Unit = {
    turn += 1
  }

  def updateMarketPrices(location: String): Unit = {
    currentCity = location
    val prices = marketPrices.getOrElse(location, Map())
    // Logic to update the market prices in the UI or game state
    println(s"Updated market prices for $location: $prices")
  }

  def getCurrentMarketPrices: Map[String, Double] = {
    marketPrices.getOrElse(currentCity, Map())
  }
}