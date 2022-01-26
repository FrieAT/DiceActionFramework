package DAF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import DAF.Components.AbstractComponent;
import DAF.Components.TransformComponent;
import DAF.Event.EventDispatcherIterator;
import DAF.Event.EventDispatcherIterator.AnyEvent;
import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class GameObject {

    private static LinkedList<GameObject> _gameObjects = new LinkedList<>();

    private static LinkedList<GameObject> _newGameObjects = new LinkedList<>();

    public static LinkedList<AnyEvent<GameObject>> _eventDelegates = null;

    private static int _gameObjectCounter = 0;

    private boolean enabled = true;

    public static GameObject find(String name) {
        for(GameObject g : _gameObjects) {
            if(g.getName().equals(name)) {
                return g;
            }
        }
        return null;
    }

    public static GameObject find(int id) {
        for(GameObject g : _gameObjects) {
            if(g.getId() == id) {
                return g;
            }
        }
        return null;
    }

    public static ArrayList<GameObject> findAll(String prefix) {
        ArrayList<GameObject> found = new ArrayList<>();
        for (GameObject g : _gameObjects) {
            if (g.getName().startsWith(prefix))
                found.add(g);
        }

        return found;
    }

    public static void addIteratorDelegate(AnyEvent<GameObject> delegate)
    {
        if(_eventDelegates == null) {
            _eventDelegates = new LinkedList<>();
        }
        _eventDelegates.add(delegate);
    }

    @SuppressWarnings("unchecked")
    public static Iterable<GameObject> getGameObjects() {
        return (Iterable<GameObject>)GameObject.iterator();
    }

    public static Iterable<GameObject> getGameObject(GameObject obj) {
        LinkedList<GameObject> list = new LinkedList<>();
        list.add(obj);
        return (Iterable<GameObject>)new EventDispatcherIterator<GameObject>(list.iterator(), _eventDelegates);
    }

    public static Iterator<GameObject> iterator() {
        return new EventDispatcherIterator<GameObject>(_gameObjects.iterator(), _eventDelegates);
    }

    public static void startAll() {
        if(_newGameObjects.size() > 0) {
            _gameObjects.addAll(_newGameObjects);
            _newGameObjects.clear();
        }
        for(GameObject g : _gameObjects) {
            g.start();
        }
    }

    public static void updateAll() {
        if(_newGameObjects.size() > 0) {
            _gameObjects.addAll(_newGameObjects);

            LinkedList<GameObject> newGameObjects = new LinkedList<>(_newGameObjects);
            
            _newGameObjects.clear();

            for(GameObject newObject : newGameObjects) {
                newObject.start();
            }
        }
        for(GameObject g : _gameObjects) {
            if (!g.enabled)
                continue;
            g.update();
        }
    }

    @JsonElement
    private int id;
    @JsonElement
    private String name;
    @JsonElement
    private GameObject parent;

    @JsonElement
    private TransformComponent transform;

    private ArrayList<AbstractComponent> components;

    private LinkedList<GameObject> children;

    public GameObject() {
        this.parent = null;
        this.components = new ArrayList<>();
        this.children = new LinkedList<>();

        this.transform = null;

        _newGameObjects.add(this);
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
        parent.children.add(this);
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
        if(this.parent != null) {
            this.parent.children.remove(this);
        }
        this.parent = parent;
        this.parent.children.add(this);
    }

    public TransformComponent getTransform() {
        return this.instantiateTransform();
    }

    public List<GameObject> getChildren() { return this.children; }

    public <T extends AbstractComponent>
    List<T> getComponentsInChildren(Class<T> componentClass) {
        LinkedList<T> returnList = new LinkedList<>();
        for (GameObject child : this.getChildren()) {
            T childComp = child.getComponent(componentClass);
            if(childComp != null) {
                returnList.add(childComp);
            }
        }
        return returnList;
    }

    public <T extends AbstractComponent>
    T getComponentInChildren(Class<T> componentClass) {
        List<T> returnList = this.getComponentsInChildren(componentClass);
        if(returnList.size() == 0) {
            return null;
        }
        return returnList.get(0);
    }

    public <T extends AbstractComponent>
    T getComponentInParent(Class<T> componentClass) {
        GameObject parent = this.getParent();
        while(parent != null) {
            T reqComp = parent.getComponent(componentClass);
            if(reqComp != null) {
                return reqComp;
            }
            parent = parent.parent;
        }
        return null;
    }

    // TODO: Standard checks parent if enabled
    public boolean isEnabled() {
        if(this.parent != null) {
            return this.parent.isEnabled() && this.enabled;
        }

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
