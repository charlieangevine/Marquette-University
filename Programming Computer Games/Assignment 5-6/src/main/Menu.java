package main;

import CoreGraphics.CGPoint;
import CoreGraphics.CGSize;
import SpriteKit.SKLabelNode;
import SpriteKit.SKNode;
import SpriteKit.SKScene;
import javafx.scene.paint.Color;
import nodes.Button;

public class Menu extends SKNode {

	private SKScene scene;

	private final Color backgroundColor = Util.Color(38, 212, 54);

	private final double width = 150;
	private final double height = 35;

	public Menu(SKScene scene) {
		this.scene = scene;
	}

	private void refresh() {
		double x = scene.getWidth() / 2;
		double y = (scene.getHeight() / 2) - (children.size() * height * .75);
		for (SKNode node : children) {
			if (node instanceof Button) {
				Button button = (Button) node;
				button.position = new CGPoint(x, y);
				y += height * 1.5;
			}
		}
	}

	public void addButton(String text, Runnable onClick) {
		Button button = new Button(backgroundColor, new CGSize(width, height), onClick, text);
		((SKLabelNode) button.content).fontColor = Color.WHITE;
		((SKLabelNode) button.content).fontSize = 18;
		button.zPosition = 1002 + children.size();
		this.addChild(button);

		refresh();
	}
}
