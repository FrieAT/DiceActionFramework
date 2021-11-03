import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class GameObject {

    private static LinkedList<GameObject> _gameObjects;

    public static void startAll() {
        if(_gameObjects != null) {
            for(GameObject g : _gameObjects) {
                g.start();
            }
        }
    }

    public static void updateAll() {
        if(_gameObjects != null) {
            for(GameObject g : _gameObjects) {
                g.update();
            }
        }
    }

    private int id;
    private String name;

    private ArrayList<AbstractComponent> components;

    public GameObject() {
        if(_gameObjects == null) {
            _gameObjects = new LinkedList<>();
        }
        _gameObjects.add(this);
    }

    public GameObject (int id, String name) {
        this();

        this.id = id;
        this.name = name;
        this.components = new ArrayList<>();
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean addComponent(AbstractComponent iComponent) {
        iComponent.setGameObject(this);
        return this.components.add(iComponent);
    }

    public boolean removeComponent(AbstractComponent iComponent) {
        iComponent.setGameObject(null);
        return this.components.remove(iComponent);
    }

    public ArrayList<AbstractComponent> getComponents() {
        return this.components;
    }

    public AbstractComponent getComponent(EComponentType eComponentType) {
        for(AbstractComponent c : components) {
            if(c.getType() == eComponentType) {
                return c;
            }
        }

        return null;
    }

    public ArrayList<EComponentType> containsDice() {
        ArrayList<EComponentType> types = new ArrayList<>();
        return types;
    }

    public void start() {
        for(AbstractComponent c : components) {
            c.start();
        }
    }

    public void update() {
        for(AbstractComponent c : components) {
            c.update();
        }
    }
}
