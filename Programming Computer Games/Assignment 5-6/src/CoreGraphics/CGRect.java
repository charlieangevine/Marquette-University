package CoreGraphics;

public class CGRect {

	public CGPoint position;
	public CGSize size;

	public CGRect(CGPoint cgPoint, CGSize cgSize) {
		this.position = cgPoint;
		this.size = cgSize;
	}

	public CGRect(double x, double y, double width, double height) {
		this(new CGPoint(x, y), new CGSize(width, height));
	}

	public boolean contains(CGPoint point) {
		return point.x > position.x && point.y > position.y && point.x < position.x + size.width
				&& point.y < position.y + size.height;
	}

	public double minX() {
		return position.x - (size.width * 0.5);
	}

	public double minY() {
		return position.y - (size.height * 0.5);
	}

	public double width() {
		return size.width;
	}

	public double height() {
		return size.height;
	}

	@Override
	public String toString() {
		return "CGRect: <" + position.toString() + ", " + size.toString() + ">";
	}
}
