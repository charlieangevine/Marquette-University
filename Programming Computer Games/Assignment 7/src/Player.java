import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Player {
	String my_id;
	int x, y;
	int dir;
	int dynamite = 2; // Start with two sticks of dynamite because you can pick
						// up more
	int score = 0;
	Color color;

	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	Player(int x, int y, int d) {
		this.x = x;
		this.y = y;
		this.dir = d;
		this.color = randomColor();
	}

	private Color randomColor() {
		double r = (double) ((int) (Math.random() * 255)) / 255.0;
		double g = (double) ((int) (Math.random() * 255)) / 255.0;
		double b = (double) ((int) (Math.random() * 255)) / 255.0;
		return new Color(r, g, b, 1);
	}

	public void turnLeft() {
		dir--;
		if (dir < UP)
			dir = LEFT;
	}

	public void turnRight() {
		dir++;
		if (dir > LEFT)
			dir = UP;
	}

	public int dx() {
		switch (dir) {
			case LEFT:
				return x - 1;
			case RIGHT:
				return x + 1;
			default:
				return x;
		}
	}

	public int dy() {
		switch (dir) {
			case UP:
				return y - 1;
			case DOWN:
				return y + 1;
			default:
				return y;
		}
	}

	public void render(GraphicsContext gc, int x, int y) {

		gc.setFill(color);
		gc.fillOval(x, y, Grab.CELLSIZE - 2, Grab.CELLSIZE - 2);
		gc.setFill(Color.BLACK);

		switch (dir) {
			case UP:
				gc.fillOval(x + Grab.CELLSIZE / 4, y, Grab.CELLSIZE / 2, Grab.CELLSIZE / 2);
				break;
			case RIGHT:
				gc.fillOval(x + Grab.CELLSIZE / 2, y + Grab.CELLSIZE / 4, Grab.CELLSIZE / 2, Grab.CELLSIZE / 2);
				break;
			case DOWN:
				gc.fillOval(x + Grab.CELLSIZE / 4, y + Grab.CELLSIZE / 2, Grab.CELLSIZE / 2, Grab.CELLSIZE / 2);

				break;
			case LEFT:
				gc.fillOval(x, y + Grab.CELLSIZE / 4, Grab.CELLSIZE / 2, Grab.CELLSIZE / 2);
		}
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font(18));
		gc.fillText("" + score, x + (x - dx()) * 15 + Grab.CELLSIZE / 2, y + (y - dy()) * 15 + Grab.CELLSIZE / 2);
	}
}