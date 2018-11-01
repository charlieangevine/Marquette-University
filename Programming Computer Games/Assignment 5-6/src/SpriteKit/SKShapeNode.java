package SpriteKit;

import CoreGraphics.CGSize;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SKShapeNode extends SKNode {

	public double cornerRadius;

	public Color fillColor = new Color(0, 0, 0, 0);
	public Color strokeColor = new Color(0, 0, 0, 0);

	public SKShapeNode(CGSize size, double cornerRadius) {
		super(size);
		this.cornerRadius = cornerRadius;
	}

	public SKShapeNode(CGSize size) {
		this(size, 0);
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(fillColor);
		gc.setStroke(strokeColor);

		gc.fillRect(size.width * -0.5, size.height * -0.5, size.width, size.height);
		gc.strokeRect(size.width * -0.5, size.height * -0.5, size.width, size.height);
	}
}
