package nodes;

import CoreGraphics.CGPoint;
import CoreGraphics.CGSize;
import SpriteKit.SKLabelNode;
import SpriteKit.SKNode;
import SpriteKit.SKShapeNode;
import SpriteKit.SKSpriteNode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Button extends SKNode {

	public Color color;
	public SKNode content;
	public CGSize size;
	public Runnable onClick;

	public SKShapeNode foreground;
	public SKShapeNode background;

	public Button(Color color, SKNode content, CGSize size, Runnable onClick) {
		super(size);
		this.name = "Button";

		this.color = color;
		this.content = content;
		this.size = size;
		this.onClick = onClick;

		createButton();
		this.zPosition = 100;
	}

	public Button(Color color, String imageNamed, CGSize size, Runnable onClick) {
		this(color, new SKSpriteNode(imageNamed), size, onClick);

		((SKSpriteNode) content).xScale = 0.3;
		((SKSpriteNode) content).yScale = 0.3;
	}

	public Button(Color color, CGSize size, Runnable onClick, String text) {
		this(color, new SKLabelNode(text), size, onClick);
	}

	private void createButton() {
		background = new SKShapeNode(size, 8);
		background.position = new CGPoint(0, 5);
		background.fillColor = color.darker();
		background.name = "background";

		foreground = new SKShapeNode(size, 8);
		foreground.position = new CGPoint(0, -5);
		foreground.fillColor = color;
		foreground.name = "foreground";

		foreground.addChild(content);
		background.addChild(foreground);

		this.addChild(background);
	}

	@Override
	public void update() {
		super.update();

		double x = getCanvasPosition().x - (size.width * anchorPoint.x);
		double y = getCanvasPosition().y - (size.height * anchorPoint.y);

		this.boundingBox = new Rectangle(x, y, size.width, size.height);
	}

	@Override
	public void touchesBegan(CGPoint point) {
		if (isHidden())
			return;
		foreground.position = new CGPoint(0, -2.5);
	}

	@Override
	public void touchesEnded(CGPoint point) {
		if (isHidden())
			return;
		foreground.position = new CGPoint(0, -5);

		if (onClick != null)
			try {
				onClick.run();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}

}
