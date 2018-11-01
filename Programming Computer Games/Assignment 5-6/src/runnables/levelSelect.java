package runnables;

import SpriteKit.SKScene;
import main.LevelSelect;
import main.UntitledGame;

public class levelSelect implements Runnable {

	private static SKScene select = new LevelSelect();

	@Override
	public void run() {
		UntitledGame.presentScene(select);
	}

}
