
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import obj.ObjView;

public class Game extends Application {

	/**
	 * Written by Jack Pfeiffer
	 * 
	 * 
	 * Controls
	 * 
	 * A or Left Arrow: Strafe Left
	 * D or Right Arrow: Strafe Right
	 * Space: Shoot
	 * 
	 * 
	 */

	private Group root;
	private Scene scene;

	private PerspectiveCamera camera;
	private Group cameraDolly;

	private Group player;
	private boolean keyDown = false;
	private double rotateZ = 0;

	double playerX = 0;
	double playerXV = 0;
	double playerY = 0;
	double playerZ = 0;
	Rotate player_rz;

	boolean update = true;

	private List<Shot> shots = new ArrayList<>();
	private List<Group> tieFighters = new ArrayList<>();

	private void createObjects() {

		camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);

		scene.setCamera(camera);

		player = load(CustomObject.MILLENNIUM_FALCON);

		player_rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
		player.getTransforms().add(player_rz);

		root.getChildren().add(player);

		cameraDolly = new Group();

		cameraDolly.getChildren().add(camera);
		root.getChildren().add(cameraDolly);

		Rotate r = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		r.setAngle(-10);
		cameraDolly.getTransforms().add(r);
	}

	private double random_between(double min, double max) {
		return (Math.random() * (max - min)) + min;
	}

	private void spawnTieFighter() {

		double x = random_between(playerX - 1000, playerX + 1000);
		double y = 0;// random_between(-500, 500);
		double z = random_between(playerZ + 2500, playerZ + 25000);

		Group tieFighter = load(CustomObject.TIE_FIGHTER);

		tieFighter.setScaleX(500);
		tieFighter.setScaleY(500);
		tieFighter.setScaleZ(500);

		tieFighter.setTranslateX(x);
		tieFighter.setTranslateY(y);
		tieFighter.setTranslateZ(z);

		root.getChildren().add(tieFighter);

		tieFighters.add(tieFighter);
	}

	private Group load(CustomObject obj) {
		ObjView view = new ObjView();
		try {
			view.load(ClassLoader.getSystemResource(obj.filePath).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return view.getRoot();
	}

	public void start(Stage stage) {
		root = new Group();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight(), true);
		scene.setFill(Color.BLACK);

		createObjects();

		stage.setTitle("World");
		stage.setScene(scene);
		stage.show();

		scene.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT)
				keyDown = false;
		});

		scene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case A:
				case LEFT:
					keyDown = true;
					rotateZ = Math.max(rotateZ - 8, -30);
					playerXV = -3;
					break;
				case D:
				case RIGHT:
					keyDown = true;
					rotateZ = Math.min(rotateZ + 8, 30);
					playerXV = 3;
					break;
				case SPACE:
					shoot();
					break;
				default:
					break;
			}
		});

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5), e -> {
			update();
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

	}

	public void shoot() {
		Shot shot = new Shot(playerX, playerY, playerZ);
		root.getChildren().add(shot);
		shots.add(shot);
	}

	private void checkCollisions() {
		for (Group t : tieFighters.toArray(new Group[tieFighters.size()])) {
			if (t.getTranslateZ() < playerZ) {
				root.getChildren().remove(t);
				tieFighters.remove(t);
				continue;
			}
			for (Shot shot : shots.toArray(new Shot[shots.size()])) {
				if (shot.getTranslateZ() > playerZ + 5000) {
					root.getChildren().remove(shot);
					shots.remove(shot);
					continue;
				}
				if (root.getChildren().contains(shot) && root.getChildren().contains(t)) {
					Bounds tBounds = getTieFighterBounds(t).getBoundsInParent();
					if (shot.getBoundsInParent().intersects(tBounds)) {
						root.getChildren().remove(t);
						root.getChildren().remove(shot);
						tieFighters.remove(t);
						shots.remove(shot);
					}
				}
			}
		}

	}

	private Box getTieFighterBounds(Group tieFighter) {
		double scale = 4;
		Box box = new Box(74.135 * scale, 92.083 * scale, 79.873 * scale);
		box.setTranslateX(tieFighter.getTranslateX());
		box.setTranslateY(tieFighter.getTranslateY());
		box.setTranslateZ(tieFighter.getTranslateZ());

		return box;
	}

	public void update() {
		if (!update)
			return;

		for (Shot shot : shots)
			shot.update();
		player.setTranslateX(playerX);
		player.setTranslateY(playerY);
		player.setTranslateZ(playerZ += 5);

		if (tieFighters.size() < 10)
			spawnTieFighter();

		if (!keyDown && rotateZ != 0)
			rotateZ += rotateZ < 0 ? 2 : -2;

		if (keyDown)
			playerX += playerXV;

		player.getTransforms().clear();
		Rotate rotate = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
		rotate.setAngle(rotateZ);
		player.getTransforms().add(rotate);

		/** Camera Work **/
		cameraDolly.setTranslateZ(playerZ - 2500);
		cameraDolly.setTranslateY(playerY - 750);
		cameraDolly.setTranslateX(playerX);

		checkCollisions();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
