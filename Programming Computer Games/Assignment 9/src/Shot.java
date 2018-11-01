import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Shot extends Box {

	double velocityX = 0;
	double velocityY = 0;
	double velocityZ = 0;

	public Shot(double x, double y, double z) {
		super(10, 10, 50);

		setTranslateX(x);
		setTranslateY(y);
		setTranslateZ(z);

		PhongMaterial mat = new PhongMaterial();
		mat.setSpecularColor(Color.RED);
		mat.setDiffuseColor(Color.RED);

		setMaterial(mat);

		this.velocityZ = 100;
	}

	void update() {
		// System.out.println((int) getTranslateX() + "," + (int)
		// getTranslateY() + "," + (int) getTranslateZ());
		setTranslateX(getTranslateX() + velocityX);
		setTranslateY(getTranslateY() + velocityY);
		setTranslateZ(getTranslateZ() + velocityZ);
	}
}
