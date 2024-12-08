import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.VBox
import javafx.fxml.FXMLLoader

object MyApp extends JFXApp3:

  override def start(): Unit =
    val root = FXMLLoader.load(getClass.getResource("fxml/playerUI.fxml"))
    stage = new PrimaryStage:
      title = "Trading Game"
      scene = new Scene(root)

end MyApp