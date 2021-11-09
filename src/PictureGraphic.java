
import java.io.File;

public class PictureGraphic extends AGraphic {

	private Integer top = 0;
	private Integer left = 0;
	private Integer width = 0;
	private Integer height = 0;
	String picturePath;
	
	@Override
	public void start() {
		super.start();

		this.setTop(this.top);
		this.setLeft(this.left);
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
		if(this.getTransform() == null) {
			return this.top;
		}
		return this.getTransform().getPosition().y;
	}
	public void setTop(Integer top) {
		if(this.getTransform() == null) {
			this.top = top;
			return;
		}
		this.getTransform().setPosition(new Vector2(this.getLeft(), top));
	}
	public Integer getLeft() {
		if(this.getTransform() == null) {
			return this.left;
		}
		return this.getTransform().getPosition().x;
	}
	public void setLeft(Integer left) {
		if(this.getTransform() == null) {
			this.left = left;
			return;
		}
		this.getTransform().setPosition(new Vector2(left, this.getTop()));
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
}
