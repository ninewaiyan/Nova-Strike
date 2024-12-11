package application;

	import javafx.scene.image.Image;
	import javafx.scene.image.ImageView;

	public class Children extends ImageView {
	    private boolean happy;
	    private  int hy ;

	    public Children(int x, int y, int w, int h,int hy, Image image) {
	        this.setImage(image);
	        this.setFitWidth(w);
	        this.setFitHeight(h);
	        this.setTranslateX(x);
	        this.setTranslateY(y);
	        this.happy= false;
	        this.hy= hy;
	        
	    }
	    
	  
	    public boolean isHappy() {
	        return happy;
	    }

	    public void setHappy(boolean happy) {
	        this.happy = happy;
	    }


		public int getHyPoint() {
			return hy;
		}


		public void setHyPoint(int hy) {
			this.hy = hy;
		}
	    
	}

