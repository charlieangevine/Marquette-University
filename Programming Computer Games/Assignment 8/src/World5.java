
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 *
 * @author mike slattery
 */
public class World5 extends Application {

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

	private void constructWorld(Group root) {
		// AmbientLight light = new AmbientLight();
		AmbientLight light = new AmbientLight(Color.rgb(160, 160, 160));

		PointLight pl = new PointLight();
		pl.setTranslateX(100);
		pl.setTranslateY(-100);
		pl.setTranslateZ(-100);
		root.getChildren().add(pl);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.FORESTGREEN);
		greenMaterial.setSpecularColor(Color.LIMEGREEN);
		Box xAxis = new Box(500, 10, 10);
		xAxis.setMaterial(greenMaterial);
		Box yAxis = new Box(10, 200, 10);
		yAxis.setMaterial(greenMaterial);
		Box zAxis = new Box(10, 10, 200);
		zAxis.setMaterial(greenMaterial);

		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.RED);
		redMaterial.setSpecularColor(Color.TOMATO);
		final Sphere sphere = new Sphere(30);
		sphere.setMaterial(redMaterial);

		sphere.setTranslateX(150);

		final PhongMaterial yellowMaterial = new PhongMaterial();
		yellowMaterial.setDiffuseColor(Color.rgb(200, 200, 0));
		// yellowMaterial.setDiffuseColor(Color.YELLOW);
		// yellowMaterial.setSpecularColor(Color.WHITE);
		final Sphere sphere2 = new Sphere(30);
		sphere2.setMaterial(yellowMaterial);
		// sphere2.setDrawMode(DrawMode.LINE);

		sphere2.setTranslateX(110);

		// Example from JavaFX for Dummies
		TriangleMesh pyramidMesh = new TriangleMesh();
		// define (a trivial) texture map
		pyramidMesh.getTexCoords().addAll(
				0.5f, 0,
				0, 0.5f,
				1, 0.5f,
				0, 1,
				1, 1);
		// define vertices
		float h = 100; // Height
		float s = 200; // Base hypotenuse
		pyramidMesh.getPoints().addAll(
				0, 0, 0, // Point 0 - Top
				0, h, -s / 2, // Point 1 - Front
				-s / 2, h, 0, // Point 2 - Left
				s / 2, h, 0, // Point 3 - Right
				0, h, s / 2 // Point 4 - Back
		);
		// define faces
		pyramidMesh.getFaces().addAll(
				0, 0, 2, 1, 1, 2, // Front left face
				0, 0, 1, 1, 3, 1, // Front right face
				0, 0, 3, 1, 4, 2, // Back right face
				0, 0, 4, 1, 2, 2, // Back left face
				4, 1, 1, 4, 2, 2, // Bottom left face
				4, 1, 3, 3, 1, 4 // Bottom right face
		);
		pyramidMesh.getFaceSmoothingGroups().addAll(
				1, 2, 3, 4, 5, 5);
		MeshView pyramid = new MeshView(pyramidMesh);
		// pyramid.setDrawMode(DrawMode.LINE);
		final PhongMaterial pyrMaterial = new PhongMaterial();
		pyrMaterial.setDiffuseMap(new Image("pyr_tex.png"));
		pyrMaterial.setSpecularColor(Color.WHITE);
		pyramid.setMaterial(pyrMaterial);
		pyramid.setTranslateX(-50);
		pyramid.setTranslateY(-200);
		pyramid.setTranslateZ(0);
		root.getChildren().add(pyramid);

		// // Dump out all characteristics of the TriangleMesh
		// // for debugging purposes
		// TriangleMesh tm = pyramidMesh;
		// System.out.println("Faces: "+tm.getFaceElementSize());
		// System.out.println(tm.getFaces() );
		// System.out.println(tm.getFaceSmoothingGroups());
		// System.out.println("Normals: "+tm.getNormalElementSize());
		// System.out.println(tm.getNormals());
		// System.out.println("Points: "+tm.getPointElementSize());
		// System.out.println(tm.getPoints());
		// System.out.println("TexCoords: "+tm.getTexCoordElementSize());
		// System.out.println(tm.getTexCoords());

		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.BLUE);
		blueMaterial.setSpecularColor(Color.WHITE);
		Box box = new Box(40, 60, 80);
		box.setMaterial(blueMaterial);
		// box.setDrawMode(DrawMode.LINE);

		box.setTranslateX(-30);
		box.setTranslateY(-20);
		box.setTranslateZ(-20);

		root.getChildren().addAll(xAxis, yAxis, zAxis);

		root.getChildren().addAll(sphere2, sphere, box, light);

	}

	@Override
	public void start(Stage primaryStage) {

		// Build your Scene and Camera
		Group sceneRoot = new Group();
		constructWorld(sceneRoot);

		Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight, true);
		scene.setFill(Color.VIOLET);
		camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		scene.setCamera(camera);
		// translations through dolly
		cameraDolly = new Group();
		cameraDolly.setTranslateZ(-1000);
		cameraDolly.setTranslateX(200);
		cameraDolly.getChildren().add(camera);
		sceneRoot.getChildren().add(cameraDolly);
		// rotation transforms
		Rotate xRotate = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate yRotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		camera.getTransforms().addAll(xRotate, yRotate);

		// Use keyboard to control camera position
		scene.setOnKeyPressed(event -> {
			double change = cameraQuantity;
			// What key did the user press?
			KeyCode keycode = event.getCode();

			Point3D delta = null;
			if (keycode == KeyCode.COMMA) {
				delta = new Point3D(0, 0, change);
			}
			if (keycode == KeyCode.PERIOD) {
				delta = new Point3D(0, 0, -change);
			}
			if (keycode == KeyCode.A) {
				delta = new Point3D(-change, 0, 0);
			}
			if (keycode == KeyCode.D) {
				delta = new Point3D(change, 0, 0);
			}
			if (keycode == KeyCode.W) {
				delta = new Point3D(0, -change, 0);
			}
			if (keycode == KeyCode.S) {
				delta = new Point3D(0, change, 0);
			}
			if (delta != null) {
				Point3D delta2 = camera.localToParent(delta);
				cameraDolly.setTranslateX(cameraDolly.getTranslateX() + delta2.getX());
				cameraDolly.setTranslateY(cameraDolly.getTranslateY() + delta2.getY());
				cameraDolly.setTranslateZ(cameraDolly.getTranslateZ() + delta2.getZ());

			}
		});

		// Use mouse to control camera rotation
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

			yRotate.setAngle(((yRotate.getAngle() - mouseDeltaX * 0.2) % 360 + 540) % 360 - 180); // +
			xRotate.setAngle(((xRotate.getAngle() + mouseDeltaY * 0.2) % 360 + 540) % 360 - 180); // -
		});

		primaryStage.setTitle("World5");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}