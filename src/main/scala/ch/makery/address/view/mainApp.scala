package ch.makery.address.view

import javafx.fxml.FXMLLoader
import javafx.scene.layout.AnchorPane
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.Pane

object MyApp extends JFXApp3:

  override def start(): Unit =
    val root: AnchorPane = FXMLLoader.load(getClass.getResource("/ch/makery/address/view/mainmenu.fxml"))
    stage = new PrimaryStage:
      title = "Trading Game"
      scene = new Scene(new Pane(root))

end MyApp