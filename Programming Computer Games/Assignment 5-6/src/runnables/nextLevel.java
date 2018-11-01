package runnables;

import SpriteKit.SKScene;
import levels.Level;
import main.UntitledGame;

public class nextLevel implements Runnable {

	private Level level;

	public nextLevel(Level level) {
		this.level = level;
	}

	@Override
	public void run() {
		String name = level.name;
		int i = Integer.parseInt(name.replace("Level", "")) + 1;
		try {
			Class<? extends SKScene> clazz = (Class<? extends SKScene>) Class.forName("levels." + i);
			Level level = (Level) clazz.getConstructor().newInstance();
			UntitledGame.presentScene(level);
		} catch (Exception e) {

		}
	}

}
