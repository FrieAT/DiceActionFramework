package DAF.Renderer.JavaFX;

import DAF.Math.Vector2;
import DAF.Renderer.Components.AGraphic;
import DAF.Renderer.Components.LabelGraphic;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LabelGraphicJavaFXRenderer extends JavaFXRenderer {
	private static final Font _defaultFont = new Font(1);

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
		label.setTextFill(Color.web(labelGraphic.getWebColor()));

		FontWeight curWeight = (labelGraphic.getBold() ? FontWeight.BOLD : FontWeight.NORMAL);
		if(Math.abs(label.getFont().getSize() - labelGraphic.getFontSize()) > 0.1
				|| !label.getFont().getStyle().equals(curWeight)) {
			label.setFont(Font.font(_defaultFont.getFamily(), curWeight, labelGraphic.getFontSize()));
		}
  
		return label;
	}

	@Override
	public void render(AGraphic g) {
		throw new NullPointerException("Need a Window to render in it, can't be alone called.");
	}
}
