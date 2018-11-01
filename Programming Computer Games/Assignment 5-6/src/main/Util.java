package main;

import java.awt.image.BufferedImage;

import CoreGraphics.CGPoint;
import SpriteKit.SKNode;
import SpriteKit.SKScene;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Util {

	public static double distance(CGPoint p1, CGPoint p2) {
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}

	public static double diff(double a1, double a2) {
		while (a1 < 0)
			a1 += Math.PI * 2;
		while (a1 > 360)
			a1 -= Math.PI * 2;

		while (a2 < 0)
			a2 += Math.PI * 2;
		while (a2 > 360)
			a2 -= Math.PI * 2;

		double diff = a1 - a2;
		if (diff > Math.PI)
			diff -= Math.PI * 2;
		return diff;
	}

	public static double diff2(double a1, double a2) {
		boolean neg = (a2 - a1) < 0;
		double R = Math.abs(a2 - a1) % 2 * Math.PI;
		if (R > Math.PI) {
			R = R - (2 * Math.PI);
		}
		return neg ? -R : R;
	}

	public static Image recolor(Image image, Color color) {
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);

		for (int x = 0; x < bImage.getWidth(); x++)
			for (int y = 0; y < bImage.getHeight(); y++) {
				int clr = bImage.getRGB(x, y);

				int red = (clr & 0x00ff0000) >> 16;
				int green = (clr & 0x0000ff00) >> 8;
				int blue = clr & 0x000000ff;

				int diff = (255 - red) + (255 - green) + (255 - blue);

				int rgb = 255;
				rgb = (rgb << 8) + ((int) (color.getRed() * 255));
				rgb = (rgb << 8) + ((int) (color.getGreen() * 255));
				rgb = (rgb << 8) + ((int) (color.getBlue() * 255));
				if (diff < 15)
					bImage.setRGB(x, y, rgb);

			}

		return SwingFXUtils.toFXImage(bImage, null);
	}

	public static CGPoint getMousePosition(SKNode node, MouseEvent e) {
		double x = e.getX() - (node.anchorPoint.x * node.size.width);
		double y = e.getY() - (node.anchorPoint.y * node.size.height);

		return new CGPoint(x, y);
	}

	public static CGPoint getMousePosition(SKScene scene, MouseEvent e) {
		double x = e.getX() - (scene.anchorPoint.x * scene.size.width);
		double y = e.getY() - (scene.anchorPoint.y * scene.size.height);

		return new CGPoint(x, y);
	}

	public static Color Color(double r, double g, double b, double a) {
		return new Color(r / 255.0, g / 255.0, b / 255.0, a / 255.0);
	}

	public static Color Color(double r, double g, double b) {
		return Color(r, g, b, 255);
	}
}
