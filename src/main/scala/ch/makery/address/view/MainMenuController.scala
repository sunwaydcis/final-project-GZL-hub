package ch.makery.address.controller

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Alert, Button}
import javafx.stage.Stage
import javafx.scene.control.Alert.AlertType

class MainMenuController {

  @FXML
  private var playButton: Button = _

  @FXML
  private var tutorialButton: Button = _

  @FXML
  private var quitButton: Button = _

  @FXML
  def handlePlayButtonAction(): Unit = {
    try {
      // Load the game scene
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/characterSelection.fxml"))
      val root = loader.load[Parent]
      val stage = playButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        // Show an error alert if the FXML file cannot be loaded
        val alert = new Alert(AlertType.ERROR)
        alert.setTitle("Error")
        alert.setHeaderText("Failed to load the game scene")
        alert.setContentText(e.getMessage)
        alert.showAndWait()
    }
  }

  @FXML
  def handleTutorialButtonAction(): Unit = {
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/tutorial.fxml"))
      val root = loader.load[Parent]
      val stage = tutorialButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        // Show an error alert if the FXML file cannot be loaded
        val alert = new Alert(AlertType.ERROR)
        alert.setTitle("Error")
        alert.setHeaderText("Failed to load the tutorial scene")
        alert.setContentText(e.getMessage)
        alert.showAndWait()
    }
  }

  @FXML
  def handleQuitButtonAction(): Unit = {
    // Close the application
    val stage = quitButton.getScene.getWindow.asInstanceOf[Stage]
    stage.close()
  }
}