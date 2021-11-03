

import java.io.File;

import javafx.scene.Node;

public class LabelGraphicJavaFXRenderer extends JavaFXRenderer {
	@Override
	public Node renderNode(AGraphic g) {
		LabelGraphic labelGraphic;
		try {
			labelGraphic = (LabelGraphic)g;
		} catch(ClassCastException e) {
			return null;
		}

		//TODO: Implement how to create a LabelGraphic for JavaFX.

		return null; //TODO: replace with correct JavaFX Node type.
	}

	@Override
	public void render(AGraphic g) {
		throw new NullPointerException("Need a Window to render in it, can't be alone called.");
	}
}
