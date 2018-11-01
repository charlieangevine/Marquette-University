package nodes;

import java.util.ArrayList;

import CoreGraphics.CGPoint;
import CoreGraphics.CGSize;
import CoreGraphics.CGVector;
import SpriteKit.SKNode;
import SpriteKit.SKSpriteNode;
import javafx.scene.shape.Circle;

public class Shot extends SKSpriteNode {

	private double vSpeed = 8;
	private Turret turret;

	public Shot(Turret turret, CGPoint position) {
		super("shot", turret.playerColor);

		this.name = "Shot";
		this.turret = turret;
		this.color = turret.playerColor;
		this.zRotation = turret.zRotation;
		this.position = position;

		this.xScale = 0.25;
		this.yScale = 0.25;

		this.size = new CGSize(size.width * xScale, size.height * yScale);

		this.velocity = new CGVector(Math.cos(this.zRotation) * vSpeed, Math.sin(this.zRotation) * vSpeed);
	}

	public void hit(SKNode node) {
		if (node instanceof WoodBarrier) {
			this.removeFromParent();
		} else if (node instanceof Shot) {
			this.removeFromParent();
			node.removeFromParent();
		} else if (node instanceof GlassBarrier) {
			double dx = Math.cos(node.zRotation + (Math.PI / 2)) * vSpeed;
			double dy = Math.sin(node.zRotation + (Math.PI / 2)) * vSpeed;

			dy = this.position.y > node.position.y ? Math.abs(dy) : -Math.abs(dy);
			dx = this.position.x > node.position.x ? Math.abs(dx) : -Math.abs(dx);

			this.velocity.applyImpulse(new CGVector(dx, dy));
		} else if (node instanceof Turret) {
			if (node != turret) {
				((Turret) node).hit(this);
				this.removeFromParent();
			}
		}
	}

	@Override
	public void update() {
		super.update();

		if (!(scene.frame.contains(position)))
			this.removeFromParent();

		for (SKNode node : new ArrayList<>(scene.children))
			if (node != this && node instanceof SKSpriteNode)
				if (boundingBox != null && node.boundingBox != null)
					if (boundingBox.getBoundsInParent().intersects(node.boundingBox.getBoundsInParent()))
						hit(node);

	}

	@Override
	public void updateBoundingBox() {
		this.boundingBox = new Circle(getCanvasPosition().x, getCanvasPosition().y, size.width * 0.5);

		super.updateBoundingBox();
	}

}
