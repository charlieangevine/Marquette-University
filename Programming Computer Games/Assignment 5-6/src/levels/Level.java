package levels;

import java.util.ArrayList;
import java.util.List;

import CoreGraphics.CGPoint;
import CoreGraphics.CGSize;
import SpriteKit.SKNode;
import SpriteKit.SKScene;
import SpriteKit.SKSpriteNode;
import javafx.scene.input.MouseEvent;
import main.Menu;
import main.Util;
import nodes.Button;
import nodes.Turret;
import runnables.levelSelect;
import runnables.nextLevel;
import runnables.resume;

public class Level extends SKScene {

	private Turret selected = null;
	private Menu pauseMenu;

	private Menu endGame;

	public Level(String name) {
		super(name);
	}

	@Override
	public void sceneDidLoad() {
		super.sceneDidLoad();

		SKSpriteNode background = new SKSpriteNode("background");
		for (double x = size.width * -0.5; x < size.width * 0.5; x += background.size.width) {
			for (double y = size.height * -0.5; y <= size.height * 0.5; y += background.size.height) {
				background = new SKSpriteNode("background");
				background.name = "background"; // DEBUG
				background.position = new CGPoint(x, y);
				background.zPosition = -Integer.MAX_VALUE;
				this.addChild(background);
			}
		}

		addMenu();

		Button pause = new Button(Util.Color(38, 212, 54), "pause", new CGSize(33, 28), new Runnable() {
			public void run() {
				pause();
			}
		});
		pause.zPosition = 998;
		pause.position = new CGPoint((-frame.size.width / 2) + 26, -(frame.size.height / 2) + 26);
		this.addChild(pause);
	}

	private void addMenu() {
		pauseMenu = new Menu(this);
		pauseMenu.addButton("Resume", new resume(this));
		pauseMenu.addButton("Level Select", new levelSelect());
		pauseMenu.zPosition = 1010;

		this.addChild(pauseMenu);

		endGame = new Menu(this);
		try {
			int i = Integer.parseInt(name.replace("Level", "")) + 1;
			Class.forName("levels." + i);
			endGame.addButton("Next Level", new nextLevel(this));
		} catch (ClassNotFoundException e) {
			// don't show next level, because they're aren't any
		}
		endGame.addButton("Level Select", new levelSelect());
		endGame.zPosition = 10010;

		this.addChild(endGame);

	}

	@Override
	public void updateLoop() {
		super.updateLoop();

		pauseMenu.setHidden(!isPaused);
		endGame.setHidden(enemies() != 0);
	}

	public void pause() {
		isPaused = true;
	}

	public void resume() {
		isPaused = false;
	}

	public List<Turret> turrets() {
		List<Turret> turrets = new ArrayList<>();
		for (SKNode child : children)
			if (child instanceof Turret)
				turrets.add((Turret) child);
		return turrets;
	}

	@Override
	public void touchesBegan(MouseEvent e) {
		super.touchesBegan(e);

		CGPoint loc = Util.getMousePosition(this, e);

		double distance = Double.MAX_VALUE;
		Turret selected = null;

		for (SKNode child : children)
			if (child instanceof Turret) {
				Turret turret = (Turret) child;
				double d = Util.distance(loc, turret.position);
				double angle = Math.atan2(loc.y - turret.position.y, loc.x - turret.position.x);

				if (turret.playerColor == Turret.PLAYER && d < distance
						&& Math.abs(Util.diff(turret.zRotation, angle)) < Math.PI / 12) {
					distance = d;
					selected = turret;
				}
			}
		this.selected = selected;
	}

	@Override
	public void touchesMoved(MouseEvent e) {
		super.touchesMoved(e);

		CGPoint loc = Util.getMousePosition(this, e);
		if (selected != null) {
			selected.zRotation = Math.atan2(loc.y - selected.position.y, loc.x - selected.position.x);
		}
	}

	@Override
	public void touchesEnded(MouseEvent e) {
		super.touchesEnded(e);

		selected = null;
	}

	public int enemies() {
		int enemies = 0;
		for (SKNode skNode : children)
			if (skNode instanceof Turret)
				if (((Turret) skNode).isEnemy())
					enemies++;
		return enemies;
	}
}
