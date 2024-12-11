package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class HistoryController implements Initializable{
	
	HomeUIController homeLink = new HomeUIController();
	
	private AudioClip buttonClickSound;
	private AudioClip chooseClickSound;

	Media themeSound = new Media(getClass().getResource("../audio/menuUIsound.mp3").toExternalForm());
	MediaPlayer HBackgroundMusic = new MediaPlayer(themeSound);

	public HistoryController() {
		// TODO Auto-generated constructor stub
		buttonClickSound = new AudioClip(getClass().getResource("../audio/bulletreload.mp3").toExternalForm());
		chooseClickSound = new AudioClip(getClass().getResource("../audio/buttonSound.wav").toExternalForm());

	}
	
	@FXML
    private Label lbSkill;

    @FXML
    private Label lbmatch;
    @FXML
    private Label lbBulletNumber;
    
    @FXML
    private Label lbRank;

    @FXML
    private Label lbKilled;

    @FXML
    private Label lbLongTime;
    
    @FXML
    private Label lbexperience;

    
    int index = 0;
    int maxIndex;
    
    List<Integer> numKilled;
    List<Integer>  numBullet;
	List<Integer>longTime;
    
    
    
    

    @FXML
    void processNext(ActionEvent event) {
    	if(Onoff.bsSound) {
    		chooseClickSound.play();
    	}
    	
    	    	index ++;
    	if(index >= maxIndex) {
    		index=maxIndex;
    	}else {
    		
    	}
    	
    	lbmatch.setText(index + 1 + " ");

    	
    	int kills = numKilled.get(index);
		int numBullets = numBullet.get(index);
		int skill = numBullets/kills;
		if(kills >= 100 && skill < 8) {
	        	lbRank.setText("LEGEND OF SPACE");
	        }else if (kills >=80) {
	        	lbRank.setText("MASTER OF SPACE");
	        }else if (kills >= 40) {
	        	lbRank.setText("SPACE DEFENDER");
	        }else if(kills >=25) {
	        	lbRank.setText("SPACE Ranger");
	        }
	        else {
	        	lbRank.setText("SPACE WARRIOR");
	        }
		lbSkill.setText(skill +" per kill ");
    	lbKilled.setText(String.valueOf(numKilled.get(index)));
		lbBulletNumber.setText(String.valueOf(numBullet.get(index)));
		timeString(longTime.get(index));
    	

    }

    @FXML
    void processPrevious(ActionEvent event) {
    	
    	if(Onoff.bsSound) {
    		chooseClickSound.play();
    	}
    	
    	index --;
    	if(index < 0) {
    		index = 0;
    	}
    	
    	lbmatch.setText(index + 1 + " ");
    	
    	int kills = numKilled.get(index);
		int numBullets = numBullet.get(index);
		int skill = numBullets/kills;
		if(kills >= 100 && skill < 8) {
	        	lbRank.setText("LEGEND OF SPACE");
	        }else if (kills >=80) {
	        	lbRank.setText("MASTER OF SPACE");
	        }else if (kills >= 40) {
	        	lbRank.setText("SPACE DEFENDER");
	        }else if(kills >=25){
	        	lbRank.setText("SPACE Ranger");
	        }else {
	        	
	        	lbRank.setText("SPACE WARRIOR");
	        }
    
		lbSkill.setText(skill +" per kill ");
		
    	lbKilled.setText(String.valueOf(numKilled.get(index)));
		lbBulletNumber.setText(String.valueOf(numBullet.get(index)));
		timeString(longTime.get(index));

    }
    
    
    private void timeString(int t) {
    	
    	int hours = t/ 3600;
	    int minutes =  (t % 3600) / 60;
	    int seconds =  (t% 60);
	    String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	    lbLongTime.setText(timeFormatted);
    }
    
    @FXML
    void processBack(ActionEvent event) throws IOException {
    	
    	HBackgroundMusic.stop();
    	
    	if(Onoff.bsSound) {
    		buttonClickSound.play();
    	}
    	
    	homeLink.processMenu(event);

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		if(Onoff.bgSound) {
			HBackgroundMusic.play();
		}
		
		List<String> recordList = FileDatabase.readRecords();
		System.out.println( "RecordList size"+recordList.size());
		
		index = maxIndex = recordList.size() - 1;

		if (recordList.size() == 0) {
			lbKilled.setText("0");
			lbBulletNumber.setText("0");
			lbLongTime.setText("00:00:00");
		} else {
			
			 numKilled= FileDatabase.getKilledList(recordList);
			 numBullet= FileDatabase.getnumBulletList(recordList);
			 longTime= FileDatabase.getLongTimeList(recordList);
			 
			int kills = numKilled.get(numKilled.size()-1);
			int numBullets = numBullet.get(numBullet.size()-1);
			int skill = numBullets/kills;
			 if(kills >= 100 && skill < 8) {
		        	lbRank.setText("LEGEND OF SPACE");
		        }else if (kills >=80) {
		        	lbRank.setText("MASTER OF SPACE");
		        }else if (kills >= 40) {
		        	lbRank.setText("SPACE DEFENDER");
		        }else if(kills >= 25) {
		        	lbRank.setText("SPACE RANGER");
		        }else 
			  {
		        	lbRank.setText("SPACE WARRIOR");
		        }
			lbmatch.setText(numKilled.size() + "");
			lbexperience.setText(numKilled.size()+ " matchs");
			lbKilled.setText(String.valueOf(numKilled.get(numKilled.size()-1)));
			lbBulletNumber.setText(String.valueOf(numBullet.get(numBullet.size()-1)));
			lbSkill.setText(skill +" per Kill");
			timeString(longTime.get(longTime.size()-1));
		}
	
		
	}

}
