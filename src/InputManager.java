import java.util.ArrayList;

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
        this._inputs.add(handler);
    }

    @Override
    public void init() {
        for(AInputHandler handler : this._inputs) {
            handler.init();
        }
    }

    @Override
    public boolean add(GameObject gameObject) {
        throw new NullPointerException("Please add an IController object and not a gameObject itself.");
    }
    
    public <T extends AInputEvent> boolean add(Class<T> clazz, IInputListener listener) {
        for(AInputHandler handler : this._inputs) {
            if(handler.getInputEventType() == clazz) {
                handler.registerListener(listener);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(GameObject gameObject) {
        throw new NullPointerException("Please add an IController object and not a gameObject itself.");
    }

    public boolean remove(IController controller) {
        throw new NullPointerException("Not implemented function.");
    }
}