import javax.swing.text.html.HTMLDocument.BlockElement;

import javafx.scene.Node;

public class JavaFXRenderer extends AGraphicRenderer {
	private JavaFXThread _guiThread;

	public void Init() {
		JavaFXThread thread = new JavaFXThread(this);
		
		this._guiThread = thread;

		thread.start();
	}

	public void clearNode() {

	}

	public Node renderNode(AGraphic g) {
		throw new NullPointerException("JavaFXRenderer can't render a Node class.");
	}

	@Override
	public boolean beforeRender() {
		JavaFXWindow window = this._guiThread.getGuiWindow();
		
		if(window == null || !window.isRendered()) {
			return false;
		}

		window.clearNodes();

		return true;
	}

	@Override
	public void render(AGraphic g) {
		JavaFXWindow window = this._guiThread.getGuiWindow();

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
		JavaFXWindow window = this._guiThread.getGuiWindow();

		window.notifyChanged();

		return true;
	}
}
