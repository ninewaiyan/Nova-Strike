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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	HomeUIController homeLink = new HomeUIController();

	private AudioClip buttonClickSound;
	private AudioClip chooseClickSound;

	Media themeSound = new Media(getClass().getResource("../audio/menuUIsound.mp3").toExternalForm());
	MediaPlayer menuBackgroundMusic = new MediaPlayer(themeSound);

	public MenuController() {
		// TODO Auto-generated constructor stub
		buttonClickSound = new AudioClip(getClass().getResource("../audio/bulletreload.mp3").toExternalForm());
		chooseClickSound = new AudioClip(getClass().getResource("../audio/buttonSound.wav").toExternalForm());

	}

	public static String selectedSkin;// Static variable to hold the selected skin path
	public static String selectedBullet;
	public static String selectMap;
	public static int bS = 15;
	public static int bP = 1;
	public static int bW = 20;
	public static int bH = 40;

	@FXML
	private ImageView skinBox;

	@FXML
	private Label lblTitle;

	@FXML
	private Label skinTitle;

	@FXML
	private Label bulletTitle;

	String choose = "heroSkin";

	int s = 0;
	int b = 0;
	int m = 0;

	
	@FXML
	private Label mapTitle;
	
	private void updateSkinBox(String selectedImg) {
		Image putImage = new Image(getClass().getResourceAsStream(selectedImg));
		skinBox.setImage(putImage);
	}

	String heroSkin1 = "../images/skinImg/spaceShipB.png";
	String heroSkin2 = "../images/skinImg/spaceShipA.png";
	String heroSkin3 = "../images/skinImg/yellowSpaceShip.png";
	String heroSkin4 = "../images/skinImg/blackSpaceShip.png";
	

	String[] heroSkinCollection = { heroSkin1, heroSkin2, heroSkin3, heroSkin4 };

	String lblHeroTitle1 = "space-F17";
	String lblHeroTitle2 = "YelloDark";
	String lblHeroTitle3 = "YelloBoss";
	String lblHeroTitle4 = "DarkMS-17";

	String[] lblHeroTitleArray = { lblHeroTitle1, lblHeroTitle2, lblHeroTitle3, lblHeroTitle4 };

	String imgBullet1 = "../images/bulletImg/rocket1.png";
	String imgBullet2 = "../images/bulletImg/rocket2.png";
	String imgBullet3 = "../images/bulletImg/rocket3.png";
	String imgBullet4 = "../images/bulletImg/rocket4.png";
	String imgBullet5 = "../images/bulletImg/cocacola.png";
	String imgBullet6 = "../images/bulletImg/eneryDrinkA.png";
	String imgBullet7 = "../images/bulletImg/drinkC.png";
	String imgBullet8 = "../images/bulletImg/drinkB.png";

	String[] bulletSkinCollection = { imgBullet1, imgBullet2, imgBullet3, imgBullet4, imgBullet5, imgBullet6,
			imgBullet7, imgBullet8 };

	String bulletTitle1 = "Motor-M1";
	String bulletTitle2 = "Motor-M3";
	String bulletTitle3 = "BlackWhite-345";
	String bulletTitle4 = "WhiteRose-SK78";
	String bulletTitle5 = "Cola Power";
	String bulletTitle6 = "Monster Energy";
	String bulletTitle7 = "Monster Power";
	String bulletTitle8 = "Coca Cola ";
	String[] bulletTitleArray = { bulletTitle1, bulletTitle2, bulletTitle3, bulletTitle4, bulletTitle5, bulletTitle6,
			bulletTitle7, bulletTitle8 };

	String background1 = "../images/background_img/panel_bg3.jpg";
	String background2 = "../images/background_img/panel_bg1.jpg";
	String background3 = "../images/background_img/panel_bg2.jpg";
	String background4 = "../images/background_img/panel_bg4.jpg";
	String background5 = "../images/background_img/panel_bg6.jpg";
	
	 

	String[] backgroundSkinCollection = { background1, background2, background3, background4, background5 };

	String mapTitle1 = "Blue Space";
	String mapTitle2 = "Purple Planet";
	String mapTitle3 = "Dark Red Whole";
	String mapTitle4 = "Glaxy Eye";
	String mapTitle5 = "Hexagon";
	String[] mapTitleArray = { mapTitle1, mapTitle2, mapTitle3, mapTitle4, mapTitle5 };

	@FXML
	void processNext(ActionEvent event) {
		
		if(Onoff.bsSound) {
			chooseClickSound.play();
		}
		
		if (choose.matches("heroSkin")) {

			s++;
			if (s >= heroSkinCollection.length) {
				s = 0;
			}

			updateSkinBox(heroSkinCollection[s]);

			lblTitle.setText(lblHeroTitleArray[s]);

		} else if (choose.matches("bulletSkin")) {
			// skinTitle.setText(skinTitleArray[1]);

			// for bullet skin
			b++;
			if (b >= bulletSkinCollection.length) {
				b = 0;
			}

			updateSkinBox(bulletSkinCollection[b]);
			// Bullet Title
			lblTitle.setText(bulletTitleArray[b]);

		} else if (choose.matches("map")) {
			// skinTitle.setText(skinTitleArray[2]);
			m++;
			if (m >= backgroundSkinCollection.length) {
				m = 0;
			}

			updateSkinBox(backgroundSkinCollection[m]);

			lblTitle.setText(mapTitleArray[m]);

		}

	}

	@FXML
	void processPrevious(ActionEvent event) {
		if(Onoff.bsSound) {
			chooseClickSound.play();
		}
		if (choose.matches("heroSkin")) {
			s--;
			if (s < 0) {
				s = heroSkinCollection.length - 1;

			}
			updateSkinBox(heroSkinCollection[s]);

			// for hero title

			lblTitle.setText(lblHeroTitleArray[s]);

		} else if (choose.matches("bulletSkin")) {
			b--;
			if (b < 0) {
				b = bulletSkinCollection.length - 1;
			}

			updateSkinBox(bulletSkinCollection[b]);
			lblTitle.setText(bulletTitleArray[b]);

		} else if (choose.matches("map")) {
			m--;
			if (m < 0) {
				m = backgroundSkinCollection.length - 1;

			}

			updateSkinBox(backgroundSkinCollection[m]);
			// for map title
			lblTitle.setText(mapTitleArray[m]);

		}
	}

	@FXML
	void processStart(ActionEvent event) {
		
		if(Onoff.bsSound) {
			buttonClickSound.play();
		}
		
		menuBackgroundMusic.stop();
		switch (b) {

		case 0: {

			bS = 15;
			bP = 2;
			bW = 20;
			bH = 50;
			break;
		}

		case 1: {

			bS = 15;
			bP = 2;
			bW = 20;
			bH = 50;
			break;
		}

		case 2: {
			bS = 15;
			bP = 3;
			bW = 20;
			bH = 50;
			break;
		}

		case 3: {
			bS = 15;
			bP = 3;
			bW = 15;
			bH = 70;
			break;
		}

		case 4: {
			bS = 13;
			bP = 2;
			bW = 20;
			bH = 50;
			break;
		}
		case 5: {
			bS = 13;
			bP = 2;
			bW = 20;
			bH = 40;
			break;
		}
		case 6: {
			bS = 13;
			bP = 2;
			bW = 20;
			bH = 40;
			break;
		}
		case 7: {
			bS = 10;
			bP = 1;
			bW = 20;
			bH = 40;
			break;

		}
		case 8: {
			bS = 15;
			bP = 2;
			bW = 20;
			bH = 40;
			break;
		}
		case 9: {
			bS = 15;
			bP = 2;
			bW = 20;
			bH = 40;
			break;
		}

		}
		selectMap = backgroundSkinCollection[m];
		selectedBullet = bulletSkinCollection[b];
		selectedSkin = heroSkinCollection[s]; // Ensure the current skin is selected
		GamePanel.gameRestart();
		homeLink.processGameStart(event);

	}

	void initialize() {
		switch (choose) {
		case "heroSkin": {
			updateSkinBox(heroSkin1);
			lblTitle.setText(lblHeroTitleArray[0]);

		}
			break;
		case "bulletSkin": {
			updateSkinBox(imgBullet1);
			lblTitle.setText(bulletTitleArray[0]);

		}
			break;
		case "map": {

			updateSkinBox(background1);
			lblTitle.setText(mapTitleArray[0]);

		}
			break;
		default:
			System.out.println("bala bala");
		}

	}

	@FXML
	void bulletSkin(ActionEvent event) {
		buttonClickSound.play();

		// skinTitle.setText(skinTitleArray[1]);
		choose = "bulletSkin";
		initialize();

	}

	@FXML
	void heroSkin(ActionEvent event) {
		buttonClickSound.play();
		choose = "heroSkin";
		initialize();

	}

	@FXML
	void mapSkin(ActionEvent event) {
		
		

		buttonClickSound.play();
		choose = "map";
		initialize();

	}

	@FXML
	void processHistory(ActionEvent event) throws IOException {
		
		if(Onoff.bsSound) {
			buttonClickSound.play();
		}
		
		menuBackgroundMusic.stop();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.hide();

		// Create a new stage for the game
		Stage HistoryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("history.fxml"));
		Scene scene = new Scene(root);
		Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
		HistoryStage.getIcons().add(icon);
		HistoryStage.setTitle("NovaStrike");
		HistoryStage.setScene(scene);
		HistoryStage.setResizable(false);
		HistoryStage.show();
	}

	@FXML
	void processBack(ActionEvent event) throws IOException {
		if(Onoff.bsSound) {
			buttonClickSound.play();
		}
		menuBackgroundMusic.stop();

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

	@FXML
	void processSoundSetting(ActionEvent event) throws IOException {
		if(Onoff.bsSound) {
			buttonClickSound.play();
		}
		menuBackgroundMusic.stop();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.hide();

		// Create a new stage for the game
		Stage SoundStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("soundSetting.fxml"));
		Scene scene = new Scene(root);
		Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
		SoundStage.getIcons().add(icon);
		SoundStage.setTitle("NovaStrike");
		SoundStage.setScene(scene);
		SoundStage.setResizable(false);
		SoundStage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if (Onoff.bgSound) {
			menuBackgroundMusic.play();
		}
		
		updateSkinBox(heroSkin1);
		lblTitle.setText(lblHeroTitleArray[0]);
		
		

	}

}
