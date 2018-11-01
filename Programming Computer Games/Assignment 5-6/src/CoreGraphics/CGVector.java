package CoreGraphics;

public class CGVector {
	public static final CGVector zero = new CGVector(0, 0);

	public double dx, dy;

	public CGVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void applyImpulse(CGVector impulse) {
		this.dx += impulse.dx;
		this.dy += impulse.dy;
	}

	@Override
	public String toString() {
		return "CGVector: <" + dx + ", " + dy + ">";
	}
}
