public abstract class AGraphic extends AbstractComponent {

	Integer RenderingLayer;
	
	@Override
	public void start() {
		this.type = EComponentType.AGraphic;

		RenderManager.getInstance().add(this);
	}

	public Integer getRenderingLayer() {
		return RenderingLayer;
	}
	

	public void setRenderingLayer(Integer level) {
		this.RenderingLayer = level;		
	}
}
