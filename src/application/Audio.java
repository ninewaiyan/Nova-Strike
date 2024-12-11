package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Audio {
	
	
	private AudioClip buttonClickSound;
	public Audio() {
        String soundPath = getClass().getResource("../audio/buttonSound.wav").toExternalForm();
        buttonClickSound = new AudioClip(soundPath);
    }

	
		
	}
  
  
  
		  

