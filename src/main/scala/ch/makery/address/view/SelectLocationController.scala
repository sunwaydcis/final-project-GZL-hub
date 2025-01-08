package ch.makery.address.view

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.stage.Stage
import ch.makery.address.model.GameState

class SelectLocationController {

  @FXML
  private var backButton: Button = _
  @FXML
  private var selectLocationButton: Button = _

  @FXML
  private def handleSelectPortArthur(): Unit = {
    selectLocation("Port Arthur")
  }

  @FXML
  private def handleSelectTaiPan(): Unit = {
    selectLocation("Tai-Pan")
  }

  @FXML
  private def handleSelectEdamame(): Unit = {
    selectLocation("Edamame")
  }

  @FXML
  private def handleSelectLamaSut(): Unit = {
    selectLocation("Lama-Sut")
  }

  @FXML
  private def handleSelectKingston(): Unit = {
    selectLocation("Kingston")
  }

  @FXML
  private def handleSelectStartingCity(): Unit = {
    val startingCity = GameState.getStartingCity
    selectLocation(startingCity)
  }

  @FXML
  private def handleBack(): Unit = {
    GameState.goBack()
    val fxmlFile = GameState.getCurrentCity match {
      case "Port Arthur" => "/ch/makery/address/view/portArthur.fxml"
      case "Tai-Pan" => "/ch/makery/address/view/taiPan.fxml"
      case "Edamame" => "/ch/makery/address/view/edamame.fxml"
      case "Lama-Sut" => "/ch/makery/address/view/lamaSut.fxml"
      case "Kingston" => "/ch/makery/address/view/kingston.fxml"
      case _ => "/ch/makery/address/view/playerUI.fxml"
    }
    try {
      val loader = new FXMLLoader(getClass.getResource(fxmlFile))
      val root = loader.load[Parent]
      val stage = backButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  private def selectLocation(location: String): Unit = {
    GameState.consumeTurn()
    GameState.updateMarketPrices(location)

    val fxmlFile = location match {
      case "Port Arthur" => "/ch/makery/address/view/portArthur.fxml"
      case "Tai-Pan" => "/ch/makery/address/view/taiPan.fxml"
      case "Edamame" => "/ch/makery/address/view/edamame.fxml"
      case "Lama-Sut" => "/ch/makery/address/view/lamaSut.fxml"
      case "Kingston" => "/ch/makery/address/view/kingston.fxml"
      case _ => "/ch/makery/address/view/playerUI.fxml"
    }

    try {
      val loader = new FXMLLoader(getClass.getResource(fxmlFile))
      val root = loader.load[Parent]
      val stage = selectLocationButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}