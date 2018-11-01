package levels;

import CoreGraphics.CGPoint;
import javafx.geometry.Orientation;
import nodes.GlassBarrier;
import nodes.Turret;

public class Level1 extends Level {

	public Level1() {
		super("Level1");
	}

	@Override
	public void sceneDidLoad() {
		super.sceneDidLoad();

		Turret turret = new Turret(Turret.PLAYER);
		turret.position = new CGPoint(-500, 200);
		this.addChild(turret);

		Turret turret2 = new Turret(Turret.PLAYER);
		turret2.position = new CGPoint(-500, -200);
		this.addChild(turret2);

		Turret enemy = new Turret(Turret.ENEMY_RED);
		enemy.position = new CGPoint(500, 0);
		this.addChild(enemy);

		GlassBarrier gb1 = new GlassBarrier(Orientation.HORIZONTAL);
		gb1.position = new CGPoint(0, -350);
		this.addChild(gb1);

		GlassBarrier gb2 = new GlassBarrier(Orientation.HORIZONTAL);
		gb2.position = new CGPoint(0, 350);
		this.addChild(gb2);
	}

}
