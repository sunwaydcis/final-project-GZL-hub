package ch.makery.address.model

case class Caravan(maxSize: Int = 100, var currentSize: Int = 0) {
  def canAddItem(quantity: Int): Boolean = {
    currentSize + quantity <= maxSize
  }

  def addItem(quantity: Int): Unit = {
    if (canAddItem(quantity)) {
      currentSize += quantity
    }
  }

  def removeItem(quantity: Int): Unit = {
    currentSize = (currentSize - quantity).max(0)
  }
}