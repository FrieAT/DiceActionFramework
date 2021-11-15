import java.io.File;

public class LabelGraphic extends AGraphic {
	private Integer fontSize = 12;
	Boolean bold = false;
	String labelText ="Example Text";
	
	@Override
	public void start() {
		super.start();
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
