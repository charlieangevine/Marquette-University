package CoreGraphics;

public class CGPoint {

	public static final CGPoint zero = new CGPoint(0, 0);

	public double x, y;

	public CGPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}

	public void add(CGVector vector) {
		add(vector.dx, vector.dy);
	}

	public void sub(double x, double y) {
		this.x -= x;
		this.y -= y;
	}

	public void sub(CGVector vector) {
		sub(vector.dx, vector.dy);
	}

	@Override
	public String toString() {
		return "CGPoint: <" + x + ", " + y + ">";
	}
}
