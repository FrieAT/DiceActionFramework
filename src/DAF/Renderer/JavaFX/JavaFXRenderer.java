package DAF.Renderer.JavaFX;

import DAF.Controller.ControllerManager;
import DAF.Renderer.AGraphicRenderer;
import DAF.Renderer.Components.AGraphic;
import javafx.scene.Node;

public class JavaFXRenderer extends AGraphicRenderer {
	private JavaFXThread _guiThread;

	public JavaFXRenderer() {
		super();

		this._guiThread = new JavaFXThread(this);;
	}

	public void Init() {
		this._guiThread.start();
	}

	public void clearNode() {
		
	}

	public JavaFXWindow getWindow() { return this._guiThread.getGuiWindow(); }

	public Node renderNode(AGraphic g) {
		throw new NullPointerException("JavaFXRenderer can't render a Node class.");
	}

	@Override
	public boolean beforeRender() {
		JavaFXWindow window = this.getWindow();
		
		if(window == null || !window.isRendered()) {
			return false;
		}

		//FIXME: Prevent strong binding to other manager!!!
		if(ControllerManager.getInstance().GetControllerAtCycle() != 0) {
			return false;
		}

		window.clearNodes();

		return true;
	}

	@Override
	public void render(AGraphic g) {
		JavaFXWindow window = this.getWindow();

		for (AGraphicRenderer renderer : this._graphicRenderer) {
			JavaFXRenderer fxRenderer = (JavaFXRenderer)renderer;
			
			if(fxRenderer == null) {
				//TODO: Throw warning if trying to render wrong class?
				System.out.println("WARNING: "+renderer.getClass().getName()+" != "+this.getClass().getName());
				continue;
			}
			
			Node n = fxRenderer.renderNode(g);

			if(n != null) {
				window.renderNode(n);
			}
		}
	}

	@Override
	public boolean afterRender() {
		JavaFXWindow window = this.getWindow();

		window.notifyChanged();

		return true;
	}
}
