
import java.io.File;

public class PictureGraphic extends AGraphic {
	private Integer width = 0;
	private Integer height = 0;
	String picturePath;
	
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
		return (int)this.getTransform().getPosition().y;
	}
	public void setTop(Integer top) {
		this.getTransform().setPosition(new Vector2(this.getLeft(), top));
	}
	public Integer getLeft() {
		return (int)this.getTransform().getPosition().x;
	}
	public void setLeft(Integer left) {
		this.getTransform().setPosition(new Vector2(left, this.getTop()));
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
}
