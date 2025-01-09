package ch.makery.address.model

import ch.makery.address.controller.PlayerUIController
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
    "Kingston" -> ListMap(
      "Tea" -> 30.0,
      "Iron Ore" -> 250.0,
      "Parchment" -> 750.0,
      "Silk" -> 1500.0,
      "Gunpowder" -> 4000.0,
      "Spices" -> 5000.0,
      "Angel Dust" -> 35000.0
    )
  )

  private val marketQuantities: Map[String, Map[String, Int]] = Map(
    "Port Arthur" -> ListMap(
      "Tea" -> 250,
      "Iron Ore" -> 200,
      "Parchment" -> 200,
      "Silk" -> 150,
      "Gunpowder" -> 100,
      "Spices" -> 50,
      "Angel Dust" -> 20
    ),
    "Tai-Pan" -> ListMap(
      "Tea" -> 250,
      "Iron Ore" -> 250,
      "Parchment" -> 230,
      "Silk" -> 150,
      "Gunpowder" -> 50,
      "Spices" -> 25,
      "Angel Dust" -> 10
    ),
    "Edamame" -> ListMap(
      "Tea" -> 250,
      "Iron Ore" -> 250,
      "Parchment" -> 200,
      "Silk" -> 150,
      "Gunpowder" -> 75,
      "Spices" -> 50,
      "Angel Dust" -> 25
    ),
    "Lama-Sut" -> ListMap(
      "Tea" -> 250,
      "Iron Ore" -> 200,
      "Parchment" -> 150,
      "Silk" -> 125,
      "Gunpowder" -> 65,
      "Spices" -> 50,
      "Angel Dust" -> 45
    ),
    "Kingston" -> ListMap(
      "Tea" -> 300,
      "Iron Ore" -> 270,
      "Parchment" -> 250,
      "Silk" -> 200,
      "Gunpowder" -> 100,
      "Spices" -> 50,
      "Angel Dust" -> 25
    )
  )

  def consumeTurn(): Unit = {
    turn += 1
    increaseDebt()
    applyInterest()
    checkWinningCondition()
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

  private def checkWinningCondition(): Unit = {
    SelectedCharacter.character.foreach { character =>
      val netWorth = character.cash + character.bank - character.debt
      if (netWorth >= 1000000) {
        // Trigger the alert to the player
        new PlayerUIController().showWinningAlert()
      }
    }
  }

  def updateMarketPrices(location: String): Unit = {
    if (currentCity != location) {
      cityHistory.push(currentCity)
      currentCity = location
    }
    val prices = marketPrices.getOrElse(location, Map())
    val quantities = marketQuantities.getOrElse(location, Map())
    println(s"Updated market prices for $location: $prices")
    println(s"Updated market quantities for $location: $quantities")
  }

  def getCurrentMarketPrices: Map[String, Double] = {
    marketPrices.getOrElse(currentCity, Map())
  }

  def getCurrentMarketQuantities: Map[String, Int] = {
    marketQuantities.getOrElse(currentCity, Map())
  }

  def goBack(): Unit = {
    println(s"Current city after going back: $currentCity")
  }

  def getCurrentCity: String = currentCity

  def getStartingCity: String = startingCity

  def getTurn: Int = turn
}