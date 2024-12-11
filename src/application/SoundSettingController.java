package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundSettingController implements Initializable {

    @FXML
    private Button bsonoff;

    @FXML
    private Button gsonoff;

    @FXML
    private Button bgonoff;

    @FXML
    private Button button1;

    @FXML
    private Button button10;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    private HomeUIController homelink = new HomeUIController();

    static String SELECTED_MUSIC;

    private boolean[] buttons = new boolean[10];
    

    

    private AudioClip chooseClickSound;

    private String[] sounds = {
        "../audio/menuUIsound.mp3",
        "../music/withoutme.mp3",
        "../music/looseyoureselfe.mp3",
        "../music/stilldre.mp3",
        "../music/badguy.mp3",
        "../music/smater.mp3",
        "../music/whatislove.mp3",
        "../music/coldplay.mp3",
        "../music/don'timpressme.mp3",
        "../music/kyayzuuparkyal.mp3"
    };

    private MediaPlayer[] mediaPlayers = new MediaPlayer[10];

    public SoundSettingController() {
        chooseClickSound = new AudioClip(getClass().getResource("../audio/buttonSound.wav").toExternalForm());
    }

    void changeButton(boolean ONOFF, Button onoff) {
        if (ONOFF) {
            onoff.setText("ON");
            onoff.getStyleClass().remove("red-button");
            onoff.getStyleClass().add("green-button");
        } else {
            onoff.setText("OFF");
            onoff.getStyleClass().remove("green-button");
            onoff.getStyleClass().add("red-button");
        }
    }

    @FXML
    void processBGSonoff(ActionEvent event) {
    	stopAllSounds();
        if (Onoff.bsSound) {
            chooseClickSound.play();
        }
        Onoff.bgSound = !Onoff.bgSound;
        changeButton(Onoff.bgSound, bgonoff);
    }

    @FXML
    void processBSonoff(ActionEvent event) {
        if (Onoff.bsSound) {
            chooseClickSound.play();
        }
        Onoff.bsSound = !Onoff.bsSound;
        changeButton(Onoff.bsSound, bsonoff);
    }

    @FXML
    void processGSonoff(ActionEvent event) {
        if (Onoff.bsSound) {
            chooseClickSound.play();
        }
        Onoff.gsSound = !Onoff.gsSound;
        changeButton(Onoff.gsSound, gsonoff);
    }

    @FXML
    void processBack(ActionEvent event) {
        stopAllSounds();
        try {
            homelink.processMenu(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processButton(int index) {
    	Onoff.choose = index;
    	updateButtonStyles(index);
    	clearSelect(index);
    	
        
        if (Onoff.bsSound) {
            chooseClickSound.play();
        }

        stopAllSounds();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (i == index);
        }

        if (mediaPlayers[index] == null) {
            Media choosedMusic = new Media(getClass().getResource(sounds[index]).toExternalForm());
            mediaPlayers[index] = new MediaPlayer(choosedMusic);
        }
        	
        SELECTED_MUSIC = sounds[index];
        if (Onoff.bgSound && buttons[index]) {
            mediaPlayers[index].play();
        }
        
        
        
    }
    
    private void clearSelect(int index) {
        Button[] buttonArray = {button1, button2, button3, button4, button5, button6, button7, button8, button9, button10};
    	for (int i = 0; i < buttonArray.length; i++) {
            if (i != index && buttonArray[i]!=null) {
            	
            	 buttonArray[i].getStyleClass().remove("green-button");
            } 
    	}
    }

    private void stopAllSounds() {
        for (int i = 0; i < mediaPlayers.length; i++) {
            if (mediaPlayers[i] != null) {
                mediaPlayers[i].stop();
            }
        }
    }

    private void updateButtonStyles(int activeIndex) {
        Button[] buttonArray = {button1, button2, button3, button4, button5, button6, button7, button8, button9, button10};

        for (int i = 0; i < buttonArray.length; i++) {
            if (i == activeIndex) {
              
            	buttonArray[i].setStyle("-fx-background-color: green;");
          } else {
           
        	  buttonArray[i].setStyle("-fx-background-color: #64719C;");
           }
        }
    }

    @FXML
    void processOne(ActionEvent event) {
        processButton(0);
    }

    @FXML
    void processTwo(ActionEvent event) {
        processButton(1);
    }

    @FXML
    void processThree(ActionEvent event) {
        processButton(2);
    }

    @FXML
    void processFour(ActionEvent event) {
        processButton(3);
    }

    @FXML
    void processFive(ActionEvent event) {
        processButton(4);
    }

    @FXML
    void processSix(ActionEvent event) {
        processButton(5);
    }

    @FXML
    void processSeven(ActionEvent event) {
        processButton(6);
    }

    @FXML
    void processEight(ActionEvent event) {
        processButton(7);
    }

    @FXML
    void processNine(ActionEvent event) {
        processButton(8);
    }

    @FXML
    void processTen(ActionEvent event) {
        processButton(9);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        changeButton(Onoff.bgSound, bgonoff);
        changeButton(Onoff.bsSound, bsonoff);
        changeButton(Onoff.gsSound, gsonoff);

        // Check which button corresponds to SELECTED_MUSIC and update styles accordingly
        if(Onoff.choose < 11) {
        	SELECTED_MUSIC = sounds[Onoff.choose];
        	processButton(Onoff.choose);
       }
        
    }
}
