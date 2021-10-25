import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class GameObject {

    private int id;
    private String name;

    private ArrayList<IComponent> components;

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

    public boolean addComponent(IComponent iComponent) {
        return this.components.add(iComponent);
    }

    public boolean removeComponent(IComponent iComponent) {
        return this.components.remove(iComponent);
    }

    public ArrayList<IComponent> getComponents() {
        return this.components;
    }

    public IComponent getComponent(EComponentType eComponentType) {
        Iterator<IComponent> it = components.iterator();
        while (it.hasNext()) {
            if (it.next().equals(eComponentType))
                return it.next();
        }

        for (IComponent ic : components) {
            System.out.println(ic.getClass());
        }

        return null;
    }

    public ArrayList<EComponentType> containsDice() {
        ArrayList<EComponentType> types = new ArrayList<>();
        return types;
    }

}
