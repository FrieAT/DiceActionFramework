
import java.util.ArrayList;

public class AbstractManager {
    ArrayList<GameObject> gameObjects;

    protected AbstractManager() {
        this.gameObjects = new ArrayList<>();
    }

    public boolean add(GameObject gameObject) {
        return gameObjects.add(gameObject);
    }

    public boolean remove(GameObject gameObject) {
        return gameObjects.remove(gameObject);
    }

    public void init() {

    }

    public void update() {

    }
}
