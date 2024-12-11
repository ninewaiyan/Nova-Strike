package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameObject extends ImageView {
	
	   
	   
	    public GameObject(int x, int y, int w, int h,Image image) {
	        this.setImage(image);
	        this.setFitWidth(w);
	        this.setFitHeight(h);
	        this.setTranslateX(x);
	        this.setTranslateY(y);
	        
	    }

		
	    
	    
	    

}
