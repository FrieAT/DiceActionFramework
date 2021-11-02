
import java.io.File;

public abstract class AGraphic implements IComponent {

	Integer RenderingLayer;
	

	public Integer getRenderingLayer() {
		return RenderingLayer;
	}
	

	public void setRenderingLayer(Integer level) {
		this.RenderingLayer = level;		
	}
}
