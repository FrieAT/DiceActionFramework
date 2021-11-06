

import java.io.File;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
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

		
		// JavaFX Label
	    Label label = new Label();
	    label.setText(labelGraphic.labelText);
	    label.setLayoutX(labelGraphic.left);
	    label.setLayoutY(labelGraphic.top);
	    label.setFont(new Font(labelGraphic.fontSize));
	    
	    if (labelGraphic.bold) {
	    	label.setStyle("-fx-font-weight: bold;");
	    }

	    System.out.println("test");
	    
		return label;
	}

	@Override
	public void render(AGraphic g) {
		throw new NullPointerException("Need a Window to render in it, can't be alone called.");
	}
}
