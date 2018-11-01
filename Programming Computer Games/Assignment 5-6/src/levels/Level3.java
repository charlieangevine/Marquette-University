package levels;

import java.util.Arrays;
import java.util.List;

import CoreGraphics.CGPoint;
import nodes.Turret;

public class Level3 extends Level {

	public Level3() {
		super("Level3");
	}

	@Override
	public void sceneDidLoad() {
		super.sceneDidLoad();

		Turret player1 = new Turret(Turret.PLAYER);
		player1.position = new CGPoint(-500, -250);
		this.addChild(player1);

		Turret player2 = new Turret(Turret.PLAYER);
		player2.position = new CGPoint(-500, 250);
		this.addChild(player2);

		Turret enemy1 = new Turret(Turret.ENEMY_RED);
		enemy1.position = new CGPoint(500, -250);
		this.addChild(enemy1);

		Turret enemy2 = new Turret(Turret.ENEMY_RED);
		enemy2.position = new CGPoint(500, 250);
		this.addChild(enemy2);

		List<CGPoint> points = Arrays.asList(new CGPoint(0, 0), new CGPoint(-250, 150), new CGPoint(-250, -150),
				new CGPoint(250, 150), new CGPoint(250, -150));

		for (CGPoint position : points) {

			Turret neutral = new Turret(Turret.NEUTRAL);
			neutral.position = position;
			this.addChild(neutral);
		}
	}

}
