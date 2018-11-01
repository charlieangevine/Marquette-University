package nodes;

import CoreGraphics.CGPoint;
import javafx.geometry.Orientation;

public class MovingWoodBarrier extends WoodBarrier {

	public MovingWoodBarrier(Orientation orientation) {
		super(orientation);

		switch (orientation) {
			case HORIZONTAL:
				dir = 0;
				break;
			case VERTICAL:
				dir = Math.PI / 2;
				break;
			default:
				break;
		}
	}

	private double dir = 0;
	private double speed = 2.5;

	@Override
	public void update() {
		super.update();

		double frameWidth = this.scene.getWidth() * 0.5;
		double frameHeight = this.scene.getHeight() * 0.5;

		double width = this.size.width * 0.5;
		double height = this.size.height * 0.5;

		double x = this.position.x + (Math.cos(dir) * speed);
		double y = this.position.y + (Math.sin(dir) * speed);

		if (x - width < -frameWidth) {
			speed = Math.abs(speed);
		}
		if (x + width > frameWidth) {
			speed = -Math.abs(speed);
		}

		if (y - height < -frameHeight) {
			speed = Math.abs(speed);
		}
		if (y + height > frameHeight) {
			speed = -Math.abs(speed);
		}

		this.position = new CGPoint(x, y);
	}
}
