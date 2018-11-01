package nodes;

import java.util.Arrays;
import java.util.List;

import CoreGraphics.CGPoint;
import SpriteKit.SKSpriteNode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import levels.Level;
import main.Util;

public class Turret extends SKSpriteNode {

	public TurretGun turretGun;
	public Double targetZRotation = null;
	public Turret target = null;

	public Color playerColor;

	public static final Color PLAYER = Util.Color(115, 250, 121);
	public static final Color ENEMY_RED = Util.Color(255, 126, 121);
	public static final Color NEUTRAL = Color.WHITE;

	public static final List<Color> enemies = Arrays.asList(ENEMY_RED);

	public Turret(Color playerColor) {
		super("turret", playerColor);

		this.name = "Turret";

		this.xScale = 0.6;
		this.yScale = 0.6;

		this.turretGun = new TurretGun(playerColor);
		this.playerColor = playerColor;

		this.addChild(this.turretGun);
	}

	public Turret() {
		this(Color.WHITE);
	}

	public void hit(Shot shot) {
		this.shiftColor(shot.color, 16);
	}

	public boolean isEnemy() {
		return enemies.contains(playerColor);
	}

	public void pointTo(CGPoint loc) {
		double angle = Math.atan2(loc.y - this.position.y, loc.x - this.position.x);
		double diff = Util.diff(this.zRotation, angle);

		this.targetZRotation = angle;
		double n = Math.toRadians(10);
		this.angularVelocity = diff > 0 ? n : -n;

	}

	@Override
	public void gameTick() {
		super.gameTick();

		if (isEnemy())
			AI();

		if (playerColor != Turret.NEUTRAL)
			this.turretGun.fire();
	}

	private void AI() {
		if (target != null) {

			if (target.playerColor == this.playerColor)
				target = null;

			return;
		}

		Turret closest = null;
		Level level = (Level) scene;

		double d = Integer.MAX_VALUE;

		for (Turret turret : level.turrets()) {
			double distance = Util.distance(this.position, turret.position);
			if (turret.playerColor == Turret.PLAYER)
				distance -= 50;
			if (turret != this && turret.playerColor != this.playerColor && distance < d) {
				d = distance;
				closest = turret;
			}
		}
		if (closest == null)
			return;
		target = closest;
		pointTo(target.position);
	}

	@Override
	public void update() {
		super.update();

		if (this.targetZRotation != null) {
			if (Math.abs(Util.diff(this.zRotation, this.targetZRotation)) < (Math.toRadians(5))) {
				this.angularVelocity = 0;
				this.zRotation = this.targetZRotation;
				this.targetZRotation = null;
			}
		}
	}

	@Override
	public void updateBoundingBox() {
		this.boundingBox = new Circle(getCanvasPosition().x, getCanvasPosition().y, size.width * 0.5);

		super.updateBoundingBox();
	}

	public void shiftColor(Color newColor, double amount) {
		double newRed = newColor.getRed() * 255.0;
		double newGreen = newColor.getGreen() * 255.0;
		double newBlue = newColor.getBlue() * 255.0;

		double red = this.color.getRed() * 255.0;
		double green = this.color.getGreen() * 255.0;
		double blue = this.color.getBlue() * 255.0;

		if (red != newRed)
			red = red < newRed ? red + amount : red - amount;
		if (green != newGreen)
			green = green < newGreen ? green + amount : green - amount;
		if (blue != newBlue)
			blue = blue < newBlue ? blue + amount : blue - amount;

		red = Math.min(Math.max(0, red), 255);
		green = Math.min(Math.max(0, green), 255);
		blue = Math.min(Math.max(0, blue), 255);

		if (Math.abs(newRed - red) <= amount && Math.abs(newGreen - green) <= amount
				&& Math.abs(newBlue - blue) <= amount) {
			this.playerColor = newColor;
			this.color = playerColor;
			this.turretGun.color = playerColor;
		} else {
			newColor = new Color(red / 255.0, green / 255.0, blue / 255.0, 1);
			this.color = newColor;
			this.turretGun.color = newColor;
		}

	}
}
