package DAF.Renderer.Components;

import DAF.Math.Vector2;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class ButtonGraphic extends AGraphic {
    @JsonElement
    private Integer fontSize = 12;
    @JsonElement
    String picturePath;
    @JsonElement
    Boolean bold = false;
    @JsonElement
	String labelText ="Example Text";
    @JsonElement
    double borderRadius = 0.0;

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
    public void setBorderRadius(double radius) {
        this.borderRadius = radius;
    }
    public double getBorderRadius() {
        return this.borderRadius;
    }
}
