package nodes;

import SpriteKit.SKSpriteNode;
import javafx.geometry.Orientation;
import javafx.scene.shape.Rectangle;

public class GlassBarrier extends SKSpriteNode {

	public GlassBarrier(Orientation orientation) {
		super("glassBarrier " + orientation.toString().toLowerCase());

		this.name = "Glass Barrier";

		this.xScale = 0.8;
		this.yScale = 0.8;
	}

	@Override
	public void updateBoundingBox() {

		double x = getCanvasPosition().x - (size.width * anchorPoint.x);
		double y = getCanvasPosition().y - (size.height * anchorPoint.y);

		this.boundingBox = new Rectangle(x, y, size.width, size.height);

		super.updateBoundingBox();
	}

	@Override
	public void update() {
		this.zRotation = 0;
		super.update();
	}
}
