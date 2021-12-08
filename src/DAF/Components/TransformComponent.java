package DAF.Components;
import DAF.GameObject;
import DAF.Math.Vector2;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class TransformComponent extends AbstractComponent {

    @JsonElement(key = "position")
    private Vector2 _position;
    @JsonElement(key = "scale")
    private Vector2 _scale;
    @JsonElement(key = "zRotation")
    private double _zRotation;

    public TransformComponent() {
        super();

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
        
        TransformComponent parentTransform = parent.getComponent(TransformComponent.class);
        if(parentTransform == null) {
            return null;
        }

        return parentTransform;
    }

    @JsonElement(key = "globalPosition")
    public Vector2 getGlobalPosition() {
        TransformComponent parentTransform = this.getParentTransform();
        
        if(parentTransform == null) {
            return this.getPosition();
        }
        
        Vector2 globalPosition = Vector2.Add(parentTransform.getGlobalPosition(), this.getPosition());
        return globalPosition; 
    }

    @JsonElement(key = "globalRotation")
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
