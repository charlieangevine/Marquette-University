import javafx.scene.media.AudioClip;

public class SoundFX {

	public static AudioClip COIN;
	public static AudioClip RELOAD;
	public static AudioClip BOOM;

	public static void initialize() {
		COIN = load("coin.wav");
		RELOAD = load("reload.wav");
		BOOM = load("boom.wav");
	}

	private static AudioClip load(String file) {
		return new AudioClip(ClassLoader.getSystemResource("sounds/" + file).toString());
	}
}
