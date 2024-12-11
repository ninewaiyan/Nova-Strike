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

public class GamePanel implements Initializable {

	AnimationTimer game_timer;
	AnimationTimer enemySpawner;
	AnimationTimer bulletSpawner;
	AnimationTimer gameLongTime;
	AnimationTimer healpointShieldSpwner;

	AnimationTimer pauseTimer;

	boolean ispaused = false;

	int bs = MenuController.bS;
	int bp = MenuController.bP;
	int bw = MenuController.bW;
	int bh = MenuController.bH;

	static int NUMBER_OF_BULLET = 0;
	static int NUMBER_OF_KILLED = 0;
	static int LONG_TIME = 0;

	static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
	static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
	private static final double DELTA_TIME = 0.016;

	private Image playerImage;

	private Image enemyBulletImage;

	private Image explosionImage;
	private Image hitEffect;
	private Image bodyHit;
	private Image portalEffect;
	private Image bulletsupport;
	private Image healpointshield;

	private Image playerBulletImg;

	private Image eImg1;
	private Image eImg2;
	private Image eImg3;
	private Image eImg4;
	private Image eImg5;
	private Image eImg6;
	private Image eImg7;
	private Image eImg8;
	private Image eImg9;
	private Image eImg10;

	private Image boss1;
	private Image boss2;
	private Image boss3;
	private Image boss4;

	private Enemy enemy;
	private Bullet bullet;

	// teleport

	private Image teleport1;
	private Image teleport2;
	private Image teleport3;
	private Image teleport4;

	private BulletSupport bulletSupport;
	private HealSupport healpointShield;

	// teleprot
	private GameObject telePort1;
	private GameObject telePort2;
	private GameObject telePort3;
	private GameObject telePort4;

	private Player player;
	public static int PLAYER_HEAL_POINT;

	private Text killCountText;

	private Text currentBullet;

	private Text shield;

	ProgressBar shieldProgressBar;

	ProgressNumber healPoint;

	private Pane root = new Pane();
	private Set<KeyCode> currentlyActiveKeys = new HashSet<>();

	// Cooldown and limit for player bullets
	private long lastPlayerShotTime = 0;
	private static final long PLAYER_SHOOT_COOLDOWN = 2000_00000L;
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

	long gameTime;
	Text timerText;

	private AudioClip shootSound;
	private AudioClip bulletHit;
	private AudioClip explosionSound;
	private AudioClip bulletFill;
	private AudioClip hpFill;
	private AudioClip lackBullet;

	private int currentbullet;

	Media themeSound = new Media(getClass().getResource("../audio/oneday.mp3").toExternalForm());
	MediaPlayer backgroundMusic = new MediaPlayer(themeSound);

