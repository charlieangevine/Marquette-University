package main;

import java.util.HashMap;

import SpriteKit.SKScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class UntitledGame extends Application {

	public static final boolean DEBUG = false;
	private static Stage stage;

	public static HashMap<String, SKScene> scenes = new HashMap<>();

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage theStage) {
		stage = theStage;

		LevelSelect select = new LevelSelect();
		presentScene(select);
	}

	public static void presentScene(SKScene scene) {
		if (scenes.containsKey(scene.name))
			scene = scenes.get(scene.name);
		else
			scenes.put(scene.name, scene);

		stage.setScene(scene);
		stage.setTitle("Untitled Game");

		stage.show();

	}

}
