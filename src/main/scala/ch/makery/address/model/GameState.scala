package ch.makery.address.model

import scala.collection.immutable.ListMap
import scala.collection.mutable.Stack

object GameState {
  private var turn: Int = 0
  private var currentCity: String = "Starting City"
  private var startingCity: String = "Starting City" // Added starting city
  private val cityHistory: Stack[String] = Stack()

  private val marketPrices: Map[String, Map[String, Double]] = Map(
    "Port Arthur" -> ListMap(
      "Tea" -> 15.0,
      "Iron Ore" -> 100.0,
      "Parchment" -> 150.0,
      "Silk" -> 700.0,
      "Gunpowder" -> 2750.0,
      "Spices" -> 3500.0,
      "Angel Dust" -> 11000.0
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
    if (currentCity != location) {
      cityHistory.push(currentCity)
      currentCity = location
    }
    val prices = marketPrices.getOrElse(location, Map())
    println(s"Updated market prices for $location: $prices")
  }

  def getCurrentMarketPrices: Map[String, Double] = {
    marketPrices.getOrElse(currentCity, Map())
  }

  def goBack(): Unit = {
    if (cityHistory.nonEmpty) {
      val previousCity = cityHistory.pop()
      if (marketPrices.contains(previousCity)) {
        currentCity = previousCity
      }
    }
    println(s"Current city after going back: $currentCity")
  }

  def getCurrentCity: String = currentCity

  def getStartingCity: String = startingCity // Added method to get starting city
}