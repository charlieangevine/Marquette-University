import static javafx.animation.Animation.INDEFINITE;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Assign3 extends Application {

	public Roomba roomba;
	public List<GameObj> gameObjects = new ArrayList<>();

	private static Canvas gameCanvas;
	private static Canvas statCanvas;

	private Timeline mainLoop;
	private boolean started = false;
	private static long startTime;

	private String curTime = "";
	private double timeY = 12.5;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		VBox frame = new VBox();

		statCanvas = new Canvas(screenSize.getWidth(), 60);
		gameCanvas = new Canvas(screenSize.getWidth(), screenSize.getHeight() - 110);

		frame.getChildren().add(statCanvas);
		frame.getChildren().add(gameCanvas);

		root.getChildren().add(frame);

		KeyFrame kf = new KeyFrame(Duration.millis(30), e -> {
			update();
			checkCollisions();
			render();
		});

		mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(INDEFINITE);

		theStage.show();

		theScene.setOnKeyPressed(e -> {
			keyPressed(e);
		});

		for (int i = 0; i < 12; i++)
			gameObjects.add(new DustBunny(Vector.random(0, gameCanvas.getWidth())));
		gameObjects.add(roomba = new Roomba(new Vector(gameCanvas.getWidth() / 2, gameCanvas.getHeight() / 2)));

		render();

		getGameContext().setFont(new Font(60));
		getGameContext().setTextAlign(TextAlignment.CENTER);
		getGameContext().setTextBaseline(VPos.CENTER);
		getGameContext().setFill(Color.LIME);

		getGameContext().fillText("Press Any Button", gameCanvas.getWidth() / 2, (gameCanvas.getHeight() / 2) - 50);
		getGameContext().fillText("To Start", gameCanvas.getWidth() / 2, (gameCanvas.getHeight() / 2) + 50);
	}

	public void win() {
		KeyFrame kf = new KeyFrame(Duration.millis(30), e -> {
			if (timeY < 450)
				timeY++;
			render();

			GraphicsContext gc = getGameContext();
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFill(Color.RED);

			gc.setFont(new Font((timeY - 90)));
			gc.fillText("YOU WIN!", (gameCanvas.getWidth() / 2), (timeY * 0.7) - 100);

			gc.setFont(new Font(36 + (timeY - 40)));
			gc.fillText(curTime, (gameCanvas.getWidth() / 2), (timeY * 1.6) - 50);
		});

		Timeline winLoop = new Timeline(kf);
		winLoop.setCycleCount(INDEFINITE);

		winLoop.play();

	}

	public void checkCollisions() {
		ArrayList<GameObj> loopList = new ArrayList<>(gameObjects);
		loopList.stream().forEach(o -> {
			if (o != roomba && o.collides(roomba))
				gameObjects.remove(o);
		});

	}

	private void keyPressed(KeyEvent e) {
		if (!started) {
			mainLoop.play();
			startTime = System.currentTimeMillis();
			started = true;
		}

		double da = 10 * (Math.PI / 180);
		switch (e.getCode()) {
			case LEFT:
				roomba.setAngle(roomba.getAngle() - da);
				break;
			case RIGHT:
				roomba.setAngle(roomba.getAngle() + da);
			default:
		}
	}

	private void update() {
		if (gameObjects.size() == 1) {
			mainLoop.pause();
			win();
		}

		curTime = (int) ((System.currentTimeMillis() - startTime) / 10.0) / 100.0 + "s";

		gameObjects.stream().forEach(o -> {
			o.update();
		});
	}

	private void render() {
		GraphicsContext gc = getGameContext();
		GraphicsContext sc = getStatContext();

		gc.setFill(Color.DARKSLATEBLUE);
		sc.setFill(Color.WHITE);

		gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
		sc.fillRect(0, 0, statCanvas.getWidth(), statCanvas.getHeight());

		sc.setFill(Color.BLACK);
		gc.setFill(Color.BLACK);
		sc.fillRect(0, statCanvas.getHeight() - 5, statCanvas.getWidth(), statCanvas.getHeight());

		sc.setFont(new Font(36));
		sc.setTextAlign(TextAlignment.LEFT);
		sc.setTextBaseline(VPos.CENTER);

		sc.fillText(curTime, (statCanvas.getWidth() / 2) - 100, timeY * 2);
		sc.fillText("Remaining: ", statCanvas.getWidth() - 250, 25);

		sc.setFill(Color.RED);
		sc.fillText("" + (gameObjects.size() - 1), statCanvas.getWidth() - 50, 25);

		gameObjects.stream().forEach(o -> {
			o.paint();
		});
	}

	public static Canvas getGameCanvas() {
		return gameCanvas;
	}

	public static Canvas getStatCanvas() {
		return statCanvas;
	}

	public static GraphicsContext getGameContext() {
		return gameCanvas.getGraphicsContext2D();
	}

	public static GraphicsContext getStatContext() {
		return statCanvas.getGraphicsContext2D();
	}
}
