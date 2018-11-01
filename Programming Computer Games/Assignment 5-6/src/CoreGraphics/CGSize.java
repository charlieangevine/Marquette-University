package CoreGraphics;

public class CGSize {

	public double width, height;

	public CGSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public String toString() {
		return "CGSize: <" + width + ", " + height + ">";
	}

	public void mult(double d) {
		this.width *= d;
		this.height *= d;
	}
}
