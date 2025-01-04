package ch.makery.address.controller

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.stage.Stage
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import ch.makery.address.model.{Character, SelectedCharacter}

class CharacterSelectionController {

  @FXML
  private var characterButton: Button = _
  @FXML
  private var characterButton2: Button = _
  @FXML
  private var characterButton3: Button = _

  @FXML
  def handleCharacterSelection(): Unit = {
    try {
      val source = characterButton.getScene.getFocusOwner.asInstanceOf[Button]
      val selectedCharacter = source.getId match {
        case "characterButton" =>
          Character("Logan", 10000, 0, 0, "Logan is a seasoned caravan leader who once traversed the harshest routes, earning a reputation as seasoned trader")
        case "characterButton2" =>
          Character("Adia", 15000, 0, 9000, "Adia, a former noble turned mercenary, is as sharp with her mind as she is with her blade.")
        case "characterButton3" =>
          Character("Polin", 5000, 20000, 0, "Polin is a fresh face in the caravan trade, driven more by ambition than experience.")
      }

      // Store the selected character in the singleton object
      SelectedCharacter.character = Some(selectedCharacter)

      // Load the game scene
      val loader = new FXMLLoader(getClass.getResource("/ch/makery/address/view/playerUI.fxml"))
      val root = loader.load[Parent]
      val stage = characterButton.getScene.getWindow.asInstanceOf[Stage]
      stage.getScene.setRoot(root)
    } catch {
      case e: Exception =>
        // Show an error alert if the FXML file cannot be loaded
        val alert = new Alert(AlertType.ERROR)
        alert.setTitle("Error")
        alert.setHeaderText("Failed to load the game scene")
        alert.setContentText(e.getMessage)
        alert.showAndWait()
        e.printStackTrace() // Log the stack trace for debugging
    }
  }
}