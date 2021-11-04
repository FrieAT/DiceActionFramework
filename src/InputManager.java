import java.lang.*;
import java.util.ArrayList;

public class InputManager extends AbstractManager
{
    private ArrayList<AInputDefinition> _inputs;

    protected static InputManager _instance;
    public static InputManager getInstance() {
        if (_instance == null)
            _instance = new InputManager();
        return _instance;
    }

    protected InputManager() {
        super();
    }

    public void setInputForPlayer(int playerNo, AInputDefinition input) {

    }

    public void OnFrameUpdate() {
        
    }

    @Override
    public boolean add(GameObject gameObject) {
        throw new NullPointerException("Please add an IController object and not a gameObject itself.");
    }

    public boolean add(IController controller) {
        return super.add(controller.getGameObject());
    }

    @Override
    public boolean remove(GameObject gameObject) {
        throw new NullPointerException("Please add an IController object and not a gameObject itself.");
    }

    public boolean remove(IController controller) {
        return super.add(controller.getGameObject());
    }
}