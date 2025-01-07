package ch.makery.address.model

case class Item(name: String, price: Double, var quantity: Int)

case class Caravan(var currentSize: Int, maxSize: Int = 100, var items: List[Item] = List())