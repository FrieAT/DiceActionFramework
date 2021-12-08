package DAF.Renderer.Components;

import DAF.Math.Vector2;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class ButtonGraphic extends AGraphic {
    @JsonElement
    private Integer fontSize = 12;
    @JsonElement
    private Integer width = 0;
    @JsonElement
    private Integer height = 0;
    @JsonElement
    String picturePath;
    @JsonElement
    Boolean bold = false;
    @JsonElement
	String labelText ="Example Text";
    
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
        return this.getTransform().getGlobalPosition().y.intValue();
    }
    public void setTop(Integer top) {
        this.getTransform().setPosition(new Vector2(this.getLeft(), top));
    }
    public Integer getLeft() {
        return this.getTransform().getGlobalPosition().x.intValue();
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
