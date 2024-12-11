package application;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class PeacePanel {

	AnimationTimer game_timer;
	AnimationTimer pauseTimer;
	AnimationTimer ChildSpawner;
	AnimationTimer gameLongTime;
	private boolean ispaused;

	static int END_COUNTER = 0;

	boolean complete = false;
	int bs = 6;
	int bp = 2;
	int bw;
	int bh;

	static int NUMBER_OF_BULLET = 0;
	static int NUMBER_OF_HAPPY = 0;
	static int LONG_TIME = 0;

	static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
	static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
	private static final double DELTA_TIME = 0.016;

	private Image playerImage;

	private Image enemyBulletImage;

	private Image happyEffect;
	private Image hungyEffect;

	private Image family;
	private Image childPlay;
	private Image dance;

	
	private Image hitEffect;
	private Image effect1;
	private Image effect2;
	private Image effect3;



	// peaceBullet
	private Image book;
	private Image bread;
	private Image hbgar;
	private Image toy;
	private Image uni;
	private Image water;
	private Image milk;
	private Image bulletImg;

	private Image food1;
	private Image food2;
	private Image food3;
	private Image food4;
	private Image food5;
	private Image food6;
	private Image food7;
	private Image food8;
	private Image food9;
	private Image food10;
	private Image food11;
	private Image food12;

	

	private Image child15;
	
	private Children child;
	private Bullet bullet;

	private Player player;
	public static int PLAYER_HEAL_POINT;

	private Text HappyCountText;

	private Text currentBullet;

	private Text shield;

	ProgressBar shieldProgressBar;

	ProgressNumber healPoint;

	private Pane root = new Pane();
	private Set<KeyCode> currentlyActiveKeys = new HashSet<>();

	// Cooldown and limit for player bullets
	private long lastPlayerShotTime = 0;
	private static final long PLAYER_SHOOT_COOLDOWN = 1500_00000L;
	private static int MAX_PLAYER_BULLETS = 50;

	// Cooldown and limit for enemy bullets
	private long lastEnemyShotTime = 0;
	private static final long ENEMY_SHOOT_COOLDOWN = 300_000000L;

	// Tesing

	boolean gameStart = true;

	void gameisRunning() {
		gameStart = false;
	}

	int bulletSpeed = 40;

	// time

	int gameTime = 0;
	Text timerText;

	private AudioClip shootSound;
	private AudioClip bulletHit;
	private AudioClip explosionSound;
	private AudioClip cannon;

	Media themeSound = new Media(getClass().getResource("../audio/oneday.mp3").toExternalForm());
	MediaPlayer backgroundMusic = new MediaPlayer(themeSound);

//	Media War = new Media(getClass().getResource("../audio/modernwar.mp3").toExternalForm());
//	MediaPlayer war= new MediaPlayer(War);

	public PeacePanel() {

		root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		HappyCountText = new Text("Happy Children : " + NUMBER_OF_HAPPY);
		HappyCountText.setFont(new Font(20));
		HappyCountText.setX(10); // Position X
		HappyCountText.setY(30); // Position Y
		HappyCountText.setFill(Color.WHITE);
		HappyCountText.setStroke(Color.GREEN);
		HappyCountText.setStrokeWidth(1);
		root.getChildren().add(HappyCountText);

		currentBullet = new Text("Bullet :" + MAX_PLAYER_BULLETS);
		currentBullet.setFont(new Font(20));
		currentBullet.setX(10);
		currentBullet.setY(100);
		currentBullet.setFill(Color.WHITE);
		currentBullet.setStroke(Color.GREEN);
		currentBullet.setStrokeWidth(1);
		root.getChildren().add(currentBullet);

		shield = new Text("HP :" + PLAYER_HEAL_POINT);
		shield.setFont(new Font(20));
		shield.setX(SCREEN_WIDTH - 260);
		shield.setY(30);
		shield.setFill(Color.WHITE);
		shield.setStroke(Color.GREEN);
		shield.setStrokeWidth(1);
		root.getChildren().add(shield);

		shieldProgressBar = new ProgressBar();

		shieldProgressBar.setProgress(PLAYER_HEAL_POINT); // Set initial progress
		shieldProgressBar.setPrefWidth(200); // Set width of the progress bar
		shieldProgressBar.setLayoutX(SCREEN_WIDTH - 220); // Set X position
		shieldProgressBar.setLayoutY(20); // Set Y position
		shieldProgressBar.setPrefWidth(210);
		shieldProgressBar.setPrefHeight(15);
		shieldProgressBar.setStyle("-fx-accent: green;");

		// Bind progress bar's progress to playerHealPoint
		healPoint = new ProgressNumber();
		healPoint.setNumber((double) PLAYER_HEAL_POINT / 100);
		shieldProgressBar.progressProperty().bind(healPoint.numberProperty());

		// Add progress bar to the root pane
		root.getChildren().add(shieldProgressBar);

		timerText = new Text();
		timerText.setFont(new Font(20));
		timerText.setX(SCREEN_WIDTH / 2); // Adjust position as needed
		timerText.setY(20); // Adjust position as needed
		timerText.setFill(Color.WHITE);
		timerText.setStroke(Color.GREEN);
		timerText.setStrokeWidth(1);
		root.getChildren().add(timerText); // Assuming 'root' is your game pane

		playerImage = new Image(getClass().getResourceAsStream("../images/skinImg/car.png"));

		// peaceBullet

		book = new Image(getClass().getResourceAsStream("../images/peaceBullet/book.png"));
		bread = new Image(getClass().getResourceAsStream("../images/peaceBullet/bread.png"));
		hbgar = new Image(getClass().getResourceAsStream("../images/peaceBullet/hbgar.png"));
		toy = new Image(getClass().getResourceAsStream("../images/peaceBullet/toy.png"));
		water = new Image(getClass().getResourceAsStream("../images/peaceBullet/water.png"));
		uni = new Image(getClass().getResourceAsStream("../images/peaceBullet/uni.png"));
		milk = new Image(getClass().getResourceAsStream("../images/peaceBullet/milk.png"));

		food1 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food1.png"));
		food2 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food2.png"));
		food3 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food3.png"));
		food4 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food4.png"));
		food5 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food5.png"));
		food6 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food6.png"));
		food7 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food7.png"));
		food8 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food8.png"));
		food9 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food9.png"));
		food10 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food10.png"));
		food11 = new Image(getClass().getResourceAsStream("../images/peaceBullet/food11.png"));

		child15 = new Image(getClass().getResourceAsStream("../images/children/child15.png"));
		

		effect1 = new Image(getClass().getResourceAsStream("../images/gameObject/effect1.png"));
		effect2 = new Image(getClass().getResourceAsStream("../images/gameObject/effect2.png"));
		effect3 = new Image(getClass().getResourceAsStream("../images/gameObject/effect3.png"));

		hitEffect = new Image(getClass().getResourceAsStream("../images/children/happyChild.gif"));


		// Happiness Effect
		childPlay = new Image(getClass().getResourceAsStream("../images/children/playingChild.gif"));
		family = new Image(getClass().getResourceAsStream("../images/children/family-home.gif"));
		dance = new Image(getClass().getResourceAsStream("../images/children/danceChild.gif"));

		player = new Player(500, 400, 100, 200, 8, playerImage);
		root.getChildren().add(player);

		// Audio

		String soundPath = getClass().getResource("../audio/shoot.mp3").toExternalForm();
		shootSound = new AudioClip(soundPath);

		bulletHit = new AudioClip(getClass().getResource("../audio/bulletHitA.mp3").toExternalForm());

//        explosionSound = new AudioClip(getClass().getResource("../audio/explosionA.mp3").toExternalForm());

		// cannon = new
		// AudioClip(getClass().getResource("../audio/cannon.mp3").toExternalForm());

		pauseTimer = new AnimationTimer() {
			private long lastUpdate = 0;

			@Override
			public void handle(long now) {

				if (now - lastUpdate >= DELTA_TIME * 1_000_000_000) {

					pauseClick();

					pause();

					lastUpdate = now;
				}

			}
		};

		pauseTimer.start();

		game_timer = new AnimationTimer() {
			private long lastUpdate = 0;

			@Override
			public void handle(long now) {

				updateTimerText();
				if (now - lastUpdate >= DELTA_TIME * 1_000_000_000) {
					update();
					lastUpdate = now;
				}
			}
		};

		game_timer.start();
		startChildSpawner();
		gamelongTimer();
	}

	public Pane createContent() {
		return root;
	}

	private void pauseClick() {
		if (currentlyActiveKeys.contains(KeyCode.P)) {
			ispaused = !ispaused;

		}
	}

	private void pause() {
		if (ispaused) {
			backgroundMusic.pause();

			game_timer.stop();
			ChildSpawner.stop();
			gameLongTime.stop();

			if (currentlyActiveKeys.contains(KeyCode.Y)) {
				System.exit(0);
			}
		} else {
			if (gameStart) {
				backgroundMusic.play();

			}

			game_timer.start();
			ChildSpawner.start();
			gameLongTime.start();

		}
	}

	private void updateTimerText() {

		LONG_TIME = (int) gameTime;
		int hours = (int) (gameTime / 3600);
		int minutes = (int) ((gameTime % 3600) / 60);
		int seconds = (int) (gameTime % 60);

		String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		timerText.setText("Time: " + timeFormatted);
	}

	private void gamelongTimer() {
		gameLongTime = new AnimationTimer() {
			private long lastSpawnTime = 0;
			private static final long SHIELD_SPAWN_COOLDOWN = 100_000_0000L; // 1s

			@Override
			public void handle(long now) {
				if (now - lastSpawnTime >= SHIELD_SPAWN_COOLDOWN) {

					gameTime++;

					lastSpawnTime = now;
				}
			}
		};
		gameLongTime.start();
	}

	private void startChildSpawner() {
		ChildSpawner = new AnimationTimer() {
			private long lastSpawnTime = 0;
			private static final long CHILD_SPAWN_COOLDOWN = 10000_000_000L; //

			@Override
			public void handle(long now) {
				if (now - lastSpawnTime >= CHILD_SPAWN_COOLDOWN) {

					if (NUMBER_OF_HAPPY <= 100) {

						
						spawnChild(child15,100, 100,5) ;
						spawnChild(child15,100, 100,5) ;
						spawnChild(child15,100, 100,5) ;
						
						spawnChild(child15,100, 100,5) ;
						spawnChild(child15,100, 100,5) ;
						spawnChild(child15,100, 100,5) ;
						
						spawnChild(child15,100, 100,5) ;
						spawnChild(child15,100, 100,5) ;
						spawnChild(child15,100, 100,5) ;
						
						spawnChild(child15,100, 100,5) ;
						spawnChild(child15,100, 100,5) ;
						spawnChild(child15,100, 100,5) ;
						
						spawnChild(child15,100, 100,5) ;
						spawnChild(child15,100, 100,5) ;
						spawnChild(child15,100, 100,5) ;
						

					} else {
						complete = true;
						END_COUNTER++;
						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100,100 );
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(childPlay, 100, 100, 100);
						
						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100,100 );
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						
						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100,100 );
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						
						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100,100 );
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(childPlay, 100, 100, 100);

						spawnChildHappy(family, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);
						spawnChildHappy(dance, 100, 100, 100);

						
					}

					lastSpawnTime = now;
				}
			}
		};
		ChildSpawner.start();
	}

	private void spawnChild(Image childImg, int w, int h, int hy) {

		child = new Children((int) ((int) 200 + (Math.random() * (SCREEN_WIDTH - 60))),
				(int) ((int) 200 + (Math.random() * (SCREEN_HEIGHT - 60))), w, h, hy, childImg);
		root.getChildren().add(child);

		 createPortalEffect(child.getTranslateX(), child.getTranslateY() - 20, 60, 60);
	}

	private void spawnChildHappy(Image childImg, int w, int h, int hy) {

		child = new Children((int) ((int) 200 + (Math.random() * (SCREEN_WIDTH - 60))),
				(int) ((int) 200 + (Math.random() * (SCREEN_HEIGHT - 60))), w, h, hy, childImg);
		root.getChildren().add(child);

	}

	private List<Object> sprites() {
		return root.getChildren().stream()
				.filter(n -> n instanceof Player || n instanceof Children || n instanceof Bullet)
				.collect(Collectors.toList());
	}

	private void update() {

		if (!gameStart) {
			return;
		}

		handleCollisions();
		handlePlayerMovement();
		handleBulletDirection();
		updateGameStatus();
		playMusic();
		gameEnd();
		root.getChildren().removeIf(n -> {
			if (n instanceof Player) {
				return ((Player) n).isDead();
			} else if (n instanceof Children) {
				return ((Children) n).isHappy();
			} else if (n instanceof Bullet) {
				return ((Bullet) n).isDead();
			}
			return false;
		});
	}

	void updateGameStatus() {
		HappyCountText.setText("Happy Children: " + NUMBER_OF_HAPPY);
		shield.setText("HP :");
		double hp = (double) PLAYER_HEAL_POINT / 100;
		if (hp >= 0.8) {
			shieldProgressBar.setStyle("-fx-accent: green;");
		} else if (hp >= 0.6) {
			shieldProgressBar.setStyle("-fx-accent: yellow;");
		} else if (hp >= 0.4) {
			shieldProgressBar.setStyle("-fx-accent: orange;");
		} else {
			shieldProgressBar.setStyle("-fx-accent: red;");
		}

		healPoint.setNumber(hp);

	}

	private void handlePlayerMovement() {

		if (currentlyActiveKeys.contains(KeyCode.A) && currentlyActiveKeys.contains(KeyCode.W)) {

			player.moveUpperLeft();

		} else if (currentlyActiveKeys.contains(KeyCode.A) && currentlyActiveKeys.contains(KeyCode.S)) {
			player.moveUnderLeft();
		} else if (currentlyActiveKeys.contains(KeyCode.D) && currentlyActiveKeys.contains(KeyCode.S)) {
			player.moveUnderRight();
		} else if (currentlyActiveKeys.contains(KeyCode.D) && currentlyActiveKeys.contains(KeyCode.W)) {
			player.moveUpperRight();
		} else if (currentlyActiveKeys.contains(KeyCode.D)) {
			player.moveRight();
		} else if (currentlyActiveKeys.contains(KeyCode.W)) {
			player.moveUp();
		} else if (currentlyActiveKeys.contains(KeyCode.S)) {
			player.moveDown();
		} else if (currentlyActiveKeys.contains(KeyCode.A)) {
			player.moveLeft();
		}

	}

	private void handleBulletDirection() {

		if (currentlyActiveKeys.contains(KeyCode.J) && currentlyActiveKeys.contains(KeyCode.I)) {

			shoot(player, "UPPERLEFT");

		} else if (currentlyActiveKeys.contains(KeyCode.J) && currentlyActiveKeys.contains(KeyCode.K)) {
			shoot(player, "UNDERLEFT");
		} else if (currentlyActiveKeys.contains(KeyCode.L) && currentlyActiveKeys.contains(KeyCode.K)) {
			shoot(player, "UNDERRIGHT");
		} else if (currentlyActiveKeys.contains(KeyCode.L) && currentlyActiveKeys.contains(KeyCode.I)) {
			shoot(player, "UPPERRIGHT");
		} else if (currentlyActiveKeys.contains(KeyCode.L)) {
			shoot(player, "RIGHT");
		} else if (currentlyActiveKeys.contains(KeyCode.I)) {
			shoot(player, "UP");
		} else if (currentlyActiveKeys.contains(KeyCode.K)) {
			shoot(player, "DOWN");
		} else if (currentlyActiveKeys.contains(KeyCode.J)) {
			shoot(player, "LEFT");
		}

	}

	private void shoot(Object who, String direction) {

		long now = System.nanoTime();

		if (who instanceof Player) {

			if (now - lastPlayerShotTime >= PLAYER_SHOOT_COOLDOWN) {

				Random random = new Random();

				int b = random.nextInt(19);

				switch (b) {

				case 1: {

					bulletImg = book;
					bw = 50;
					bh = 50;
					break;

				}

				case 2: {

					bulletImg = bread;
					bw = 50;
					bh = 50;
					break;

				}

				case 3: {
					bulletImg = hbgar;
					bw = 50;
					bh = 50;
					break;
				}

				case 4: {

					bulletImg = milk;
					bw = 50;
					bh = 50;
					break;

				}
				case 5: {
					bulletImg = toy;
					bw = 50;
					bh = 50;
					break;

				}

				case 6: {
					bulletImg = uni;
					bw = 40;
					bh = 40;
					break;

				}
				case 7: {
					bulletImg = water;
					bw = 50;
					bh = 50;
					break;
				}
				case 8: {
					bulletImg = food1;
					bw = 50;
					bh = 50;
					break;
				}
				case 9: {
					bulletImg = food2;
					bw = 50;
					bh = 50;
					break;
				}
				case 10: {
					bulletImg = food3;
					bw = 50;
					bh = 50;
					break;
				}
				case 11: {
					bulletImg = food4;
					bw = 50;
					bh = 50;
					break;
				}
				case 12: {
					bulletImg = food5;
					bw = 50;
					bh = 50;
					break;
				}
				case 13: {
					bulletImg = food6;
					bw = 50;
					bh = 50;
					break;
				}
				case 14: {
					bulletImg = food7;
					bw = 50;
					bh = 50;
					break;
				}
				case 15: {
					bulletImg = food8;
					bw = 50;
					bh = 50;
					break;
				}
				case 16: {
					bulletImg = food9;
					bw = 20;
					bh = 20;
					break;
				}
				case 17: {
					bulletImg = food10;
					bw = 20;
					bh = 20;
					break;
				}
				case 18: {
					bulletImg = food11;
					bw = 20;
					bh = 20;
					break;
				}

				}

				bullet = new Bullet((int) ((Player) who).getTranslateX() + 50,
						(int) ((Player) who).getTranslateY() + 50, bw, bh, bs, // Player bullet speed
						bulletImg, direction, "player", bp);
				root.getChildren().add(bullet);
				lastPlayerShotTime = now;
				shootSound.play();
				NUMBER_OF_BULLET++;
			}
		} else if (who instanceof Enemy) {

			if (now - lastEnemyShotTime >= ENEMY_SHOOT_COOLDOWN) {
				bullet = new Bullet((int) ((Enemy) who).getTranslateX() + 20, (int) ((Enemy) who).getTranslateY(), 10,
						40, 15, // Enemy bullet speed
						enemyBulletImage, direction, "enemy", 1);
				root.getChildren().add(bullet);
				lastEnemyShotTime = now;
				shootSound.play();
			}
		}
	}

	private void handleCollisions() {
		List<Object> objects = sprites();
		List<Object> bulletsToRemove = new ArrayList<>();
		List<Object> childToHappy = new ArrayList<>();

		for (Object s : objects) {
			if (s instanceof Bullet) {
				Bullet bullet = (Bullet) s;
				bullet.move();
				if (bullet.getOwner().equals("player")) {
					for (Object e : objects) {
						if (e instanceof Children) {
							Children child = (Children) e;

							if (bullet.getBoundsInParent().intersects(child.getBoundsInParent())) {
								// enemy.setDead(true);

								childHappy(child);
								bulletHit(bullet);

							}
						}
					}
				}
				bulletsToRemove.add(bullet);

			}
		}

		// Remove bullets and enemies marked for removal
		objects.removeAll(bulletsToRemove);
		objects.removeAll(childToHappy);
	}

	private void childHappy(Children child) {
		createHitEffect(child.getTranslateX(), child.getTranslateY(), 40, 40);
		child.setHyPoint(child.getHyPoint() - 1);
		if (child.getHyPoint() == 0) {
			child.setHappy(true);
			createHappiness(child.getTranslateX(), child.getTranslateY(), 100, 100);
			NUMBER_OF_HAPPY++;

			return;
		}

	}

	private void bulletHit(Bullet bullet) {

		bullet.setPenetrate(bullet.getPenetrate() - 1);
		if (bullet.getPenetrate() == 0) {
			bullet.setDead(true);
		}

	}

	public void handleKeyPressed(KeyCode keyCode) {
		currentlyActiveKeys.add(keyCode);
	}

	public void handleKeyReleased(KeyCode keyCode) {
		currentlyActiveKeys.remove(keyCode);
	}

	private void createHappiness(double x, double y, double width, double height) {
		Random random = new Random();
		int h = random.nextInt(3);
		switch (h) {
		case 1:
			happyEffect = childPlay;
			break;
		case 2:
			happyEffect = dance;
			break;
				}
		ImageView happiness = new ImageView(happyEffect);
		happiness.setFitWidth(width);
		happiness.setFitHeight(height);
		happiness.setX(x - width / 2);
		happiness.setY(y - height / 2);

		root.getChildren().add(happiness);

		// Remove the explosion after a short delay
		AnimationTimer timer = new AnimationTimer() {
			private long startTime = -1;

			@Override
			public void handle(long now) {
				if (startTime == -1) {
					startTime = now;
				}

				if (now - startTime >= 3000_000_000L) { // 3 seconds
					root.getChildren().remove(happiness);
					stop();
				}
			}
		};
		timer.start();
	}

	private void createHitEffect(double x, double y, double width, double height) {

		ImageView hittingEffect = new ImageView(hitEffect);
		hittingEffect.setFitWidth(width);
		hittingEffect.setFitHeight(height);
		hittingEffect.setX(x - width / 2);
		hittingEffect.setY(y - height / 2);

		root.getChildren().add(hittingEffect);

		// Remove the explosion after a short delay
		AnimationTimer timer = new AnimationTimer() {
			private long startTime = -1;

			@Override
			public void handle(long now) {
				if (startTime == -1) {
					startTime = now;
				}

				if (now - startTime >= 300_000_000) { // 0.3 seconds
					root.getChildren().remove(hittingEffect);
					stop();
				}
			}
		};
		timer.start();
	}

	private void createPortalEffect(double x, double y, double width, double height) {

		Random randon = new Random();
		int c = randon.nextInt(3);
		switch (c) {
	
		case 1: {
			hungyEffect = effect2;
			break;
		}
		case 2: {
			hungyEffect = effect3;
			break;
		}
		}

  ImageView Effect = new ImageView(hungyEffect);
			Effect.setFitWidth(width);
			Effect.setFitHeight(height);
			Effect.setX(x - width / 2);
			Effect.setY(y - height / 2);
			root.getChildren().add(Effect);
		
		

		// Remove the explosion after a short delay
		AnimationTimer timer = new AnimationTimer() {
			private long startTime = -1;

			@Override
			public void handle(long now) {
				if (startTime == -1) {
					startTime = now;
				}

				if (now - startTime >= 300_000_0000L) { // seconds
					root.getChildren().remove(Effect);

					stop();
				}
			}
		};
		timer.start();
	}

	private void AllChildrenHappy() {
		root.getChildren().removeIf(node -> node instanceof Children);
	}

	private void removeAllBullets() {
		root.getChildren().removeIf(node -> node instanceof Bullet);
	}

	private void playMusic() {
		if (gameStart) {

			backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
			backgroundMusic.play();

		} else {
			backgroundMusic.stop();

		}

	}

	private void gameEnd() {

		if (NUMBER_OF_HAPPY > 100 && END_COUNTER > 2) {

			gameisRunning();

			HomeUIController.GameStart = false;
			AllChildrenHappy();
			removeAllBullets();
			// Clear the root pane of all nodes (except the player if needed)
			root.getChildren().clear();
			try {
				gameComplete(root);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			NUMBER_OF_HAPPY = 0;
			ispaused = true;
		}

	}

	private void gameComplete(Node node) throws IOException {

		// Hide the current stage
		Stage stage = (Stage) node.getScene().getWindow();
		stage.hide();
		HomeUIController.GameStart = false;
		// Load the GameOverUI.fxm
		FXMLLoader loader = new FXMLLoader(getClass().getResource("gameEnd.fxml"));
		Parent root = loader.load();

		// Show the Game Over stage
		Stage gameCompleteStage = new Stage();
		gameCompleteStage.setScene(new Scene(root));
		gameCompleteStage.setResizable(false);
		Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
		gameCompleteStage.getIcons().add(icon);
		gameCompleteStage.setTitle("NovaStrike");
		gameCompleteStage.show();

	}

	static void gameRestart() {
		HomeUIController.gameStage = null;
		LONG_TIME = 0;
		NUMBER_OF_BULLET = 0;
		NUMBER_OF_HAPPY = 0;
		MAX_PLAYER_BULLETS = 50;
		PLAYER_HEAL_POINT = 100;
		END_COUNTER = 0;

	}

}
