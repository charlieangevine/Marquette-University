package levels;

import java.util.Arrays;
import java.util.List;

import CoreGraphics.CGPoint;
import nodes.Turret;

public class Level2 extends Level {

	public Level2() {
		super("Level2");
	}

	@Override
	public void sceneDidLoad() {
		super.sceneDidLoad();

		Turret turret = new Turret(Turret.PLAYER);
		turret.position = new CGPoint(-500, 0);
		this.addChild(turret);

		List<String> points = Arrays.asList("-250,-125", "-250,125", "0,200", "0,0", "0,-200", "250,-125", "250,125");
		for (String s : points) {
			Turret n = new Turret();
			String[] a = s.split(",");
			n.position = new CGPoint(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
			addChild(n);
		}

		Turret enemy = new Turret(Turret.ENEMY_RED);
		enemy.position = new CGPoint(500, 0);
		this.addChild(enemy);

	}

}
