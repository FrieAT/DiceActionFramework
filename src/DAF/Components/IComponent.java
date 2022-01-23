package DAF.Components;

import DAF.GameObject;

public interface IComponent {
    public GameObject getGameObject();

    public void setGameObject(GameObject owner);
}
