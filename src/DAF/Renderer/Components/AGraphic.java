package DAF.Renderer.Components;
import DAF.Components.AbstractComponent;
import DAF.Math.Vector2;
import DAF.Renderer.RenderManager;
import DAF.Serializer.Init;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public abstract class AGraphic extends AbstractComponent {

	@JsonElement
	Integer RenderingLayer = 1;

	@JsonElement
	boolean fillWidth = false;

	@JsonElement
	boolean fillHeight = false;

    @JsonElement
    private Integer width = 0;
    
	@JsonElement
    private Integer height = 0;

	@JsonElement
	String color = "rgba(0, 0, 0, 1.0)";

	@JsonElement
	String backgroundColor = "rgba(255, 255, 255, 1.0)";
	
	@Override
	public void start() {
		RenderManager.getInstance().add(this);
	}

	public Integer getRenderingLayer() {
		return RenderingLayer;
	}

	public void setRenderingLayer(Integer level) {
		this.RenderingLayer = level;
	}

	public void bindWidthToParent(boolean state) {
		this.fillWidth = state;
	}

	public void bindHeightToParent(boolean state) {
		this.fillHeight = state;
	}

	public String getWebColor() { return this.color; }

	public void setWebColor(String color) { this.color = color; }

	public String getWebBgColor() { return this.backgroundColor; }

	public void setWebBgColor(String color) { this.backgroundColor = color; }

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
		return (int)this.getTransform().getGlobalPosition().y.intValue();
	}

	public void setTop(Integer top) {
		this.getTransform().setPosition(new Vector2(this.getLeft(), top));
	}

	public Integer getLeft() {
		return this.getTransform().getGlobalPosition().x.intValue();
	}
	
	public void setLeft(Integer left) {
		this.getTransform().setPosition(new Vector2(left.doubleValue(), this.getTop().doubleValue()));
	}
}

