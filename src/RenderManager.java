import java.util.ArrayList;

public class RenderManager extends AbstractManager {
	
	private ArrayList<AGraphicRenderer> renderer;

    private long _lastRenderedTime;

    private long _currentRenderedTime;

    protected static RenderManager _instance;
    public static RenderManager getInstance() {
        if (_instance == null)
            _instance = new RenderManager();
        return _instance;
    }
    
    // Villeicht sollte das Framework weniger "generell" sein
    public RenderManager() { // JavaFXRenderer
        super();
        
    	this.renderer = new ArrayList<>();
        this._lastRenderedTime = 0;
        this._currentRenderedTime = 0;
    }

    public void setRenderer(AGraphicRenderer renderer) {
    	this.renderer.add(renderer);
    }

    public synchronized void setRenderedTime(long now) {
        if(_lastRenderedTime == 0) {
            _lastRenderedTime = now;
        } else {
            _lastRenderedTime = _currentRenderedTime;
        }
        _currentRenderedTime = now;
    }

    public double getDeltaTime() {
        double deltaTime = (double) ((_currentRenderedTime - _lastRenderedTime) / 1e9);
        if(deltaTime < 0) {
            //TODO: Calculate correct deltatime if negative
            //TODO: Or avoid negative deltaTime.
            deltaTime *= -1;
        }
        return deltaTime;
    }

    @Override
    public boolean add(GameObject gameObject) {
        throw new NullPointerException("Please add an AGraphic object and not a gameObject itself.");
    }

    public boolean add(AGraphic controller) {
        return super.add(controller.getGameObject());
    }

    @Override
    public boolean remove(GameObject gameObject) {
        throw new NullPointerException("Please add an AGraphic object and not a gameObject itself.");
    }

    public boolean remove(AGraphic controller) {
        return super.add(controller.getGameObject());
    }

    @Override
    public void init() {

        
    	for (AGraphicRenderer graphicRenderer : renderer) {
        	graphicRenderer.Init();
    	}
    }
    
    @Override
    public void update() {
    	for (AGraphicRenderer graphicRenderer : renderer) {
            if(!graphicRenderer.beforeRender()) {
                return;
            }
            
        	for (GameObject gameObject: gameObjects) {
        	    if (!gameObject.isEnabled())
        	        continue;
        		AGraphic graphic = gameObject.getComponent(AGraphic.class);

                if(graphic == null) {
                    //TODO: Exception if not an AGraphic component.
                    continue;
                }
                
                graphicRenderer.render(graphic);
        	}

        	graphicRenderer.afterRender();
    	}
    }

    public ArrayList<AGraphicRenderer> getRenderer() { //
        return this.renderer;
    }

    public AGraphicRenderer getSpecificJavaFXRenderer() { //To do
    	for (AGraphicRenderer graphicRenderer : renderer) {
    		if (graphicRenderer instanceof JavaFXRenderer) {
    			return graphicRenderer;
    		}
    	}
		return null;
    }  
}