	public GamePanel() {

		root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		killCountText = new Text("Kills: " + NUMBER_OF_KILLED);
		killCountText.setFont(new Font(20));
		killCountText.setX(10); // Position X
		killCountText.setY(30); // Position Y
		killCountText.setFill(Color.WHITE);
		killCountText.setStroke(Color.GREEN);
		killCountText.setStrokeWidth(1);
		root.getChildren().add(killCountText);

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

		// teleprot
		teleport1 = new Image(getClass().getResourceAsStream("../images/background_img/redhole.gif"));
		teleport2 = new Image(getClass().getResourceAsStream("../images/background_img/redhole.gif"));

		telePort1 = new GameObject(100, 100, 150, 150, teleport2);
		root.getChildren().add(telePort1);

		telePort2 = new GameObject((int) (SCREEN_WIDTH - 250), 100, 150, 150, teleport2);
		root.getChildren().add(telePort2);

		telePort3 = new GameObject(100, (int) (SCREEN_HEIGHT - 250), 150, 150, teleport2);
		root.getChildren().add(telePort3);

		telePort4 = new GameObject((int) (SCREEN_WIDTH - 300), (int) (SCREEN_HEIGHT - 150), 150, 150, teleport2);
		root.getChildren().add(telePort4);

		if (MenuController.selectedSkin != null) {
			playerImage = new Image(getClass().getResourceAsStream(MenuController.selectedSkin));
		} else {
			playerImage = new Image(getClass().getResourceAsStream(HomeUIController.initialImage));
		}

		if (MenuController.selectedBullet != null) {
			playerBulletImg = new Image(getClass().getResourceAsStream(MenuController.selectedBullet));
		} else {
			playerBulletImg = new Image(getClass().getResourceAsStream(HomeUIController.initialBullet));
		}

		eImg1 = new Image(getClass().getResourceAsStream("../images/enemyImg/onehp.png"));
		eImg2 = new Image(getClass().getResourceAsStream("../images/enemyImg/twohp.png"));
		eImg3 = new Image(getClass().getResourceAsStream("../images/enemyImg/threehp.png"));
		eImg4 = new Image(getClass().getResourceAsStream("../images/enemyImg/threehp2.png"));
		eImg5 = new Image(getClass().getResourceAsStream("../images/enemyImg/fourhp.png"));

		eImg6 = new Image(getClass().getResourceAsStream("../images/enemyImg/fourhp2.png"));
		eImg7 = new Image(getClass().getResourceAsStream("../images/enemyImg/fivehp.png"));
		eImg8 = new Image(getClass().getResourceAsStream("../images/enemyImg/sevenhp.png"));
		eImg9 = new Image(getClass().getResourceAsStream("../images/enemyImg/eighthp.png"));
		eImg10 = new Image(getClass().getResourceAsStream("../images/enemyImg/boss.png"));

		boss1 = new Image(getClass().getResourceAsStream("../images/enemyImg/bossA.png"));
		boss2 = new Image(getClass().getResourceAsStream("../images/enemyImg/bossB.png"));
		boss3 = new Image(getClass().getResourceAsStream("../images/enemyImg/bossC.png"));

		portalEffect = new Image(getClass().getResourceAsStream("../images/background_Img/portal1.gif"));

		enemyBulletImage = new Image(getClass().getResourceAsStream("../images/bulletImg/redbullet.png"));
		hitEffect = new Image(getClass().getResourceAsStream("explosion.png"));
		explosionImage = new Image(getClass().getResourceAsStream("../images/hitImage/explotion-explode.gif"));
		bodyHit = new Image(getClass().getResourceAsStream("../images/hitImage/hit.png"));
		bulletsupport = new Image(getClass().getResourceAsStream("../images/gameObject/bulletsupport.png"));
		healpointshield = new Image(getClass().getResourceAsStream("../images/gameObject/hpsupport.png"));
		player = new Player(500, 400, 60, 60, 8, playerImage);
		root.getChildren().add(player);

		// Audio

		String soundPath = getClass().getResource("../audio/shoot.mp3").toExternalForm();
		shootSound = new AudioClip(soundPath);

		bulletHit = new AudioClip(getClass().getResource("../audio/bulletHitA.mp3").toExternalForm());

		explosionSound = new AudioClip(getClass().getResource("../audio/explosionA.mp3").toExternalForm());

		bulletFill = new AudioClip(getClass().getResource("../audio/bulletreload.mp3").toExternalForm());

		hpFill = new AudioClip(getClass().getResource("../audio/hpreload.mp3").toExternalForm());

		lackBullet = new AudioClip(getClass().getResource("../audio/lackgun.mp3").toExternalForm());
		if (SoundSettingController.SELECTED_MUSIC == null) {

			themeSound = new Media(getClass().getResource("../music/default.mp3").toExternalForm());

		} else {
			themeSound = new Media(getClass().getResource(SoundSettingController.SELECTED_MUSIC).toExternalForm());
		}

		backgroundMusic = new MediaPlayer(themeSound);

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

				// updateTimerText();
				if (now - lastUpdate >= DELTA_TIME * 1_000_000_000) {
					update();
					lastUpdate = now;
				}
			}
		};

		game_timer.start();
		gamelongTimer();
		startEnemySpawner();
		startBulletSpawner();
		starthealpointShieldSpwner();

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

			game_timer.stop();

			gameLongTime.stop();

			enemySpawner.stop();

			bulletSpawner.stop();

			healpointShieldSpwner.stop();

			backgroundMusic.pause();

			if (currentlyActiveKeys.contains(KeyCode.Y)) {
				System.exit(0);
			}
		} else {
			if (gameStart && Onoff.bgSound) {
				backgroundMusic.play();
			}
			gameLongTime.start();
			game_timer.start();
			enemySpawner.start();
			bulletSpawner.start();
			gameLongTime.start();
			healpointShieldSpwner.start();

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

	private void startEnemySpawner() {
		enemySpawner = new AnimationTimer() {
			private long lastSpawnTime = 0;
			private static final long ENEMY_SPAWN_COOLDOWN = 5000_000_000L; //

			@Override
			public void handle(long now) {
				if (now - lastSpawnTime >= ENEMY_SPAWN_COOLDOWN) {

					if (NUMBER_OF_KILLED <= 5) {
						spawnEnemy(eImg1, 60, 60, 2, 1);
						spawnEnemy(eImg1, 60, 60, 2, 1);

					} else if (5 < NUMBER_OF_KILLED && NUMBER_OF_KILLED <= 10) {
						spawnEnemy(eImg2, 60, 60, 2, 3);
						spawnEnemy(eImg2, 60, 60, 2, 3);

					} else if (10 < NUMBER_OF_KILLED && NUMBER_OF_KILLED <= 15) {

						spawnEnemy(eImg3, 60, 60, 2, 4);
						spawnEnemy(eImg3, 60, 60, 2, 4);

					} else if (16 <= NUMBER_OF_KILLED && NUMBER_OF_KILLED <= 20) {

						spawnEnemy(eImg5, 60, 60, 3, 5);
						spawnEnemy(eImg4, 60, 60, 3, 5);

					} else if (21 <= NUMBER_OF_KILLED && NUMBER_OF_KILLED <= 25) {

						spawnEnemy(eImg6, 60, 60, 3, 6);
						spawnEnemy(eImg7, 60, 60, 3, 6);

					} else if (26 <= NUMBER_OF_KILLED && NUMBER_OF_KILLED <= 30) {

						spawnEnemy(eImg6, 60, 60, 3, 6);
						spawnEnemy(eImg7, 60, 60, 3, 7);
					} else if (31 <= NUMBER_OF_KILLED && NUMBER_OF_KILLED <= 45) {
						spawnEnemy(eImg8, 70, 70, 3, 8);
						spawnEnemy(eImg9, 70, 70, 3, 10);

					} else if (46 <= NUMBER_OF_KILLED && NUMBER_OF_KILLED <= 50) {
						spawnEnemy(eImg9, 70, 70, 3, 10);
						spawnEnemy(eImg10, 80, 80, 3, 15);

					} else {

						Random random = new Random();
						int r = random.nextInt(8);
						switch (r) {
						case 1: {
							spawnEnemy(boss1, 80, 80, 3, 15);
							spawnEnemy(eImg1, 60, 60, 2, 1);
							spawnEnemy(eImg1, 60, 60, 2, 1);
							break;
						}
						case 2: {

							spawnEnemy(boss2, 80, 80, 3, 15);
							spawnEnemy(eImg2, 60, 60, 2, 3);
							spawnEnemy(eImg2, 60, 60, 2, 3);

							break;
						}
						case 3: {
							spawnEnemy(boss3, 80, 80, 3, 15);
							spawnEnemy(eImg2, 60, 60, 2, 3);
							spawnEnemy(eImg1, 60, 60, 2, 1);

							break;
						}
						case 4: {

							spawnEnemy(boss3, 80, 80, 3, 15);
							spawnEnemy(eImg8, 70, 70, 3, 8);
							spawnEnemy(eImg9, 70, 70, 3, 10);

							break;
						}
						case 5: {
							spawnEnemy(boss1, 80, 80, 3, 15);
							spawnEnemy(boss2, 80, 80, 3, 15);
							spawnEnemy(boss3, 80, 80, 3, 15);

							break;
						}

						case 6: {
							spawnEnemy(boss1, 80, 80, 3, 15);
							spawnEnemy(eImg6, 60, 60, 3, 6);
							spawnEnemy(eImg7, 60, 60, 3, 7);

							break;
						}

						case 7: {
							spawnEnemy(boss3, 80, 80, 3, 15);
							spawnEnemy(eImg8, 60, 60, 3, 8);
							spawnEnemy(eImg9, 60, 60, 3, 10);

							break;
						}

						}

					}

					lastSpawnTime = now;
				}
			}
		};
		enemySpawner.start();
	}

	private void startBulletSpawner() {
		bulletSpawner = new AnimationTimer() {
			private long lastSpawnTime = 0;
			private static final long BULLET_SPAWN_COOLDOWN = 500_000_0000L;

			@Override
			public void handle(long now) {
				if (now - lastSpawnTime >= BULLET_SPAWN_COOLDOWN) {
					bulletSupport();

					lastSpawnTime = now;
				}
			}
		};
		bulletSpawner.start();
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

	private void starthealpointShieldSpwner() {
		healpointShieldSpwner = new AnimationTimer() {
			private long lastSpawnTime = 0;
			private static final long SHIELD_SPAWN_COOLDOWN = 1000_000_0000L; // 10s

			@Override
			public void handle(long now) {
				if (now - lastSpawnTime >= SHIELD_SPAWN_COOLDOWN) {
					healpointShield();

					lastSpawnTime = now;
				}
			}
		};
		healpointShieldSpwner.start();
	}

	private void spawnEnemy(Image eImg, int w, int h, int speed, int hp) {

		enemy = new Enemy((int) ((int) 200 + (Math.random() * (SCREEN_WIDTH - 60))),
				(int) ((int) 200 + (Math.random() * (SCREEN_HEIGHT - 60))), w, h, speed, hp, eImg);
		root.getChildren().add(enemy);

		createPortalEffect(enemy.getTranslateX(), enemy.getTranslateY(), 100, 100);
	}

	private void bulletSupport() {
		bulletSupport = new BulletSupport((int) ((int) 200 + (Math.random() * (SCREEN_WIDTH - 60))),
				(int) ((int) 200 + (Math.random() * (SCREEN_HEIGHT - 60))), 50, 50, bulletsupport);
		root.getChildren().add(bulletSupport);
	}

	private void healpointShield() {
		healpointShield = new HealSupport((int) ((int) 200 + (Math.random() * (SCREEN_WIDTH - 60))),
				(int) ((int) 200 + (Math.random() * (SCREEN_HEIGHT - 60))), 50, 50, healpointshield);
		root.getChildren().add(healpointShield);
	}

	private List<Object> sprites() {
		return root.getChildren().stream().filter(n -> n instanceof Player || n instanceof Enemy || n instanceof Bullet
				|| n instanceof BulletSupport || n instanceof HealSupport).collect(Collectors.toList());
	}

	private void update() {

		if (!gameStart) {
			return;
		}

		handleCollisions();
		handlePlayerMovement();
		handleBulletDirection();
		updateGameStatus();
		enemyMovement();
		enemyShooting();
		support();
		teleTransport();
		playerDead();
		playMusic();

		root.getChildren().removeIf(n -> {
			if (n instanceof Player) {
				return ((Player) n).isDead();
			} else if (n instanceof Enemy) {
				return ((Enemy) n).isDead();
			} else if (n instanceof Bullet) {
				return ((Bullet) n).isDead();
			} else if (n instanceof BulletSupport) {
				return ((BulletSupport) n).isDead();
			} else if (n instanceof HealSupport) {
				return ((HealSupport) n).isDead();
			}
			return false;
		});
	}

	void updateGameStatus() {
		currentbullet = MAX_PLAYER_BULLETS - NUMBER_OF_BULLET;
		killCountText.setText("Kills : " + NUMBER_OF_KILLED);
		currentBullet.setText("Bullet Numbrer : " + currentbullet);
		shield.setText("HP :");
		double hp = (double) PLAYER_HEAL_POINT / 100;
		if (hp >= 1) {
			shieldProgressBar.setStyle("-fx-accent:blue;");
		} else if (hp >= 0.8) {
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

			if (currentbullet <= 0) {
				if (Onoff.gsSound) {
					lackBullet.play();
				}
			}

			if (now - lastPlayerShotTime >= PLAYER_SHOOT_COOLDOWN && NUMBER_OF_BULLET < MAX_PLAYER_BULLETS) {
				bullet = new Bullet((int) ((Player) who).getTranslateX() + 20, (int) ((Player) who).getTranslateY(), bw,
						bh, bs, // Player bullet speed
						playerBulletImg, direction, "player", bp);
				root.getChildren().add(bullet);
				lastPlayerShotTime = now;

				if (Onoff.gsSound) {
					shootSound.play();
				}

				NUMBER_OF_BULLET++;
			}
		} else if (who instanceof Enemy) {

			if (now - lastEnemyShotTime >= ENEMY_SHOOT_COOLDOWN) {
				bullet = new Bullet((int) ((Enemy) who).getTranslateX() + 20, (int) ((Enemy) who).getTranslateY(), 10,
						40, 15, // Enemy bullet speed
						enemyBulletImage, direction, "enemy", 1);
				root.getChildren().add(bullet);
				lastEnemyShotTime = now;
				if (Onoff.gsSound) {

					shootSound.play();
				}
			}
		}
	}

	private void handleCollisions() {
		List<Object> objects = sprites();
		List<Object> bulletsToRemove = new ArrayList<>();
		List<Object> enemiesToRemove = new ArrayList<>();

		for (Object s : objects) {
			if (s instanceof Bullet) {
				Bullet bullet = (Bullet) s;
				bullet.move();
				if (bullet.getOwner().equals("enemy")) {
					if (bullet.getBoundsInParent().intersects(player.getBoundsInParent())) {
						createHitEffect(player.getTranslateX(), player.getTranslateY(), 50, 50);
						bulletHit(bullet);
						if (Onoff.gsSound) {
							bulletHit.play();
						}
						createHitEffect(player.getTranslateX(), player.getTranslateY(), 40, 40);
						PLAYER_HEAL_POINT--;
						if (PLAYER_HEAL_POINT < 10) {
							createExplosion(player.getTranslateX(), player.getTranslateY(), 200, 200);
							if (Onoff.gsSound) {
								explosionSound.play();
							}

							player.setDead(true);

						}
					}
				} else if (bullet.getOwner().equals("player")) {
					for (Object e : objects) {
						if (e instanceof Enemy) {
							Enemy enemy = (Enemy) e;

							if (bullet.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
								// enemy.setDead(true);
								enemyDestroy(enemy);
								bulletHit(bullet);
								if (Onoff.gsSound) {
									bulletHit.play();
								}

							}
						}
					}
				}
				bulletsToRemove.add(bullet);

				// Check collision between enemy and player
				if (enemy.getBoundsInParent().intersects(player.getBoundsInParent())) {
					// enemy.setDead(true);
					enemyDestroy(enemy);
					createBodyHitEffect(player.getTranslateX(), player.getTranslateY(), 40, 40);
					PLAYER_HEAL_POINT--;
					if (PLAYER_HEAL_POINT < 10) {
						createExplosion(player.getTranslateX(), player.getTranslateY(), 40, 40);
						if (Onoff.gsSound) {
							explosionSound.play();
						}
						player.setDead(true);

					}

				}
			}
		}

		// Remove bullets and enemies marked for removal
		objects.removeAll(bulletsToRemove);
		objects.removeAll(enemiesToRemove);
	}

	private void enemyShooting() {
		List<Object> objects = sprites();
		for (Object s : objects) {
			if (s instanceof Enemy) {
				Enemy enemy = (Enemy) s;
				double eX = enemy.getTranslateX();
				double pX = player.getTranslateX();

				double eY = enemy.getTranslateY();
				double pY = player.getTranslateY();

				double deltaX = Math.abs(eX - pX);
				double deltaY = Math.abs(eY - pY);
				double deltaXY = Math.abs(deltaX - deltaY);

				if (((0 <= deltaX) && (deltaX <= 50)) && eY > pY) {
					shoot(enemy, "UP");
				} else if (((0 <= deltaX) && (deltaX <= 50)) && eY < pY) {
					shoot(enemy, "DOWN");
				} else if (((0 <= deltaY) && (deltaY <= 50)) && eX < pX) {
					shoot(enemy, "RIGHT");
				} else if (((0 <= deltaY) && (deltaY <= 50)) && eX > pX) {
					shoot(enemy, "LEFT");
				} else if (((0 <= deltaXY) && (deltaXY <= 200)) && eX > pX && eY > pY) {
					shoot(enemy, "UPPERLEFT");
				} else if (((0 <= deltaXY) && (deltaXY <= 200)) && eX > pX && eY < pY) {
					shoot(enemy, "UNDERLEFT");
				} else if (((0 <= deltaXY) && (deltaXY <= 200)) && eX < pX && eY > pY) {
					shoot(enemy, "UPPERRIGHT");
				} else if (((0 <= deltaXY) && (deltaXY <= 200)) && eX < pX && eY < pY) {
					shoot(enemy, "UNDERRIGHT");
				}
			}

		}
	}

	private void enemyMovement() {
		List<Object> objects = sprites();
		for (Object s : objects) {
			if (s instanceof Enemy) {
				Enemy enemy = (Enemy) s;

				double eX = enemy.getTranslateX();
				double pX = player.getTranslateX();

				double eY = enemy.getTranslateY();
				double pY = player.getTranslateY();

				double deltaX = Math.abs(eX - pX);
				double deltaY = Math.abs(eY - pY);
				double deltaXY = Math.abs(deltaX - deltaY);

				if (((0 <= deltaX) && (deltaX <= 100)) && eY > pY && !((0 <= deltaY) && (deltaY <= 100))) {
					enemy.moveUp();
				} else if (((0 <= deltaX) && (deltaX <= 100)) && eY < pY && !((0 <= deltaY) && (deltaY <= 100))) {
					enemy.moveDown();
				} else if (((0 <= deltaY) && (deltaY <= 100)) && eX < pX && !((0 <= deltaX) && (deltaX <= 100))) {
					enemy.moveRight();
				} else if (((0 <= deltaY) && (deltaY <= 100)) && eX > pX && !((0 <= deltaX) && (deltaX <= 100))) {
					enemy.moveLeft();
				} else if (((0 <= deltaXY) && (deltaXY <= 200)) && eX > pX && eY > pY) {
					enemy.moveUpperLeft();
				} else if (((0 <= deltaXY) && (deltaXY <= 200)) && eX > pX && eY < pY) {
					enemy.moveUnderLeft();
				} else if (((0 <= deltaXY) && (deltaXY <= 200)) && eX < pX && eY > pY) {
					enemy.moveUpperRight();
				} else if (((0 <= deltaXY) && (deltaXY <= 200)) && eX < pX && eY < pY) {
					enemy.moveUnderRight();
				} else {

					if (eX > pX) {
						enemy.moveLeft();
					} else {
						enemy.moveRight();
					}

				}

			}

		}

	}

	private void support() {

		List<Object> objects = sprites();
		for (Object s : objects) {
			if (s instanceof BulletSupport) {
				BulletSupport bulletSupport = (BulletSupport) s;
				if (player.getBoundsInParent() != null
						&& bulletSupport.getBoundsInParent().intersects(player.getBoundsInParent())) {

					bulletSupport.setDead(true);
					if (Onoff.gsSound) {
						bulletFill.play();
					}

					MAX_PLAYER_BULLETS += 20;

				}

			}

			if (s instanceof HealSupport) {
				HealSupport healSupport = (HealSupport) s;
				if (player.getBoundsInParent() != null
						&& healSupport.getBoundsInParent().intersects(player.getBoundsInParent())) {

					healSupport.setDead(true);
					if (Onoff.gsSound) {
						hpFill.play();
					}

					PLAYER_HEAL_POINT += 20;

				}

			}
		}

	}

	private void enemyDestroy(Enemy enemy) {
		createHitEffect(enemy.getTranslateX(), enemy.getTranslateY(), 40, 40);
		enemy.setHealPoint(enemy.getHealPoint() - 1);
		if (enemy.getHealPoint() == 0) {
			enemy.setDead(true);
			createExplosion(enemy.getTranslateX(), enemy.getTranslateY(), 100, 100);
			NUMBER_OF_KILLED++;
			if (Onoff.gsSound) {
				explosionSound.play();
			}

			return;
		}

	}

	private void bulletHit(Bullet bullet) {

		bullet.setPenetrate(bullet.getPenetrate() - 1);
		if (bullet.getPenetrate() == 0) {
			bullet.setDead(true);
		}

	}

	private void teleTransport() {
		if (telePort1.getBoundsInParent().intersects(player.getBoundsInParent())) {
			player.setTranslateX(telePort4.getTranslateX() - 100);
			player.setTranslateY(telePort4.getTranslateY());

		}
		if (telePort2.getBoundsInParent().intersects(player.getBoundsInParent())) {
			player.setTranslateX(telePort3.getTranslateX() + 200);
			player.setTranslateY(telePort3.getTranslateY());

		}
		if (telePort3.getBoundsInParent().intersects(player.getBoundsInParent())) {
			player.setTranslateX(telePort2.getTranslateX() - 100);
			player.setTranslateY(telePort2.getTranslateY());

		}
		if (telePort4.getBoundsInParent().intersects(player.getBoundsInParent())) {
			player.setTranslateX(telePort1.getTranslateX() + 150);
			player.setTranslateY(telePort1.getTranslateY());

		}
	}

	public void handleKeyPressed(KeyCode keyCode) {
		currentlyActiveKeys.add(keyCode);
	}

	public void handleKeyReleased(KeyCode keyCode) {
		currentlyActiveKeys.remove(keyCode);
	}

	private void createExplosion(double x, double y, double width, double height) {
		ImageView explosion = new ImageView(explosionImage);
		explosion.setFitWidth(width);
		explosion.setFitHeight(height);
		explosion.setX(x - width / 2);
		explosion.setY(y - height / 2);

		root.getChildren().add(explosion);

		// Remove the explosion after a short delay
		AnimationTimer timer = new AnimationTimer() {
			private long startTime = -1;

			@Override
			public void handle(long now) {
				if (startTime == -1) {
					startTime = now;
				}

				if (now - startTime >= 300_000_000) { // 0.3 seconds
					root.getChildren().remove(explosion);
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

	private void createBodyHitEffect(double x, double y, double width, double height) {
		ImageView hittingEffect = new ImageView(bodyHit);
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
		ImageView Effect = new ImageView(portalEffect);
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

				if (now - startTime >= 100_000_0000L) { // seconds
					root.getChildren().remove(Effect);
					stop();
				}
			}
		};
		timer.start();
	}

	private void removeAllEnemies() {
		root.getChildren().removeIf(node -> node instanceof Enemy);
	}

	private void removeAllBullets() {
		root.getChildren().removeIf(node -> node instanceof Bullet);
	}

	private void playerDead() {

		if (player.isDead()) {

			gameisRunning();
			HomeUIController.GameStart = false;
			removeAllEnemies();
			removeAllBullets();
			// Clear the root pane of all nodes (except the player if needed)
			root.getChildren().clear();

			try {

				gameOver(root);

				// Restart the game by initializing a new instance of GamePanel

				player.setDead(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void playMusic() {
		if (gameStart && Onoff.bgSound) {
			backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
			backgroundMusic.play();
		} else {
			backgroundMusic.stop();

		}

	}

	private void gameOver(Node node) throws IOException {

		// Hide the current stage
		Stage stage = (Stage) node.getScene().getWindow();
		stage.hide();

		// Load the GameOverUI.fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverUI.fxml"));
		Parent root = loader.load();

		// Show the Game Over stage
		Stage gameOverStage = new Stage();
		gameOverStage.setScene(new Scene(root));
		gameOverStage.setResizable(false);
		Image icon = new Image(getClass().getResourceAsStream("../images/logo/ship.png"));
		gameOverStage.getIcons().add(icon);
		gameOverStage.setTitle("NovaStrike");
		gameOverStage.show();

	}

	static void gameRestart() {
		HomeUIController.gameStage = null;
		LONG_TIME = 0;
		NUMBER_OF_BULLET = 0;
		NUMBER_OF_KILLED = 0;
		MAX_PLAYER_BULLETS = 50;
		PLAYER_HEAL_POINT = 100;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
