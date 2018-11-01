
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TravelingSalesman extends Application {

	final static int width = 100;
	final static int height = 100;

	final static double X_SCALE = 9; // How much the 100x100 Plane should be scaled up by in terms of x-coordinates
	final static double Y_SCALE = 9;// How much the 100x100 Plane should be scaled up by in terms of y-coordinates

	int pos, a, b, improves;

	ArrayList<Point> points;
	Point[] map;

	Timeline pathLoop;

	void start() {
		points = new ArrayList<Point>();

		for (int i = 0; i < 40; i++) { // Generate random points
			double x = Math.random() * width;
			double y = Math.random() * height;

			points.add(new Point((int) x, (int) y));
		}

		points.sort(new Comparator<Point>() { // Sort by x-values

			@Override
			public int compare(Point p1, Point p2) {
				if (p1.x < p2.x)
					return -1;
				else if (p1.x > p2.x)
					return 1;
				return 0;
			}
		});

		map = new Point[points.size()];

		map[a = 0] = points.get(0); // Start a in the front of the tour
		map[b = map.length - 1] = points.get(1); // and b in the back
		pos = 2; // start at the next possible point.
		improves = 0;
	}

	double distance(Point[] map) {
		double d = 0;
		for (int i = 0; i < map.length; i++) {
			Point p = map[i];
			Point next = map[(i + 1) % map.length];
			if (p != null && next != null) // If there exists an edge between points
				d += p.distance(next); // Add the distance of the edge
		}
		return d;
	}

	void improve() {
		improves++;
		int rand = (int) (Math.random() * map.length);

		int start = rand;
		int end = (rand + 5) % (map.length - 1);

		Point[] best = new Point[6];
		double bestDistance = Double.MAX_VALUE;

		for (int p2 = start + 1; p2 < start + 5; p2++)
			for (int p3 = start + 1; p3 < start + 5; p3++)
				for (int p4 = start + 1; p4 < start + 5; p4++)
					for (int p5 = start + 1; p5 < start + 5; p5++)
						if (unique(p2, p3, p4, p5)) { // Make sure all points are accounted for.
							Point[] cur = new Point[] { map[start], map[p2 % (map.length - 1)],
									map[p3 % (map.length - 1)], map[p4 % (map.length - 1)], map[p5 % (map.length - 1)],
									map[end] };
							if (distance(cur) < bestDistance) {
								best = cur;
								bestDistance = distance(cur);
							}
						}
		for (int i = start; i <= end; i++)
			map[i] = best[i - start];
	}

	// Helper method
	boolean unique(int... values) {
		List<Integer> ints = new ArrayList<>();
		for (int i : values)
			if (ints.contains(i))
				return false;
			else
				ints.add(i);
		return true;
	}

	void run() {
		if (pos >= map.length) { // If the tour is complete
			improve(); // Start local improvements
			if (improves >= 500)
				pathLoop.stop();
			pos++;
			return;
		}

		Point p = points.get(pos++);

		if (p.distance(map[a]) < p.distance(map[b]))
			map[++a] = p;
		else
			map[--b] = p;
	}

	void paint(GraphicsContext gc) {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

		gc.setStroke(Color.LIGHTGRAY);
		gc.setFill(Color.BLACK);
		gc.setLineWidth(2);

		gc.beginPath();
		gc.moveTo(X_SCALE * map[0].x, Y_SCALE * map[0].y);

		boolean penDown = true; // Keeping track of whether to draw between points preventing connecting the
								// a-point and b-point heads as they traverse along the plane
		for (int i = 0; i < map.length; i++) {
			if (map[i] != null)
				if (penDown)
					gc.lineTo(X_SCALE * map[i].x, Y_SCALE * map[i].y);
				else {
					gc.moveTo(X_SCALE * map[i].x, Y_SCALE * map[i].y);
					penDown = true;
				}
			else
				penDown = false;
		}

		gc.lineTo(X_SCALE * map[0].x, Y_SCALE * map[0].y);
		gc.stroke();
		gc.closePath();

		for (Point p : points) {
			if (p == map[a])
				gc.setFill(Color.RED);
			else if (p == map[b])
				gc.setFill(Color.BLUE);
			else
				gc.setFill(Color.BLACK);
			gc.fillOval((X_SCALE * p.x) - 3, (Y_SCALE * p.y) - 3, 6, 6);
		}

		gc.setFill(Color.BLACK);
		gc.setTextBaseline(VPos.CENTER);
		gc.setTextAlign(TextAlignment.LEFT);

		gc.fillText("Distance: " + ((int) distance(map)), 8, 24);
		gc.fillText("Improve calls: " + improves, 8, 40);

		gc.setFill(pathLoop.getStatus() == Status.RUNNING ? Color.GREEN : Color.RED);
		gc.fillText(pathLoop.getStatus().name(), 8, 8);
	}

	public void start(Stage stage) {
		stage.setTitle("Traveling Salesman");

		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);

		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.SPACE) {
				pathLoop.play();
				scene.setOnKeyPressed(null);
			}
		});

		Canvas canvas = new Canvas(width * X_SCALE, height * Y_SCALE);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		Timeline paintLoop = new Timeline(new KeyFrame(Duration.millis(5), e -> {
			paint(gc);
		}));
		paintLoop.setCycleCount(Animation.INDEFINITE);

		pathLoop = new Timeline(new KeyFrame(Duration.millis(50), e -> {
			run();
		}));
		pathLoop.setCycleCount(Animation.INDEFINITE);

		stage.show();
		paintLoop.play();
		start();

	}

	public static void main(String[] args) {
		launch(args);
	}
}