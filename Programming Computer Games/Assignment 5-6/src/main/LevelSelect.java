package main;

import CoreGraphics.CGPoint;
import CoreGraphics.CGSize;
import SpriteKit.SKLabelNode;
import SpriteKit.SKScene;
import javafx.scene.paint.Color;
import levels.Level;
import nodes.Button;

public class LevelSelect extends SKScene {

	public LevelSelect() {
		super("LevelSelect");
	}

	@Override
	public void sceneDidLoad() {

		double x = (size.width * -0.5) + 50;
		double y = (size.height * -0.5) + 50;

		double width = 50;
		double height = 50;
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			try {
				Class.forName("levels.Level" + i);
				final int l = i;
				Button button = new Button(Color.WHITESMOKE, new CGSize(width, height), new Runnable() {

					@Override
					public void run() {
						try {
							@SuppressWarnings("unchecked")
							Class<? extends SKScene> clazz = (Class<? extends SKScene>) Class
									.forName("levels.Level" + l);
							Level level = (Level) clazz.getConstructor().newInstance();
							UntitledGame.presentScene(level);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}, "" + i);
				((SKLabelNode) button.content).fontSize = 18;
				button.zPosition = 1002 + children.size();
				this.addChild(button);

				button.position = new CGPoint(x, y);

				x += width * 1.5;
			} catch (Exception e) {
				break;
			}
		}
	}

}
