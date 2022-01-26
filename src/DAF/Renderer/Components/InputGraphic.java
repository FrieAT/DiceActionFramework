package DAF.Renderer.Components;

import DAF.Math.Vector2;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class InputGraphic extends AGraphic {
    @JsonElement
    private Integer fontSize = 12;
    @JsonElement
    Boolean bold = false;
    @JsonElement
    String text ="Example Text";

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
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
