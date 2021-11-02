

import java.util.ArrayList;

public class RenderManager extends AbstractManager {
	AGraphicRenderer renderer = new JavaFXRenderer();  
	// Villeicht sollte das Framework weniger "generell" sein
    public RenderManager () { // JavaFXRenderer
        this.gameObjects = new ArrayList<>(); // Only Graphic Gameobjects // Viel Typcasting.
    }
    public void Render() throws Exception {		
    	for (GameObject gameObject: gameObjects) {
    		this.renderer.render((AGraphic)gameObject.getComponent(EComponentType.AGraphic)); //??
    	}
    }

    
}
