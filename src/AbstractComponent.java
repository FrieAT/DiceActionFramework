

public abstract class AbstractComponent implements IComponent {
    protected GameObject owner;

    protected EComponentType type;

    public AbstractComponent() {
        this.owner = null;
        this.type = EComponentType.Undefined;
    }

    public void start() {
    }

    public void update() {
    }

    public EComponentType getType() { return this.type; }

    public GameObject getGameObject() { return this.owner; }

    public TransformComponent getTransform() {
        if(this.owner == null) {
            return null;
        }
        return this.owner.getTransform();
    }

    public void setGameObject(GameObject owner) { this.owner = owner; }
}
