package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy extends ImageView {
    private double speed;
    private boolean dead;
    private  int hp ;

    public Enemy(int x, int y, int w, int h, double speed,int hp, Image image) {
        this.setImage(image);
        this.setFitWidth(w);
        this.setFitHeight(h);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.speed = speed;
        this.dead = false;
        this.hp = hp;
        
    }
    
    public void stop() {
    	
    }
    public void moveUpperLeft() {
    	setRotate(315);
    	if (getTranslateX() > 0) {
            setTranslateX(getTranslateX() - speed);
            
        }
    	
    	 if (getTranslateY() > 0) {
             setTranslateY(getTranslateY() - speed);
         }
    	
    
    }
    
    public void moveUnderLeft() {
    	setRotate(225);
    	if (getTranslateX() > 0) {
            setTranslateX(getTranslateX() - speed/2);
            
        }
    	if (getTranslateY() + getFitHeight() < GamePanel.SCREEN_HEIGHT + 60) {
            setTranslateY(getTranslateY() + speed/2);
        }
    	
    	
    }
    
    public void moveUpperRight() {
    	setRotate(45);
    	 if (getTranslateX() + getFitWidth() < GamePanel.SCREEN_WIDTH) {
             setTranslateX(getTranslateX() + speed/2);
         }
    	 
    	 if (getTranslateY() > 0) {
             setTranslateY(getTranslateY() - speed/2);
         }
    	
    }
    
    public void moveUnderRight() {
    	setRotate(135);
    	
    	if (getTranslateX() + getFitWidth() < GamePanel.SCREEN_WIDTH) {
            setTranslateX(getTranslateX() + speed/2);
        }
    	if (getTranslateY() + getFitHeight() < GamePanel.SCREEN_HEIGHT + 60) {
            setTranslateY(getTranslateY() + speed/2);
        }
    	
    }
    
   

    public void moveLeft() {
    	setRotate(270); // Set rotation to face left
    
    	if (getTranslateX() > 0) {
            setTranslateX(getTranslateX() - speed);
            
        }
    	
    	     
    }

    public void moveRight() {
       
        setRotate(90); // Set rotation to face right
        if (getTranslateX() + getFitWidth() < GamePanel.SCREEN_WIDTH) {
            setTranslateX(getTranslateX() + speed);
        }
    }

    public void moveUp() {
      
        setRotate(0); // Set rotation to face up
        if (getTranslateY() > 0) {
            setTranslateY(getTranslateY() - speed);
        }
    }

    public void moveDown() {
       
        setRotate(180); // Set rotation to face down
        if (getTranslateY() + getFitHeight() < GamePanel.SCREEN_HEIGHT + 60) {
            setTranslateY(getTranslateY() + speed);
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public int getHealPoint() {
		return hp;
	}


	public void setHealPoint(int healPoint) {
		this.hp = healPoint;
	}
    
}
