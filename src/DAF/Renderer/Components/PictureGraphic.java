package DAF.Renderer.Components;

import DAF.Math.Vector2;
import DAF.Serializer.Init;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class PictureGraphic extends AGraphic {

	@JsonElement
	private Integer width = 0;
	@JsonElement
	private Integer height = 0;
	@JsonElement
	String picturePath;
	
	public PictureGraphic() {
		super();
		
		backgroundColor = "rgba(255, 255, 255, 0.0)";
	}

	@Override
	public void start() {
		super.start();
	}

	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getTop() {
		return (int)this.getTransform().getGlobalPosition().y.intValue();
	}
	public void setTop(Integer top) {
		this.getTransform().setPosition(new Vector2(this.getLeft(), top));
	}
	public Integer getLeft() {
		return this.getTransform().getGlobalPosition().x.intValue();
	}
	public void setLeft(Integer left) {
		this.getTransform().setPosition(new Vector2(left.doubleValue(), this.getTop().doubleValue()));
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}


	@Init
	private void initValues() {

	}
}
