package SpriteKit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import CoreGraphics.CGPoint;
import CoreGraphics.CGRect;
import CoreGraphics.CGSize;
import interfaces.Node;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import main.Util;

public class SKScene extends Scene implements Node {

	public String name;

	public Canvas canvas;
	private GraphicsContext gc;

	public List<SKNode> children = new ArrayList<>();
	public CGPoint anchorPoint = new CGPoint(0.5, 0.5);
	public final CGSize size = new CGSize(1334, 750);
	public final CGRect frame;

	private int gameCounter = 0;
	public boolean isPaused = false;

	public SKScene(String name) {
		super(new Group());
		this.name = name;
		this.frame = new CGRect(new CGPoint(size.width * -1 * anchorPoint.x, size.height * -1 * anchorPoint.y), size);

		canvas = new Canvas(size.width, size.height);
		((Group) getRoot()).getChildren().add(canvas);

		this.gc = canvas.getGraphicsContext2D();

		KeyFrame kf = new KeyFrame(Duration.millis(50), e -> {
			updateLoop();
		});

		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		sceneDidLoad();
		setHandlers();
	}

	public void setHandlers() {
		setOnMousePressed(e -> {
			touchesBegan(e);
		});
		setOnMouseDragged(e -> {
			touchesMoved(e);
		});
		setOnMouseReleased(e -> {
			touchesEnded(e);
		});
	}

	@Override
	public void addChild(SKNode node) {
		node.setScene(this);
		node.parent = this;
		children.add(node);
	}

	@Override
	public void removeChild(SKNode node) {
		children.remove(node);

		ArrayList<SKNode> clone = new ArrayList<>();
		clone.addAll(node.children);
		for (SKNode child : clone)
			node.removeChild(child);

		node.parent = null;
	}

	@Override
	public SKNode childNodeWithName(String name) {
		for (SKNode child : children)
			if (child.name.equals(name))
				return child;
		return null;
	}

	public void updateLoop() {
		gc.clearRect(0, 0, size.width, size.height);

		for (javafx.scene.Node node : new ArrayList<>(((Group) this.getRoot()).getChildren())) {
			if (!(node instanceof Canvas))
				((Group) this.getRoot()).getChildren().remove(node);
		}

		boolean callGameTick = false;

		if (gameCounter++ >= 10) {
			callGameTick = true;
			gameCounter = 0;
		}
		for (SKNode node : nodes()) {
			if (isPaused && node instanceof GameNode)
				continue;
			node.update();
			if (callGameTick)
				node.gameTick();

		}

		for (SKNode node : nodes())
			if (!node.isHidden())
				node.render(gc);
		this.update();

	}

	public List<SKNode> nodes() {
		List<SKNode> nodes = new ArrayList<>();
		for (SKNode node : new ArrayList<>(children)) {
			add(nodes, node);
		}
		nodes.sort(new Comparator<SKNode>() {
			@Override
			public int compare(SKNode o1, SKNode o2) {
				return Double.compare(o1.zPosition, o2.zPosition);
			}
		});
		return nodes;
	}

	private void add(List<SKNode> list, SKNode node) {
		list.add(node);
		for (SKNode skNode : node.children)
			add(list, skNode);
	}

	/** Mouse Handlers **/

	public void touchesBegan(MouseEvent e) {
		CGPoint point = Util.getMousePosition(this, e);
		for (SKNode node : nodes()) {
			if (node.frame.contains(point))
				node.touchesBegan(point);
		}
	}

	public void touchesEnded(MouseEvent e) {
		CGPoint point = Util.getMousePosition(this, e);
		for (SKNode node : nodes())
			if (node.frame.contains(point))
				node.touchesEnded(point);
	}

	public void touchesMoved(MouseEvent e) {
		CGPoint point = Util.getMousePosition(this, e);
		for (SKNode node : nodes())
			if (node.frame.contains(point))
				node.touchesMoved(point);
	}

	/**
	 * Overrideable functions
	 * 
	 */

	public void update() {
	}

	public void sceneDidLoad() {
	}

	public boolean isHidden() {
		return false;
	}

}