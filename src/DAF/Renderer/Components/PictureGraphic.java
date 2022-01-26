package DAF.Renderer.Components;

import DAF.Math.Vector2;
import DAF.Serializer.Init;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class PictureGraphic extends AGraphic {
	@JsonElement
	String picturePath = "";
	
	public PictureGraphic() {
		super();

		this.backgroundColor = "rgba(255, 255, 255, 0.0)";
	}

	@Override
	public void start() {
		super.start();
	}

	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
}
