package SpriteKit;

import CoreGraphics.CGPoint;
import CoreGraphics.CGSize;
import CoreGraphics.CGVector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.UntitledGame;
import main.Util;

public class SKSpriteNode extends GameNode {

	private Image image;

	public Color color = null;
	public double xScale = 1;
	public double yScale = 1;

	public double angularVelocity = 0;
	public CGVector velocity = new CGVector(0, 0);

	public SKSpriteNode(String imageName) {
		super();
		this.image = new Image(UntitledGame.class.getResourceAsStream("/assets/" + imageName + ".png"));
		// this.image = new Image("file:assets/" + imageName + ".png");
		this.size = new CGSize(image.getWidth(), image.getHeight());
	}

	public SKSpriteNode(String imageName, Color color) {
		this(imageName);

		this.color = color;

		this.image = new Image(UntitledGame.class.getResourceAsStream("/assets/" + imageName + ".png"));
		// this.image = new Image("file:assets/" + imageName + ".png");
		this.size = new CGSize(image.getWidth() * xScale, image.getHeight() * yScale);
	}

	@Override
	protected void draw(GraphicsContext gc) {

		Image image = this.image;

		if (color != null)
			image = Util.recolor(image, color);

		double width = size.width;
		double height = size.height;

		double x = width * -anchorPoint.x;
		double y = height * -anchorPoint.y;

		gc.drawImage(image, x, y, width, height);
	}

	@Override
	public void update() {
		this.size = new CGSize(image.getWidth() * xScale, image.getHeight() * yScale);

		simulatePhysics();
		super.update();
	}

	private void simulatePhysics() {
		this.position.add(velocity);
		this.zRotation += angularVelocity;
	}

	@Override
	public void touchesBegan(CGPoint point) {
		super.touchesBegan(point);
	}
}
