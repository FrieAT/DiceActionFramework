import java.io.File;

public class LabelGraphic extends AGraphic {

	private Integer top = 0;
	private Integer left = 0;
	private Integer fontSize = 12;
	Boolean bold = false;
	String labelText ="Example Text";
	
	@Override
	public void start() {
		super.start();

		this.setTop(this.top);
		this.setLeft(this.left);
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
	public Integer getFontSize() {
		return fontSize;
	}
	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}
	public Boolean getBold() {
		return bold;
	}
	public void setBold(Boolean bold) {
		this.bold = bold;
	}
	public String getLabelText() {
		return labelText;
	}
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}
}
