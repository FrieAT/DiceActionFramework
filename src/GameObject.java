<<<<<<< HEAD

import java.lang.reflect.Array;
=======
>>>>>>> ad6e96f0ef091867c5818385c8f4bb234e5d5b36
import java.util.ArrayList;
import java.util.Iterator;

public class GameObject {

    private int id;
    private String name;

    private ArrayList<AbstractComponent> components;

    public GameObject() {
        super();
    }

    public GameObject (int id, String name) {
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
        Iterator<AbstractComponent> it = components.iterator();
        while (it.hasNext()) {
            if (it.next().equals(eComponentType))
                return it.next();
        }

        for (AbstractComponent ic : components) {
            System.out.println(ic.getClass());
        }

        return null;
    }

    public ArrayList<EComponentType> containsDice() {
        ArrayList<EComponentType> types = new ArrayList<>();
        return types;
    }

}
