public class ButtonGraphic extends AGraphic {
    private Integer fontSize = 12;
    private Integer width = 0;
    private Integer height = 0;
    String picturePath;
    Boolean bold = false;
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
        return (int)this.getTransform().getGlobalPosition().y;
    }
    public void setTop(Integer top) {
        this.getTransform().setPosition(new Vector2(this.getLeft(), top));
    }
    public Integer getLeft() {
        return (int)this.getTransform().getGlobalPosition().x;
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
