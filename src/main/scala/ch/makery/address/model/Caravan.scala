package ch.makery.address.model

case class Item(name: String, price: Int, quantity: Int)

case class Caravan(currentSize: Int, maxSize: Int = 100, items: List[Item] = List())