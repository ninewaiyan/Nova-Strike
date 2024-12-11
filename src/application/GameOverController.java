package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class GameOverController implements Initializable{
	
	private AudioClip buttonClickSound;
	private AudioClip gameOverSound;
	
	Media themeSound = new Media(getClass().getResource("../audio/homeUIsound.mp3").toExternalForm());
	MediaPlayer gameoverBackgroundMusic = new MediaPlayer(themeSound);


    private HomeUIController homeLink = new HomeUIController();
    
     public GameOverController() {
		// TODO Auto-generated constructor stub
		buttonClickSound = new AudioClip(getClass().getResource("../audio/bulletreload.mp3").toExternalForm());
		gameOverSound = new AudioClip(getClass().getResource("../audio/gameOver.mp3").toExternalForm());
		

	}
     @FXML
    private Label lblRank;
     
    private Double skill = 0.0;

    @FXML
    private Label lbBulletUsed;

    @FXML
    private Label lbKill;

    @FXML
    private Label lbSkill;
    
  
    
    @FXML
    private Label lbTime;
    
    
    
    
    
    
    public void setGameOverData(int bulletsUsed, int kills,int longTime) {
    	
    	int skill = bulletsUsed/kills;
        lbBulletUsed.setText(String.valueOf(bulletsUsed));
        lbKill.setText(String.valueOf(kills));
        lbSkill.setText(skill + " per Kill"); // Replace "Skills info" with actual data if availablewsa
        
        if(kills >= 100 && skill < 8) {
        	lblRank.setText("LEGEND OF SPACE");
        }else if (kills >=80) {
        	lblRank.setText("MASTER OF SPACE");
        }else if (kills >= 40) {
        	lblRank.setText("SPACE DEFENDER");
        }else if(kills >= 25){
        	lblRank.setText("SPACE RANGER");
        }else {
        	lblRank.setText("SPACE WARRIOR");
        }
        
        int hours = longTime / 3600;
	    int minutes =  (longTime % 3600) / 60;
	    int seconds =  (longTime % 60);
	    String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	    lbTime.setText(timeFormatted);
	
        FileDatabase.writeRecord(kills, bulletsUsed,longTime);

    }
       

    @FXML
    void gameOverMenu(ActionEvent event) throws IOException {
    		
    	if(Onoff.bsSound) {
    		buttonClickSound.play();
    	}
    	gameoverBackgroundMusic.stop();
        homeLink.processMenu(event);
    }

    @FXML
    void gameOverStart(ActionEvent event) {
    	if(Onoff.bsSound) {
    		buttonClickSound.play();
    	}
    	gameoverBackgroundMusic.stop();
        homeLink.processGameStart(event);
    }

    @FXML
    void processExit(ActionEvent event) {
    	if(Onoff.bsSound) {
    		buttonClickSound.play();
    	}
        System.exit(0);
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setGameOverData(GamePanel.NUMBER_OF_BULLET, GamePanel.NUMBER_OF_KILLED,GamePanel.LONG_TIME);
		
		if(Onoff.bgSound) {
			gameoverBackgroundMusic.play();
		}
		
		if(Onoff.gsSound) {
			gameOverSound.play();
		}
	}
}
