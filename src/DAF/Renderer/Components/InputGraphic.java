package DAF.Renderer.Components;

import DAF.Math.Vector2;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class InputGraphic extends AGraphic {
    @JsonElement
    private Integer fontSize = 12;
    @JsonElement
    private Integer width = 0;
    @JsonElement
    private Integer height = 0;
    @JsonElement
    Boolean bold = false;
    @JsonElement
    String text ="Example Text";

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
    public void setTop(Integer top) { this.getTransform().setPosition(new Vector2(this.getLeft(), top)); }
    public Integer getLeft() {
        return this.getTransform().getGlobalPosition().x.intValue();
    }
    public void setLeft(Integer left) { this.getTransform().setPosition(new Vector2(left, this.getTop())); }
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
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
