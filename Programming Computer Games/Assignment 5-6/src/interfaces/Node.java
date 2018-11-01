package interfaces;

import SpriteKit.SKNode;

public interface Node {

	public void addChild(SKNode node);

	public void removeChild(SKNode node);

	public SKNode childNodeWithName(String name);

	public boolean isHidden();
}
