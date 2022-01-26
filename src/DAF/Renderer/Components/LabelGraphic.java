package DAF.Renderer.Components;

import DAF.Math.Vector2;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class LabelGraphic extends AGraphic {
	@JsonElement
	private Integer fontSize = 12;
	@JsonElement
	private String fontFamily = "Arial";
	@JsonElement
	Boolean bold = false;
	@JsonElement
	String labelText ="Example Text";
	
	@Override
	public void start() {
		super.start();
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

	public String getFontFamily() { return this.fontFamily; }
	public void setFontFamily(String family) { this.fontFamily = family; }
}
