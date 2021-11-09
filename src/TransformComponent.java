public class TransformComponent extends AbstractComponent {
    
    private Vector2 _position;

    private Vector2 _scale;

    private double _zRotation;

    public TransformComponent() {
        super();

        this.type = EComponentType.Transform;
        
        this._position = Vector2.Zero;
        this._scale = Vector2.One;
        this._zRotation = 0.0;
    }

    public void setPosition(Vector2 position) {
        this._position = position;
    }

    public void setRotation(double rotation) {
        this._zRotation = rotation;
    }

    public void setScale(Vector2 scale) {
        this._scale = scale;
    }

    private TransformComponent getParentTransform() {
        GameObject parent = this.getGameObject().getParent();
        if(parent == null) {
            return null;
        }
        
        TransformComponent parentTransform = (TransformComponent)parent.getComponent(EComponentType.Transform);
        if(parentTransform == null) {
            return null;
        }

        return parentTransform;
    }

    public Vector2 getGlobalPosition() {
        TransformComponent parentTransform = this.getParentTransform();
        
        if(parentTransform == null) {
            return this.getPosition();
        }
        
        Vector2 globalPosition = Vector2.Add(parentTransform.getGlobalPosition(), this.getPosition());
        return globalPosition; 
    }

    public double getGlobalRotation() {
        TransformComponent parentTransform = this.getParentTransform();
        
        if(parentTransform == null) {
            return this.getRotation();
        }
        
        double globalRotation = parentTransform.getGlobalRotation() + parentTransform.getRotation();
        return globalRotation; 
    }

    public Vector2 getPosition() { return this._position; }

    public double getRotation() { return this._zRotation; }

    public Vector2 getScale() { return this._scale; }
}
