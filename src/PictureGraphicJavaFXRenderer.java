

import java.io.File;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PictureGraphicJavaFXRenderer extends JavaFXRenderer {

	@Override
	public Node renderNode(AGraphic g) {
		PictureGraphic pictureGraphic = (PictureGraphic)g;
		if(pictureGraphic == null) {
			return null;
		}

		Image image = new Image(new File(pictureGraphic.getPicturePath()).toURI().toString(), true);
			
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setY(pictureGraphic.getTop());
		imageView.setX(pictureGraphic.getLeft());

		return imageView;
	}

	@Override
	public void render(AGraphic g) {
		throw new NullPointerException("Need a Window to render in it, can't be alone called.");
	}
}
