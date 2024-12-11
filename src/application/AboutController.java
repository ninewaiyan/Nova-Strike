package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class AboutController implements Initializable {

	Media themeSound = new Media(getClass().getResource("../audio/homeUIsound.mp3").toExternalForm());
	MediaPlayer aboutBackgroundMusic = new MediaPlayer(themeSound);

	private AudioClip buttonClickSound;

	public AboutController() {
		buttonClickSound = new AudioClip(getClass().getResource("../audio/bulletreload.mp3").toExternalForm());
	}
	
    @FXML
    void processBack(ActionEvent event) throws IOException {
    	if(Onoff.bsSound) {
			buttonClickSound.play();
		}
		aboutBackgroundMusic.stop();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.hide();

		// Create a new stage for the game
		Stage HomeStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("homeUI.fxml"));
		Scene scene = new Scene(root);
		Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
		HomeStage.getIcons().add(icon);
		HomeStage.setTitle("NovaStrike");
		HomeStage.setScene(scene);
		HomeStage.setResizable(false);
		HomeStage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		if(Onoff.bgSound) {
			aboutBackgroundMusic.play();
		}
		
	}

}
