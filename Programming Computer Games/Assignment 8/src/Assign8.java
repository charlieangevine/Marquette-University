import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;

public class Assign8 extends Application {

	private PerspectiveCamera camera;
	private Group cameraDolly;
	private final double cameraQuantity = 10.0;
	private final double sceneWidth = 600;
	private final double sceneHeight = 600;

	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;

	private List<Transition> transitions = new ArrayList<>();
	private boolean running;

	private void constructWorld(Group root) {

		final PhongMaterial earthMaterial = new PhongMaterial();
		earthMaterial.setDiffuseMap(new Image("http://planetmaker.wthr.us/img/earth_gebco8_texture_8192x4096.jpg"));

		final PhongMaterial sunMaterial = new PhongMaterial();
		sunMaterial.setDiffuseMap(new Image("http://cfile30.uf.tistory.com/image/122D320D4C95EFD4393E34"));

		final PhongMaterial moonMaterial = new PhongMaterial();
		moonMaterial.setDiffuseMap(new Image("http://planetmaker.wthr.us/img/moon_lro_bumpmap_512x256.jpg"));

		// EARTH
		Group g = new Group();

		Sphere earth = new Sphere(50);
		earth.setMaterial(earthMaterial);

		Sphere moon = new Sphere(12);
		moon.setMaterial(moonMaterial);

		g.getTransforms().add(new Translate(180, 0));
		g.setTranslateX(250);
		g.getChildren().addAll(earth, moon);

		// EARTH ROTATION

		RotateTransition rotate1 = new RotateTransition(Duration.seconds(20), g);
		rotate1.setAxis(Rotate.Y_AXIS);
		rotate1.setFromAngle(360);
		rotate1.setToAngle(0);
		rotate1.setInterpolator(Interpolator.LINEAR);
		rotate1.setCycleCount(RotateTransition.INDEFINITE);

		// Moon Rotation

		moon.getTransforms().add(new Translate(0, -75));
		moon.setTranslateY(0);

		RotateTransition rotate2 = new RotateTransition(Duration.seconds(5), moon);
		rotate2.setAxis(Rotate.X_AXIS);
		rotate2.setFromAngle(360);
		rotate2.setToAngle(0);
		rotate2.setInterpolator(Interpolator.LINEAR);
		rotate2.setCycleCount(RotateTransition.INDEFINITE);

		// SUN

		Sphere sun = new Sphere(100);
		sun.setMaterial(sunMaterial);

		sun.setTranslateX(250);

		// SATTELITE

		Group sattelite = new Group();

		PhongMaterial grayMaterial = new PhongMaterial();
		grayMaterial.setDiffuseColor(Color.GRAY);
		grayMaterial.setSpecularColor(Color.GRAY);

		Cylinder cylinder = new Cylinder(5, 45);
		cylinder.setMaterial(grayMaterial);

		Box box1 = new Box(15, 15, 15);
		box1.setMaterial(grayMaterial);

		box1.setTranslateY(28);

		Box box2 = new Box(15, 15, 15);
		box2.setMaterial(grayMaterial);

		box2.setTranslateY(-28);

		sattelite.setTranslateY(-80);
		sattelite.setTranslateX(-100);

		sattelite.getChildren().addAll(cylinder, box1, box2);

		//

		root.getChildren().addAll(sun, g, sattelite);

		transitions.add(rotate1);
		transitions.add(rotate2);

		play();
	}

	private void play() {
		for (Transition t : transitions)
			t.play();
		running = true;
	}

	private void pause() {
		for (Transition t : transitions)
			t.pause();
		running = false;
	}

	public void start(Stage stage) {

		Group root = new Group();
		constructWorld(root);

		Scene scene = new Scene(root, sceneWidth, sceneHeight, true);
		scene.setFill(Color.BLACK);

		camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);

		scene.setCamera(camera);

		cameraDolly = new Group();
		cameraDolly.setTranslateZ(-1500);
		cameraDolly.setTranslateX(200);
		cameraDolly.getChildren().add(camera);
		root.getChildren().add(cameraDolly);

		Rotate r = new Rotate();
		r.setAxis(Rotate.Z_AXIS);
		r.setAngle(-30);
		cameraDolly.getTransforms().add(r);

		Rotate xRotate = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate yRotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		camera.getTransforms().addAll(xRotate, yRotate);

		scene.setOnKeyPressed(event -> {
			double change = cameraQuantity;
			Point3D delta = null;

			switch (event.getCode()) {
				case W:
					delta = new Point3D(0, 0, change);
					break;
				case S:
					delta = new Point3D(0, 0, -change);
					break;
				case A:
					delta = new Point3D(-change, 0, 0);
					break;
				case D:
					delta = new Point3D(change, 0, 0);
					break;
				case UP:
					delta = new Point3D(0, -change, 0);
					break;
				case DOWN:
					delta = new Point3D(0, change, 0);
					break;
				case SPACE:
					if (running)
						pause();
					else
						play();
					break;
				default:
					break;
			}

			if (delta != null) {
				delta = camera.localToParent(delta);
				cameraDolly.setTranslateX(cameraDolly.getTranslateX() + delta.getX());
				cameraDolly.setTranslateY(cameraDolly.getTranslateY() + delta.getY());
				cameraDolly.setTranslateZ(cameraDolly.getTranslateZ() + delta.getZ());
			}
		});

		scene.setOnMousePressed(me -> {
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
		});

		scene.setOnMouseDragged(me -> {
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
			mouseDeltaX = (mousePosX - mouseOldX);
			mouseDeltaY = (mousePosY - mouseOldY);

			yRotate.setAngle(((yRotate.getAngle() - mouseDeltaX * 0.02) % 360 + 540) % 360 - 180);
			xRotate.setAngle(((xRotate.getAngle() + mouseDeltaY * 0.02) % 360 + 540) % 360 - 180);
		});

		stage.setTitle("World");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
