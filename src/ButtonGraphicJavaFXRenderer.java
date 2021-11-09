
import java.io.File;
import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonGraphicJavaFXRenderer extends JavaFXRenderer {
	private HashMap<String, Image> _cachedImages; //TODO: Replace with own FlyweightAssetManager-class.

	private HashMap<Integer, Button> _cachedNodes;

	public ButtonGraphicJavaFXRenderer() {
		super();

		this._cachedImages = new HashMap<>();

		this._cachedNodes = new HashMap<>();
	}

	@Override
	public Node renderNode(AGraphic g) {
		ButtonGraphic buttonGraphic;
		try {
			buttonGraphic = (ButtonGraphic)g;
		} catch(ClassCastException e) {
			return null;
		}

		String imagePath = buttonGraphic.getPicturePath();
		
		Image image = this._cachedImages.get(imagePath);
		if(image == null) {
			String path = buttonGraphic.getPicturePath();
			if(path != null) {
				image = new Image(new File(buttonGraphic.getPicturePath()).toURI().toString(), true);
			}
		}

		Vector2 scale = g.getTransform().getScale();
		double rotation = g.getTransform().getRotation();

		ImageView imageView = null;
		if(image != null) {
			imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(buttonGraphic.getWidth() * scale.x);
			imageView.setFitHeight(buttonGraphic.getHeight() * scale.y);
		}

		int gameObjectId = g.getGameObject().getId();
		Button buttonView = this._cachedNodes.get(gameObjectId);
		if(buttonView == null) {
			buttonView = new Button(buttonGraphic.labelText, imageView);
			this._cachedNodes.put(gameObjectId, buttonView);
		}
		
		buttonView.setLayoutX(buttonGraphic.getLeft());	
		buttonView.setLayoutY(buttonGraphic.getTop());
		buttonView.setScaleX(scale.x);
		buttonView.setScaleY(scale.y);
		buttonView.setRotate(rotation);

		//FIXME: On macOS wrong width gets examined.
		if(buttonView.getWidth() > buttonGraphic.getWidth()) {
			buttonGraphic.setWidth((int)buttonView.getWidth());
		}

		//FIXME: On macOS wrong height gets examined.
		if(buttonView.getHeight() > buttonGraphic.getHeight()) {
			buttonGraphic.setHeight((int)buttonView.getHeight());
		}

		return buttonView;
	}

	@Override
	public void render(AGraphic g) {
		throw new NullPointerException("Need a Window to render in it, can't be alone called.");
	}
}
