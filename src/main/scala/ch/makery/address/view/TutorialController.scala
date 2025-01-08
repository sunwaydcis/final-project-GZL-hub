package ch.makery.address.controller

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.stage.Stage
import javafx.scene.control.Button

class TutorialController {

  @FXML
  private var backButton: Button = _

  @FXML
  private def handleBackButtonAction(): Unit = {
    try {
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/mainmenu.fxml"))
      val root = loader.load[Parent]
      val stage = backButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}