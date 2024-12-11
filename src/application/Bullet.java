package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet extends ImageView {
    private double speed;
    private String direction;
    private boolean dead;
    private String owner;
    private int penetrate;

    private static final double SCREEN_WIDTH = GamePanel.SCREEN_WIDTH;
    private static final double SCREEN_HEIGHT = GamePanel.SCREEN_HEIGHT;

    public Bullet(int x, int y, int w, int h, double speed, Image image, String direction, String owner,int penetrate) {
        this.setImage(image);
        this.setFitWidth(w);
        this.setFitHeight(h);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.speed = speed;
        this.direction = direction;
        this.dead = false;
        this.owner = owner;
        this.penetrate = penetrate;
    }

    public void move() {
        switch (direction) {
        
            case "LEFT":
            	setRotate(270);
                setTranslateX(getTranslateX() - speed);
                break;
            case "RIGHT":
            	setRotate(90);
                setTranslateX(getTranslateX() + speed);
                break;
            case "UP":
            	setRotate(0);
                setTranslateY(getTranslateY() - speed);
                break;
            case "DOWN":
            	setRotate(180);
                setTranslateY(getTranslateY() + speed);
                break;
            
            case "UPPERLEFT":
                	 setRotate(315);
                     setTranslateX(getTranslateX() - speed);
                     setTranslateY(getTranslateY() - speed); 
                      
                      break;
                      
            case "UNDERLEFT":
            	setRotate(225);
            	setTranslateX(getTranslateX() - speed);
            	setTranslateY(getTranslateY() + speed);
            	break;
            	
            case "UPPERRIGHT":
            	setRotate(45);
            	setTranslateX(getTranslateX() + speed);
            	setTranslateY(getTranslateY() - speed);
            	break;
            	
            case "UNDERRIGHT":
            	setRotate(135);
            	setTranslateX(getTranslateX() + speed);
            	setTranslateY(getTranslateY() + speed);
            	break;
            	
            	
        }

        // Check if the bullet is out of bounds
        if (getTranslateX() < 0 || getTranslateX() > SCREEN_WIDTH || getTranslateY() < 0 || getTranslateY() > SCREEN_HEIGHT + 100) {
            setDead(true);
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getOwner() {
        return owner;
    }

	public int getPenetrate() {
		return penetrate;
	}

	public void setPenetrate(int penetrate) {
		this.penetrate = penetrate;
	}

	
    
    
}
