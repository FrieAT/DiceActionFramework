public class RenderManager extends AbstractManager {
	private AGraphicRenderer renderer;

    protected static RenderManager _instance;
    public static RenderManager getInstance() {
        if (_instance == null)
            _instance = new RenderManager();
        return _instance;
    }
    
    // Villeicht sollte das Framework weniger "generell" sein
    public RenderManager() { // JavaFXRenderer
        super();
        
        this.renderer = null;
    }

    public void setRenderer(AGraphicRenderer renderer) {
        this.renderer = renderer;
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
        this.renderer.Init();
    }
    
    @Override
    public void update() {		
    	for (GameObject gameObject: gameObjects) {
    		AGraphic graphic = (AGraphic)gameObject.getComponent(EComponentType.AGraphic);

            if(graphic == null) {
                //TODO: Exception if not an AGraphic component.
                continue;
            }
            
            this.renderer.render(graphic);
    	}
    }

    
}
