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
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class HelpUIController implements Initializable {

	
	Media themeSound = new Media(getClass().getResource("../audio/homeUIsound.mp3").toExternalForm());
	MediaPlayer helpBackgroundMusic = new MediaPlayer(themeSound);

	private AudioClip buttonClickSound;

	public HelpUIController() {
		buttonClickSound = new AudioClip(getClass().getResource("../audio/bulletreload.mp3").toExternalForm());
	}
	
	 @FXML
	 void processController(ActionEvent event) throws IOException {
		 if(Onoff.bsSound) {
	    		buttonClickSound.play();
	    	}
		 helpBackgroundMusic.stop();
		 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.hide();

         // Create a new stage for the game
         Stage HelpStage = new Stage();
         Parent root = FXMLLoader.load(getClass().getResource("Control.fxml"));
         Scene scene = new Scene(root);
         Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
         HelpStage.getIcons().add(icon);
         HelpStage.setTitle("NovaStrike");
         HelpStage.setScene(scene);
         HelpStage.setResizable(false);
         HelpStage.show();
	    }

    @FXML
    void processBack(ActionEvent event) throws IOException {
    	
    	if(Onoff.bsSound) {
    		buttonClickSound.play();
    	}
    	
    	helpBackgroundMusic.stop();
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();

        // Create a new stage for the game
        Stage HelpStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("homeUI.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
        HelpStage.getIcons().add(icon);
        HelpStage.setTitle("NovaStrike");
        HelpStage.setScene(scene);
        HelpStage.setResizable(false);
        HelpStage.show();
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if(Onoff.bgSound) {
			helpBackgroundMusic.play();
		}
	}

}
