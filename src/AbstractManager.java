import java.util.ArrayList;

public class AbstractManager {

    private static AbstractManager _instance = null;

    ArrayList<GameObject> gameObjects;

    public static AbstractManager getInstance() {
        if (AbstractManager._instance == null)
            AbstractManager._instance = new AbstractManager();
        return AbstractManager._instance;
    }

    public boolean add(GameObject gameObject) {
        return gameObjects.add(gameObject);
    }

    public boolean remove(GameObject gameObject) {
        return gameObjects.remove(gameObject);
    }
}
