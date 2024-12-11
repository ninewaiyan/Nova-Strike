package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeUIController implements Initializable {

	static public String initialImage = "../images/skinImg/spaceShipB.png";
	static public String initialBullet = "../images/bulletImg/defaultbullet.png";
	public static Stage gameStage = null;
	public static boolean GameStart = false;
	

	Media themeSound = new Media(getClass().getResource("../audio/homeUIsound.mp3").toExternalForm());
	MediaPlayer homeBackgroundMusic = new MediaPlayer(themeSound);

	private AudioClip buttonClickSound;
	private AudioClip buttonSound;

	public HomeUIController() {
		buttonClickSound = new AudioClip(getClass().getResource("../audio/bulletreload.mp3").toExternalForm());
		buttonSound = new AudioClip(getClass().getResource("../audio/buttonSound.wav").toExternalForm());
	}

	private boolean isEscapePressed = false; // Flag to track if Escape key is pressed
	
	 @FXML
	    void processAbout(MouseEvent event) throws IOException {
		  
		  if(Onoff.bsSound) {
	    		buttonClickSound.play();
	    	}
		  
		  homeBackgroundMusic.stop();
	    		
	    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.hide();

	        // Create a new stage for the game
	        Stage AboutStage = new Stage();
	        Parent root = FXMLLoader.load(getClass().getResource("aboutUI.fxml"));
	        Scene scene = new Scene(root);
	        Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
	        AboutStage.getIcons().add(icon);
	        AboutStage.setTitle("NovaStrike");
	        AboutStage.setScene(scene);
	        AboutStage.setResizable(false);
	        AboutStage.show();

	    }
	@FXML
	void processExit(ActionEvent event) {
		if(Onoff.bsSound) {
			buttonClickSound.play();
		}
		
		System.exit(0);

	}

	@FXML
	void processGameStart(ActionEvent event) {
		
		if(Onoff.bsSound) {
			buttonClickSound.play();
		}
		
		homeBackgroundMusic.stop();

		// Hide the current stage
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.hide();

		GameStart = true;

		if (GameStart) {

			GamePanel.gameRestart();

			// Create a new stage for the game

			// Create game panel
			GamePanel gamePanel = new GamePanel();
			Pane root = new Pane(gamePanel.createContent());
			Scene scene = new Scene(root);
			// Set key event handlers
			scene.setOnKeyPressed(e -> gamePanel.handleKeyPressed(e.getCode()));
			scene.setOnKeyReleased(e -> gamePanel.handleKeyReleased(e.getCode()));

			// Set background image
			Image backgroundImage;
			if(MenuController.selectMap == null) {
				 backgroundImage = new Image(getClass().getResourceAsStream("../images/background_img/panel_bg3.jpg"));
			}else {
				backgroundImage = new Image(getClass().getResourceAsStream(MenuController.selectMap));
			}
			
//			if (backgroundImage != null) {
//				BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, null, null, null, null);
//				
//				Background background = new Background(backgroundImg);
//			
//				root.setBackground(background);
//			}
			
			if (backgroundImage != null) {
			    // Create the BackgroundSize with the desired width and height
			    BackgroundSize backgroundSize = new BackgroundSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, true, true, true, true);
			    
			    // Create the BackgroundImage
			    BackgroundImage backgroundImg = new BackgroundImage(
			        backgroundImage, 
			        BackgroundRepeat.NO_REPEAT, 
			        BackgroundRepeat.NO_REPEAT, 
			        BackgroundPosition.CENTER, 
			        backgroundSize
			    );
			    
			    Background background = new Background(backgroundImg);
			    root.setBackground(background);
			}

			// Set key event handlers
			scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
				if (e.getCode() == KeyCode.ESCAPE && !isEscapePressed) {
					// Handle full screen toggle when ESC is pressed
					toggleFullScreen();
					isEscapePressed = true; // Set flag to indicate Escape key is pressed
					e.consume(); // Prevent further processing of this key event
				} else {
					gamePanel.handleKeyPressed(e.getCode());
				}
			});

			scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
				gamePanel.handleKeyReleased(e.getCode());
				if (e.getCode() == KeyCode.ESCAPE) {
					isEscapePressed = false; // Reset flag when Escape key is released
				}
			});

			// Set scene and show the game stage

			if (gameStage == null) {

				gameStage = new Stage();
				gameStage.initStyle(StageStyle.UNDECORATED);
				Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
				gameStage.getIcons().add(icon);
				gameStage.setTitle("Nova~Strike");
				gameStage.setFullScreen(true);
				gameStage.setFullScreenExitHint("");
				gameStage.setFullScreenExitKeyCombination(null);
			}

			gameStage.setScene(scene);
			gameStage.show();
		}

	}

	void toggleFullScreen() {
		if (gameStage.isFullScreen()) {
			gameStage.setFullScreen(false);
		} else {
			gameStage.setFullScreen(true);
		}

	}

	@FXML
	void processPeaceMode(ActionEvent event) {
		if(Onoff.bsSound) {
			buttonSound.play();
		}
		
		homeBackgroundMusic.stop();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.hide();

		GameStart = true;

		if (GameStart) {

			PeacePanel.gameRestart();

			// Create a new stage for the game

			// Create game panel
			PeacePanel peacePanel = new PeacePanel();
			Pane root = new Pane(peacePanel.createContent());
			Scene scene = new Scene(root);
			// Set key event handlers
			scene.setOnKeyPressed(e -> peacePanel.handleKeyPressed(e.getCode()));
			scene.setOnKeyReleased(e -> peacePanel.handleKeyReleased(e.getCode()));

			// Set background image
			Image backgroundImage = new Image(getClass().getResourceAsStream("../images/mapImg/ground.jpg"));
			if (backgroundImage != null) {
				BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, null, null, null, null);
				Background background = new Background(backgroundImg);
				root.setBackground(background);
			}
			
			if (backgroundImage != null) {
			    // Create the BackgroundSize with the desired width and height
			    BackgroundSize backgroundSize = new BackgroundSize(PeacePanel.SCREEN_WIDTH, PeacePanel.SCREEN_HEIGHT, true, true, true, true);
			    
			    // Create the BackgroundImage
			    BackgroundImage backgroundImg = new BackgroundImage(
			        backgroundImage, 
			        BackgroundRepeat.NO_REPEAT, 
			        BackgroundRepeat.NO_REPEAT, 
			        BackgroundPosition.CENTER, 
			        backgroundSize
			    );
			    
			    Background background = new Background(backgroundImg);
			    root.setBackground(background);
			}

			// Set key event handlers
			scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
				if (e.getCode() == KeyCode.ESCAPE && !isEscapePressed) {
					// Handle full screen toggle when ESC is pressed
					toggleFullScreen();
					isEscapePressed = true; // Set flag to indicate Escape key is pressed
					e.consume(); // Prevent further processing of this key event
				} else {
					peacePanel.handleKeyPressed(e.getCode());
				}
			});

			scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
				peacePanel.handleKeyReleased(e.getCode());
				if (e.getCode() == KeyCode.ESCAPE) {
					isEscapePressed = false; // Reset flag when Escape key is released
				}
			});

			// Set scene and show the game stage

			if (gameStage == null) {

				gameStage = new Stage();
				gameStage.initStyle(StageStyle.UNDECORATED);
				Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
				gameStage.getIcons().add(icon);
				gameStage.setTitle("NovaStrike");
				gameStage.setFullScreen(true);
				gameStage.setFullScreenExitHint("");
				gameStage.setFullScreenExitKeyCombination(null);
			}

			gameStage.setScene(scene);
			gameStage.show();
		}

	}

	private void togglefullScreen() {
		if (gameStage.isFullScreen()) {
			gameStage.setFullScreen(false);
		} else {
			gameStage.setFullScreen(true);
		}

	}

	@FXML
	void processHelp(ActionEvent event) throws IOException {
		if(Onoff.bsSound) {
			buttonClickSound.play();
		}
		homeBackgroundMusic.stop();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	      stage.hide();

	      // Create a new stage for the game
	      Stage HelpStage = new Stage();
	      Parent root = FXMLLoader.load(getClass().getResource("HelpUI.fxml"));
	      Scene scene = new Scene(root);
	      Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
	      HelpStage.getIcons().add(icon);
	      HelpStage.setTitle("NovaStrike");
	      HelpStage.setScene(scene);
	      HelpStage.setResizable(false);
	      HelpStage.show();
		}
	

	@FXML
	void processMenu(ActionEvent event) throws IOException {
		if(Onoff.bsSound) {
			buttonClickSound.play();
		}
		homeBackgroundMusic.stop();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.hide();

		// Create a new stage for the game
		Stage MenuStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("mainMenuUI.fxml"));
		Scene scene = new Scene(root);
		Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
		MenuStage.getIcons().add(icon);
		MenuStage.setTitle("NovaStrike");
		MenuStage.setScene(scene);
		MenuStage.setResizable(false);
		MenuStage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if (Onoff.bgSound) {
			homeBackgroundMusic.play();
		}

	}

}
