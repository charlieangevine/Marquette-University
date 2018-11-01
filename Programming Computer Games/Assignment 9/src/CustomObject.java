
public enum CustomObject {
	MILLENNIUM_FALCON("m/Millennium_Falcon"), TIE_FIGHTER("t/TieFighter");

	String filePath;

	CustomObject(String fileName) {
		this.filePath = "res/" + fileName + ".obj";
	}
}
