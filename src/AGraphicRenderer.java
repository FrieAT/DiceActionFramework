import java.util.ArrayList;

public abstract class AGraphicRenderer {
	
	public void Init() {
		throw new NullPointerException("Not implemented.");
	}

	public void Update() {
		throw new NullPointerException("Not implemented.");
	}

	protected ArrayList<AGraphicRenderer> _graphicRenderer;

	public AGraphicRenderer() {
		this._graphicRenderer = new ArrayList<>();
	}
 
	public void add(AGraphicRenderer g) {
		this._graphicRenderer.add(g);
	}
	
	public void remove(AGraphicRenderer g) {
		this._graphicRenderer.remove(g);	
	}

	public void render(AGraphic g) {
		throw new NullPointerException("a");		
	}
	
	public boolean beforeRender() {
		return true;
	}

	public boolean afterRender() {
		return true;
	}
}
