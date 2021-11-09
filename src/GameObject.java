import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.scene.transform.Transform;

public class GameObject {

    private static LinkedList<GameObject> _gameObjects;

    private static int _gameObjectCounter = 0;

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

    private GameObject parent;

    private TransformComponent transform;

    private ArrayList<AbstractComponent> components;

    public GameObject() {
        this.parent = null;
        this.transform = null;

        if(_gameObjects == null) {
            _gameObjects = new LinkedList<>();
        }
        _gameObjects.add(this);
    }

    public GameObject (String name) {
        this();

        this.id = _gameObjectCounter++;
        this.name = name;
        this.components = new ArrayList<>();
    }

    public GameObject(String name, TransformComponent transform) {
        this(name);

        this.transform = transform;
    }

    public GameObject(String name, GameObject parent) {
        this(name);

        this.parent = parent;
    }

    public GameObject(String name, GameObject parent, TransformComponent transform) {
        this(name);

        this.transform = transform;
        this.parent = parent;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public GameObject getParent() {
        return this.parent;
    }

    public TransformComponent getTransform() {
        return this.transform;
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
        TransformComponent transform = (TransformComponent)this.getComponent(EComponentType.Transform);
        if(transform == null) {
            transform = new TransformComponent();
            this.addComponent(transform);
        }
        this.transform = transform;

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
