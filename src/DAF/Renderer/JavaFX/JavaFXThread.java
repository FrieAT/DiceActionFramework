package DAF.Renderer.JavaFX;

import DAF.Renderer.AGraphicRenderer;

public class JavaFXThread extends Thread {
	private AGraphicRenderer _renderer;
	
	public JavaFXThread(AGraphicRenderer renderer) {
		this._renderer = renderer;
	}
	
	public void run(){
		JavaFXWindow.launch(JavaFXWindow.class);
	}

	public synchronized AGraphicRenderer getRenderer() { return this._renderer; }

	public synchronized JavaFXWindow getGuiWindow() { return JavaFXWindow.getInstance(); }
}