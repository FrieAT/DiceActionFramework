
public interface IComponent {
    public EComponentType getType();

    public GameObject getGameObject();

    public void setGameObject(GameObject owner);
}
