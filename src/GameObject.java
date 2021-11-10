import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.scene.transform.Transform;

public class GameObject {

    private static LinkedList<GameObject> _gameObjects;

    private static int _gameObjectCounter = 0;

    private boolean enabled = true;

    private boolean initialized = false;

    public static GameObject find(String name) {
        for(GameObject g : _gameObjects) {
            if(g.getName().equals(name)) {
                return g;
            }
        }
        return null;
    }

    public static void startAll() {
        //TODO: Add gameobjects from start() method later.
        if(_gameObjects != null) {
            for(GameObject g : _gameObjects) {
                g.initialize();
            }
        }
    }

    public static void updateAll() {
        //TODO: Add gameobjects from update() method later.
        if(_gameObjects != null) {
            for(GameObject g : _gameObjects) {
                if (!g.initialized)
                    g.initialize();
                if (!g.enabled)
                    continue;
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
        this.components = new ArrayList<>();

        if(_gameObjects == null) {
            _gameObjects = new LinkedList<>();
        }
        _gameObjects.add(this);
    }

    public GameObject (String name) {
        this();

        this.id = _gameObjectCounter++;
        this.name = name;
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

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public TransformComponent getTransform() {
        return this.instantiateTransform();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean val) {
        this.enabled = val;
    }

    public AbstractComponent addComponent(AbstractComponent component) {
        component.setGameObject(this);
        this.components.add(component);
        return component;
    }

    public <T extends AbstractComponent> T addComponent(Class<T> componentClass) {
        T component = null;
        try {
            component = componentClass.getDeclaredConstructor().newInstance();
        }
        catch(Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        component.setGameObject(this);
        this.components.add(component);
        return component;
    }

    public <T extends AbstractComponent> boolean removeComponent(Class<T> componentClass) {
        for(AbstractComponent otherComponent : this.components) {
            if(otherComponent.getClass() == componentClass) {
                otherComponent.setGameObject(null);
                return this.components.remove(otherComponent);
            }
        }
        return false;
    }

    public ArrayList<AbstractComponent> getComponents() {
        return this.components;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractComponent> T getComponent(Class<T> componentClass) {
        for(AbstractComponent otherComponent : components) {
            if(componentClass.isAssignableFrom(otherComponent.getClass())) {
                return (T)otherComponent;
            }
        }
        return null;
    }

    private void initialize() {
        if (this.initialized)
            return;

        this.initialized = true;
        this.start();
    }

    public void start() {  
        this.instantiateTransform();

        for(AbstractComponent c : components) {
            c.start();
        }
    }

    public void update() {
        for(AbstractComponent c : components) {
            c.update();
        }
    }

    private TransformComponent instantiateTransform() {
        if(this.transform != null) {
            return this.transform;
        }

        TransformComponent transform = this.getComponent(TransformComponent.class);
        if(transform == null) {
            transform = this.addComponent(TransformComponent.class);
        }

        this.transform = transform;
        
        return transform;
    }
}
