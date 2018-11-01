package SpriteKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import CoreGraphics.CGPoint;
import CoreGraphics.CGRect;
import CoreGraphics.CGSize;
import interfaces.Node;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import main.UntitledGame;

public class SKNode implements Node {

	public Node parent = null;
	public SKScene scene;
	private boolean hidden = false;
	public String name;
	public CGPoint position = new CGPoint(0, 0);
	public CGSize size = new CGSize(0, 0);
	public CGRect frame = new CGRect(position, size);
	public CGPoint anchorPoint = new CGPoint(0.5, 0.5);
	public double zRotation = 0; // Radians
	public double zPosition = 0;

	public Shape boundingBox;

	public List<SKNode> children = new ArrayList<>();

	public SKNode(CGSize size) {
		this.size = size;
	}

	public SKNode() {
		this(new CGSize(0, 0));
	}

	public void addChild(SKNode node) {
		node.setScene(scene);
		node.parent = this;
		children.add(node);
	}

	public void setScene(SKScene scene) {
		this.scene = scene;
		for (SKNode skNode : children)
			skNode.setScene(scene);
	}

	public SKNode childNodeWithName(String name) {
		for (SKNode child : children)
			if (child.name.equals(name))
				return child;
		return null;
	}

	public void removeChild(SKNode node) {
		children.remove(node);
		for (SKNode child : new ArrayList<>(node.children))
			node.removeChild(child);
		node.parent = null;
	}

	public void removeFromParent() {
		if (parent != null)
			parent.removeChild(this);

	}

	public boolean isHidden() {
		if (parent.isHidden())
			return true;
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void render(GraphicsContext gc) { // called every 10 milliseconds
		gc.save();

		// Change to SKScene Coordinate System
		gc.translate(scene.size.width * scene.anchorPoint.x, scene.size.height * scene.anchorPoint.y);

		// Using stack to loop through parent elements from highest parent to
		// lowest
		Stack<SKNode> stack = new Stack<>();
		SKNode node = this;

		while (node != null) {
			stack.push(node);
			if (node.parent instanceof SKNode)
				node = (SKNode) node.parent;
			else
				node = null;
		}

		while (!stack.isEmpty()) {
			SKNode skNode = stack.pop();
			gc.translate(skNode.position.x, skNode.position.y);
			gc.rotate(Math.toDegrees(skNode.zRotation));
		}

		draw(gc);

		gc.restore();
	}

	public CGPoint getCanvasPosition() {
		CGPoint point = new CGPoint(scene.size.width * scene.anchorPoint.x, scene.size.height * scene.anchorPoint.y);

		SKNode node = this;
		while (node != null) {
			point.add(node.position.x, node.position.y);
			if (!(node.parent instanceof SKNode))
				break;
			node = (SKNode) node.parent;
		}
		return point;
	}

	public CGRect canvasFrame() {
		return new CGRect(getCanvasPosition(), size);
	}

	/**
	 * 
	 * Override-able functions
	 */

	public void touchesBegan(CGPoint point) {
	}

	public void touchesEnded(CGPoint point) {
	}

	public void touchesMoved(CGPoint point) {
	}

	public void gameTick() { // called every second
	}

	public void update() { // called every 10 milliseconds, before render
		this.frame = new CGRect(this.position.x - (size.width * 0.5), this.position.y - (size.height * 0.5), size.width,
				size.height);

		if (UntitledGame.DEBUG && boundingBox != null)
			((Group) scene.getRoot()).getChildren().remove(boundingBox);
		updateBoundingBox();
		if (UntitledGame.DEBUG && boundingBox != null && parent != null)
			((Group) scene.getRoot()).getChildren().add(boundingBox);
	}

	public void updateBoundingBox() {
		if (this.boundingBox != null) {
			this.boundingBox.setFill(null);
			this.boundingBox.setStroke(Color.AQUA);
			this.boundingBox.setStrokeWidth(1);
		}
	}

	protected void draw(GraphicsContext gc) {
	}
}
