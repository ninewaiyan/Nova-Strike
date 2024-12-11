package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HealSupport extends ImageView{
	
	 private boolean dead;
	   
	    public HealSupport(int x, int y, int w, int h,Image image) {
	        this.setImage(image);
	        this.setFitWidth(w);
	        this.setFitHeight(h);
	        this.setTranslateX(x);
	        this.setTranslateY(y);
	        this.dead = false;
	        
	    }

		public boolean isDead() {
			return dead;
		}

		public void setDead(boolean dead) {
			this.dead = dead;
		}

}
