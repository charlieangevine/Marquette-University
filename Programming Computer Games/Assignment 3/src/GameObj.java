import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameObj {

	private double angle, radius;
	private Vector velocity;
	private Vector location;
	private Color color;

	public GameObj(Vector location, double radius) {
		this.location = location;
		this.radius = radius;
	}

	public Vector getLoc() {
		return this.location;
	}

	public void setLoc(Vector location) {
		this.location = location;
	}

	public Vector getTopLeft() {
		return this.location.add(-this.radius, -this.radius);
	}

	public Vector getBottomRight() {
		return this.location.add(this.radius, this.radius);
	}

	public Vector getVelocity() {
		return this.velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public double getAngle() {
		return this.angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double cos() {
		return Math.cos(this.angle);
	}

	public double sin() {
		return Math.sin(this.angle);
	}

	public double getRadius() {
		return this.radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getWidth() {
		return this.radius * 2;
	}

	public double getHeight() {
		return this.radius * 2;
	}

	public boolean collides(GameObj o) {
		return Vector.distance(this.getLoc(), o.getLoc()) < this.getRadius() + o.getRadius();
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public void update() {
		this.location = this.location.add(velocity);
	}

	public void paint() {
		Canvas canvas = Assign3.getGameCanvas();
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setFill(color);
		gc.fillOval(getTopLeft().getX(), getTopLeft().getY(), getWidth(), getHeight());

	}

}
