package ch.makery.address.model

import scala.collection.immutable.ListMap
import scala.collection.mutable.Stack

object GameState {
  private var turn: Int = 0
  private var currentCity: String = "Starting City"
  private var startingCity: String = "Starting City"
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
    "Tai-Pan" -> ListMap(
      "Tea" -> 15.0,
      "Iron Ore" -> 75.0,
      "Parchment" -> 200.0,
      "Silk" -> 900.0,
      "Gunpowder" -> 2000.0,
      "Spices" -> 3500.0,
      "Angel Dust" -> 15000.0
    ),
    "Edamame" -> ListMap(
      "Tea" -> 17.0,
      "Iron Ore" -> 50.0,
      "Parchment" -> 100.0,
      "Silk" -> 1500.0,
      "Gunpowder" -> 3000.0,
      "Spices" -> 5000.0,
      "Angel Dust" -> 9000.0
    ),
    "Lama-Sut" -> ListMap(
      "Tea" -> 15.0,
      "Iron Ore" -> 25.0,
      "Parchment" -> 400.0,
      "Silk" -> 700.0,
      "Gunpowder" -> 2500.0,
      "Spices" -> 2100.0,
      "Angel Dust" -> 25000.0
    ),
    "Kingston" -> ListMap("Item1" -> 14.0, "Item2" -> 24.0)
  )

  def consumeTurn(): Unit = {
    turn += 1
    increaseDebt()
    applyInterest()
  }

  private def increaseDebt(): Unit = {
    SelectedCharacter.character.foreach { character =>
      val increasedDebt = (character.debt * 1.1).toInt
      SelectedCharacter.character = Some(character.copy(debt = increasedDebt))
    }
  }

  private def applyInterest(): Unit = {
    SelectedCharacter.character.foreach { character =>
      val interest = (character.bank * 0.05).toInt
      val updatedCharacter = character.copy(bank = character.bank + interest)
      SelectedCharacter.character = Some(updatedCharacter)
    }
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

  def getStartingCity: String = startingCity

  def getTurn: Int = turn
}