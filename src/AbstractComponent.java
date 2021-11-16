
@Serializable
public abstract class AbstractComponent implements IComponent {
    @JsonElement
    protected GameObject owner = null;

    /**
     * Instantiate an component of type AbstractComponent.
     * Warning: Components may only be allowed to have an empty public constructor,
     * due to the current restrictions in GameObject.addComponent(Class<AbstractComponent>).
     */
    public AbstractComponent() { }

    /**
     * Optional callback which gets called once at engine initialization.
     * Default behavior is empty, because it is optional to implement it.
     * FIXME: Call it also on runtime initialization.
     */
    public void start() { }

    /**
     * Optional callback which gets called after every engine-tick.
     * Default behavior is empty, because it is optional to implement it.
     */
    public void update() { }

    public GameObject getGameObject() { return this.owner; }

    public TransformComponent getTransform() {
        if(this.owner == null) {
            return null;
        }
        return this.owner.getTransform();
    }

    public void setGameObject(GameObject owner) { this.owner = owner; }
}
