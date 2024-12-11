package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class Home extends Application {
	@Override
	public void start(Stage primaryStage) {
		Font.loadFont(getClass().getResourceAsStream("../fonts/space age.ttf"), 10);
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("homeUI.fxml"));
			Scene scene = new Scene(root);
	//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("NovaStrike");
			primaryStage.setResizable(false);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
