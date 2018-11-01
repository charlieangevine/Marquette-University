import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Roomba extends GameObj {

	public Roomba(Vector location) {
		super(location, 25);

		setColor(Color.BLACK);
	}

	public void update() {
		Canvas canvas = Assign3.getGameCanvas();

		double dx = cos() * 3;
		double dy = sin() * 3;

		if (getBottomRight().getY() + dy > canvas.getHeight() || getTopLeft().getY() + dy < 0)
			dy = 0;
		if (getBottomRight().getX() + dx > canvas.getWidth() || getTopLeft().getX() + dx < 0)
			dx = 0;

		setVelocity(new Vector(dx, dy));

		super.update();

	}

	public void paint() {
		super.paint();
		Canvas canvas = Assign3.getGameCanvas();
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setFill(Color.WHITE);

		double l = getRadius() * 0.75;
		double w = getRadius() / 6;

		gc.setStroke(Color.LIME);
		gc.setLineWidth(3);

		gc.beginPath();
		gc.arc(getLoc().getX() + (cos() * l), getLoc().getY() + (sin() * l), w, w, 0, 360);
		gc.stroke();
		gc.fill();
		gc.closePath();

	}
}
