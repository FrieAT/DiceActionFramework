package DAF;

import java.util.ArrayList;
import java.util.Iterator;

import DAF.Event.EventDispatcherIterator;

public class AbstractManager {
    protected ArrayList<GameObject> gameObjects;

    protected AbstractManager() {
        this.gameObjects = new ArrayList<>();
    }

    public boolean add(GameObject gameObject) {
        return gameObjects.add(gameObject);
    }

    public boolean remove(GameObject gameObject) {
        return gameObjects.remove(gameObject);
    }

    @SuppressWarnings("unchecked")
    public Iterable<GameObject> getGameObjects() {
        return (Iterable<GameObject>)GameObject.iterator();
    }

    public Iterator<GameObject> iterator() {
        return new EventDispatcherIterator<GameObject>(this.gameObjects.iterator(), GameObject._eventDelegates);
    }

    public void init() {

    }

    public void update() {

    }
}
