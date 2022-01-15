package DAF.Input;
import java.util.ArrayList;

import DAF.AbstractManager;
import DAF.GameObject;
import DAF.Controller.Components.IController;
import DAF.Event.AInputEvent;
import DAF.Event.IInputListener;

public class InputManager extends AbstractManager
{
    private ArrayList<AInputHandler> _inputs;

    protected static InputManager _instance;
    public static InputManager getInstance() {
        if (_instance == null)
            _instance = new InputManager();
        return _instance;
    }

    protected InputManager() {
        super();

        this._inputs = new ArrayList<>();
    }

    public void addInputHandler(AInputHandler handler) {
        if(this.getInputHandler(handler.getClass()) != null) {
            throw new NullPointerException("Duplicate entry for AInputHandler : "+handler.getClass().getName());
        }

        this._inputs.add(handler);
    }

    public <T extends AInputHandler> T getInputHandler(Class<? extends T> clazz) {
        for(AInputHandler handler : this._inputs) {
            if(handler.getClass().isAssignableFrom(clazz)) {
                return (T)handler;
            }
        }
        return null;
    }

    @Override
    public void init() {
        for(AInputHandler handler : this._inputs) {
            handler.init();
        }
    }

    @Override
    public void update() {
        for(AInputHandler handler : this._inputs) {
            handler.update();
        }
    }

    @Override
    public boolean add(GameObject gameObject) {
        throw new NullPointerException("Please add an IController object and not a gameObject itself.");
    }
    
    public boolean add(Class<? extends AInputEvent> clazz, IInputListener listener) {
        boolean registered = false;
        for(AInputHandler handler : this._inputs) {
            if(handler.getInputEventType() == clazz) {
                handler.registerListener(listener);
                registered = true;
            }
        }
        return registered;
    }

    @Override
    public boolean remove(GameObject gameObject) {
        throw new NullPointerException("Please add an IController object and not a gameObject itself.");
    }

    public boolean remove(IController controller) {
        throw new NullPointerException("Not implemented function.");
    }
}