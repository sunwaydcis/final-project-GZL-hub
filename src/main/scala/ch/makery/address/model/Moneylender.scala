package ch.makery.address.model

case class Moneylender(availableFunds: Double) {
  def updateFunds(amount: Double): Moneylender = {
    copy(availableFunds = this.availableFunds + amount)
  }
}