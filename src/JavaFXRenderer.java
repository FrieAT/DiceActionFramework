
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFXRenderer extends AGraphicRenderer {
	private JavaFXThread _guiThread;

	public void Init() {
		JavaFXThread thread = new JavaFXThread(this);
		
		this._guiThread = thread;
		
		thread.start();
	}

	public Node renderNode(AGraphic g) {
		throw new NullPointerException("JavaFXRenderer can't render a Node class.");
	}

	@Override
	public void render(AGraphic g) {
		JavaFXWindow window = this._guiThread.getGuiWindow();
		
		if(window == null || !window.isRendered()) {
			return;
		}

		window.clearNodes();
		
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
		
		window.notifyChanged(true);
	}	
}
