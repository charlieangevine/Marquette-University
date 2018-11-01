import static javafx.animation.Animation.INDEFINITE;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Jack Pfeiffer
 * 
 *         Draws a curve star just by drawing straight lines.
 */

public class Assign1 extends Application {

	private final int COUNT = 100;
	private final int WIDTH = 800;
	private final int HEIGHT = 800;

	private Timeline mainLoop;

	private int i = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		theStage.setTitle("Pfeiffer - Assign1");

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		KeyFrame kf = new KeyFrame(Duration.millis(25), e -> {
			render(gc);
		});
		mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(INDEFINITE);
		mainLoop.play();

		theStage.show();
	}

	void render(GraphicsContext gc) {
		double dx = WIDTH / COUNT;
		double dy = HEIGHT / COUNT;

		double y = (i * dy);

		double constX = (WIDTH / 2);
		double constY = (HEIGHT / 2);

		double rx = i > (COUNT / 2) ? (WIDTH - ((i - (COUNT / 2)) * dx)) : ((WIDTH / 2) + (i * dx));
		double lx = i > (COUNT / 2) ? ((i - (COUNT / 2)) * dx) : ((WIDTH / 2) - (i * dx));

		gc.setStroke(Color.rgb(i * 2, i * 2, i * 2));
		gc.strokeLine(constX, y, lx, constY);
		gc.strokeLine(constX, y, rx, constY);

		i++;
		if (i > COUNT)
			mainLoop.stop();
	}

}
