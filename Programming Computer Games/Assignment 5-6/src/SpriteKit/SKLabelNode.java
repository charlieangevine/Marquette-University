package SpriteKit;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class SKLabelNode extends SKNode {

	public String text;
	public Color fontColor = Color.BLACK;
	public int fontSize = 16;
	public TextAlignment horizontalAlignment = TextAlignment.CENTER;
	public VPos verticalAlignment = VPos.CENTER;

	public SKLabelNode(String text) {
		super();
		this.text = text;
	}

	protected void draw(GraphicsContext gc) {
		gc.setFill(fontColor);
		gc.setTextAlign(horizontalAlignment);
		gc.setTextBaseline(verticalAlignment);
		gc.setFont(new Font(fontSize));
		gc.fillText(text, 0, 0);
	}
}
