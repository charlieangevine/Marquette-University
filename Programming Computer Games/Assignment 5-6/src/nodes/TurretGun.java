package nodes;

import CoreGraphics.CGPoint;
import CoreGraphics.CGVector;
import SpriteKit.SKSpriteNode;
import javafx.scene.paint.Color;

public class TurretGun extends SKSpriteNode {

	public TurretGun(Color playerColor) {
		super("turret_gun", playerColor);

		this.name = "Turret Gun";

		this.xScale = 0.5;
		this.yScale = 0.5;

		this.color = playerColor;

		this.zPosition = -1;
		this.position = new CGPoint(5, 0);
	}

	public CGPoint endOfTurretPosition() {
		Turret turret = (Turret) parent;

		double turretWidth = turret.size.width * 0.5;
		double gunWidth = this.size.width * 0.5;

		double hyp = turretWidth + gunWidth;

		double x = turret.position.x + (Math.cos(turret.zRotation) * hyp);
		double y = turret.position.y + (Math.sin(turret.zRotation) * hyp);

		return new CGPoint(x, y);
	}

	public void fire() {
		this.velocity = new CGVector(-1, 0);
	}

	public void shoot() {
		Turret turret = (Turret) parent;
		Shot shot = new Shot(turret, this.endOfTurretPosition());

		if (!(this.scene == null))
			this.scene.addChild(shot);
	}

	@Override
	public void update() {
		super.update();

		if (this.position.x <= 0) {
			this.velocity = new CGVector(1, 0);
			shoot();
		}
		if (this.position.x >= 5) {
			this.velocity = CGVector.zero;
			this.position = new CGPoint(5, 0);
		}
	}
}