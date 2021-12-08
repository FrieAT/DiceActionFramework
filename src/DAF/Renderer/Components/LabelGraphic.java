package DAF.Renderer.Components;

import DAF.Math.Vector2;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class LabelGraphic extends AGraphic {
	@JsonElement
	private Integer fontSize = 12;
	@JsonElement
	Boolean bold = false;
	@JsonElement
	String labelText ="Example Text";
	
	@Override
	public void start() {
		super.start();
	}

	public Integer getTop() {
		return this.getTransform().getPosition().y.intValue();
	}
	public void setTop(Integer top) {
		this.getTransform().setPosition(new Vector2(this.getLeft(), top));
	}
	public Integer getLeft() {
		return this.getTransform().getPosition().x.intValue();
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
