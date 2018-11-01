import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DustBunny extends GameObj {

	public DustBunny(Vector location) {

		// Create game object with radius 10
		super(location, 10);

		// Give dust bunny a random velocity between <-2, -2> and <2, 2>
		setVelocity(Vector.random(-2, 2));

		setColor(Color.ALICEBLUE);
	}

	@Override
	public void update() {
		super.update();

		Canvas canvas = Assign3.getGameCanvas();

		/*
		 * Loop dust bunny around screen
		 */

		// If dust bunny goes towards the left wall
		if (getLoc().getX() < 0)
			setLoc(new Vector(canvas.getWidth(), getLoc().getY()));

		// If dust bunny goes towards the right wall
		if (getLoc().getX() > canvas.getWidth())
			setLoc(new Vector(0, getLoc().getY()));

		// If dust bunny goes towards top
		if (getLoc().getY() < 0)
			setLoc(new Vector(getLoc().getX(), canvas.getHeight()));

		// If dust bunny goes towards bottom
		if (getLoc().getY() > canvas.getHeight())
			setLoc(new Vector(getLoc().getX(), 0));
	}

	@Override
	public void paint() {
		super.paint();

		Canvas canvas = Assign3.getGameCanvas();
		GraphicsContext gc = canvas.getGraphicsContext2D();

		double w = getWidth(), h = getHeight();
		double topLeftX = getTopLeft().getX(), topRightX = getBottomRight().getX();
		double topLeftY = getTopLeft().getY(), bottomRightY = getBottomRight().getY();

		/*
		 * Drawing the rest of dust bunny if part of it is off screen
		 */

		// If dust bunny is going towards left wall
		if (topLeftX < 0) {
			gc.fillOval(topLeftX + canvas.getWidth(), topLeftY, w, h);
		}

		// If dust bunny is going towards right wall
		if (topRightX > canvas.getWidth()) {
			gc.fillOval(topRightX - getWidth() - canvas.getWidth(), topLeftY, w, h);
		}

		// If dust bunny is going towards top
		if (topLeftY < 0) {
			gc.fillOval(topLeftX, topLeftY + canvas.getHeight(), w, h);
		}

		// If dust bunny is going towards bottom
		if (bottomRightY > canvas.getHeight()) {
			gc.fillOval(topLeftX, bottomRightY - canvas.getWidth(), w, h);
		}

	}
}
