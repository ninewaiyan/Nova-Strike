package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ControlController {

	HomeUIController linkHome = new HomeUIController();
	@FXML
	private ImageView bulletImage;
	@FXML
	private ImageView bulletImage1;

	@FXML
	private ImageView airplaneRotate;
	
	

	// back action
	@FXML
	void backAction(ActionEvent event) throws IOException {
		linkHome.processHelp(event);

	}


	@FXML
	void a(ActionEvent event) {
		airplaneRotate.setRotate(270);

	}
	
	@FXML
	void aPlusS(ActionEvent event) {
		airplaneRotate.setRotate(225);

	}
	
	@FXML
	void s(ActionEvent event) {
		airplaneRotate.setRotate(180);

	}


	@FXML
	void aPlusW(ActionEvent event) {
		airplaneRotate.setRotate(315);

	}

	@FXML
	void d(ActionEvent event) {
		airplaneRotate.setRotate(90);

	}

	@FXML
	void dPlusS(ActionEvent event) {
		airplaneRotate.setRotate(135);

	}

	
	@FXML
	void w(ActionEvent event) {
		airplaneRotate.setRotate(0);

	}

	@FXML
	void wPlusD(ActionEvent event) {
		airplaneRotate.setRotate(45);

	}

	
	// for bullet direction
	@FXML
	void j(ActionEvent event) {
		bulletImage.setRotate(270);
		

	}

	@FXML
	void jPlusI(ActionEvent event) {
		bulletImage.setRotate(315);

	}

	@FXML
	void i(ActionEvent event) {
		bulletImage.setRotate(0);

	}

	@FXML
	void iPlusL(ActionEvent event) {
		bulletImage.setRotate(45);

	}

	@FXML
	void jPlusK(ActionEvent event) {
		bulletImage.setRotate(215);

	}

	@FXML
	void k(ActionEvent event) {
		bulletImage.setRotate(180);

	}

	@FXML
	void kPlusL(ActionEvent event) {
		bulletImage.setRotate(125);

	}

	
	

	  @FXML
	    void l(ActionEvent event) {
		  bulletImage.setRotate(90);

	    }
}
