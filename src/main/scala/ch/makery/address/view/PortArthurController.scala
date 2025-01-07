package ch.makery.address.view

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, Label}
import javafx.stage.Stage
import ch.makery.address.controller.MarketController
import ch.makery.address.model.GameState

class PortArthurController {

  @FXML
  private var setCourseButton: Button = _

  @FXML
  private var marketPricesLabel: Label = _

  @FXML
  private def handleGoToBank(): Unit = {
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/bank.fxml"))
      val root = loader.load[Parent]
      val stage = setCourseButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  @FXML
  private def handleGoToMoneylender(): Unit = {
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/moneylender.fxml"))
      val root = loader.load[Parent]
      val stage = setCourseButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  @FXML
  private def handleGoToMarket(): Unit = {
    GameState.updateMarketPrices("Port Arthur")
    updateMarketPricesUI()
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/market.fxml"))
      val root = loader.load[Parent]
      val stage = setCourseButton.getScene.getWindow.asInstanceOf[Stage]
      val scene = stage.getScene
      scene.setRoot(root)

      // Pass the updated prices to the MarketController
      val marketController = loader.getController[MarketController]
      marketController.setMarketPrices(GameState.getCurrentMarketPrices)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  @FXML
  private def handleSetCourse(): Unit = {
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/selectLocation.fxml"))
      val root = loader.load[Parent]
      val stage = setCourseButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  private def updateMarketPricesUI(): Unit = {
    val prices = GameState.getCurrentMarketPrices
    marketPricesLabel.setText(prices.map { case (item, price) => s"$item: $$price" }.mkString("\n"))
  }
}