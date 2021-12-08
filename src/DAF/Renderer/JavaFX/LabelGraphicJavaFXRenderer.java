package DAF.Renderer.JavaFX;

import DAF.Math.Vector2;
import DAF.Renderer.Components.AGraphic;
import DAF.Renderer.Components.LabelGraphic;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class LabelGraphicJavaFXRenderer extends JavaFXRenderer {
	@Override
	public Node renderNode(AGraphic g) {
		LabelGraphic labelGraphic;
		try {
			labelGraphic = (LabelGraphic)g;
		} catch(ClassCastException e) {
			return null;
		}

		Vector2 scale = g.getTransform().getScale();
		double rotation = g.getTransform().getRotation();

	    Label label = new Label();
	    label.setText(labelGraphic.getLabelText());
	    label.setLayoutX(labelGraphic.getLeft());
	    label.setLayoutY(labelGraphic.getTop());
		label.setScaleX(scale.x);
		label.setScaleY(scale.y);
		label.setRotate(rotation);
	    label.setFont(new Font(labelGraphic.getFontSize()));
	    
	    if (labelGraphic.getBold()) {
	    	label.setStyle("-fx-font-weight: bold;");
	    }
  
		return label;
	}

	@Override
	public void render(AGraphic g) {
		throw new NullPointerException("Need a Window to render in it, can't be alone called.");
	}
}
