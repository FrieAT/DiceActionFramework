

import java.io.File;
import java.util.HashMap;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PictureGraphicJavaFXRenderer extends JavaFXRenderer {
	private HashMap<String, Image> _cachedImages; //TODO: Replace with own FlyweightAssetManager-class.

	public PictureGraphicJavaFXRenderer() {
		super();

		this._cachedImages = new HashMap<>();
	}

	@Override
	public Node renderNode(AGraphic g) {
		PictureGraphic pictureGraphic;
		try {
			pictureGraphic = (PictureGraphic)g;
		} catch(ClassCastException e) {
			return null;
		}

		String imagePath = pictureGraphic.getPicturePath();
		
		Image image = this._cachedImages.get(imagePath);
		if(image == null) {
			image = new Image(new File(pictureGraphic.getPicturePath()).toURI().toString(), true);
		}

		
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(pictureGraphic.getWidth());
		imageView.setFitHeight(pictureGraphic.getHeight());
		imageView.setX(pictureGraphic.getLeft());	
		imageView.setY(pictureGraphic.getTop());	
		

		return imageView;
	}

	@Override
	public void render(AGraphic g) {
		throw new NullPointerException("Need a Window to render in it, can't be alone called.");
	}
}
