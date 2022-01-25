package DAF.Renderer.Components;
import DAF.Components.AbstractComponent;
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

	@Init
	private void initValues() {
		this.RenderingLayer = 0;
	}
}

