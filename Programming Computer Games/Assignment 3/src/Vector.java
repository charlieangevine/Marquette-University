public class Vector {

	private double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public Vector add(double x, double y) {
		return Vector.add(this, new Vector(x, y));
	}

	public Vector add(Vector vector) {
		return Vector.add(this, vector);
	}

	public double distance(Vector p) {
		return distance(this, p);
	}

	public static double distance(Vector p1, Vector p2) {
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}

	public static Vector add(Vector v1, Vector v2) {
		return new Vector(v1.getX() + v2.getX(), v1.getY() + v2.getY());
	}

	public static Vector mult(Vector v1, double d) {
		return new Vector(v1.getX() * d, v1.getY() * d);
	}

	public static Vector random(double low, double high) {
		double range = high - low;
		double dx = Math.random() * range - Math.abs(low);
		double dy = Math.random() * range - Math.abs(low);

		return new Vector(dx, dy);
	}
}
