package ch.makery.address.model

case class Caravan(currentSize: Int, maxSize: Int = 100)

case class Character(name: String, cash: Int, bank: Int, debt: Int, caravan: Caravan)