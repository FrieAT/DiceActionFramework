
import java.util.ArrayList;

public class JavaFXRenderer extends AGraphicRenderer {
	public ArrayList<AGraphicRenderer> graphicRenderer;


 
	public void add(AGraphicRenderer g) throws Exception {
		graphicRenderer.add(g);
	}
	public void remove(AGraphicRenderer g) throws Exception {
		graphicRenderer.remove(g);	
	}
	public void render(AGraphic g) throws Exception {
		for (AGraphicRenderer renderer: graphicRenderer) {
			renderer.render(g);
		}

	}	
}
