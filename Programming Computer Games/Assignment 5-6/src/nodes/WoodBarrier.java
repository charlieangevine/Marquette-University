package nodes;

import SpriteKit.SKSpriteNode;
import javafx.geometry.Orientation;
import javafx.scene.shape.Rectangle;

public class WoodBarrier extends SKSpriteNode {

	public WoodBarrier(Orientation orientation) {
		super("woodBarrier " + orientation.toString().toLowerCase());

		this.name = "Wood Barrier";

		this.xScale = 0.6;
		this.yScale = 0.6;
	}

	@Override
	public void updateBoundingBox() {

		double x = getCanvasPosition().x - (size.width * anchorPoint.x);
		double y = getCanvasPosition().y - (size.height * anchorPoint.y);

		this.boundingBox = new Rectangle(x, y, size.width, size.height);
		this.boundingBox.setRotate(Math.toDegrees(zRotation));

		super.updateBoundingBox();
	}

	@Override
	public void update() {
		zRotation += Math.PI / 180;
		super.update();
	}
}
