import java.util.LinkedList;

public class RenderManager extends AbstractManager {
	private LinkedList<AGraphicRenderer> renderer;

    private long _lastRenderedTime;

    private long _currentRenderedTime;

    protected static RenderManager _instance;
    public static RenderManager getInstance() {
        if (_instance == null)
            _instance = new RenderManager();
        return _instance;
    }
    
    // Vielleicht sollte das Framework weniger "generell" sein
    public RenderManager() { // JavaFXRenderer
        super();
        
        this.renderer = new LinkedList<>();
        this._lastRenderedTime = 0;
        this._currentRenderedTime = 0;
    }

    public void addRenderer(AGraphicRenderer renderer) {
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
        for(AGraphicRenderer renderer : this.renderer) {
            renderer.Init();
        }
    }
    
    @Override
    public void update() {
        for(AGraphicRenderer renderer : this.renderer) {
            if(!renderer.beforeRender()) {
                return;
            }
            
            for (GameObject gameObject: GameObject.getGameObjects()) {
                if (!gameObject.isEnabled())
                    continue;
                AGraphic graphic = gameObject.getComponent(AGraphic.class);
    
                if(graphic == null) {
                    //TODO: Exception if not an AGraphic component.
                    continue;
                }
                
                renderer.render(graphic);
            }
    
            renderer.afterRender();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends AGraphicRenderer> T getRenderer(Class<T> rendererClass) {
        for(AGraphicRenderer otherRenderer : this.renderer) {
            if(rendererClass.isAssignableFrom(otherRenderer.getClass())) {
                return (T)otherRenderer;
            }
        }
        return null;
    }

    
}
