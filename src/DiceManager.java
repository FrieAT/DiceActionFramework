
import java.util.ArrayList;
import java.util.HashMap;

public class DiceManager extends AbstractManager {

    protected static DiceManager _instance;

    public static DiceManager getInstance() {
        if (_instance == null)
            _instance = new DiceManager();
        return _instance;
    }

    protected DiceManager () {
        super();
    }

    @Override
    public boolean add(GameObject gameObject) {
        throw new NullPointerException("Please add an ADice object and not a gameObject itself.");
    }

    public boolean add (ADice dice) {
        return super.add(dice.getGameObject());
    }

    @Override
    public boolean remove(GameObject gameObject) {
        throw new NullPointerException("Please remove an ADice object and not a gameObject itself.");
    }

    public boolean remove(ADice dice) {
        return super.remove(dice.getGameObject());
    }

    @Override
    public void update() {
        for (GameObject gameObject : gameObjects) {
            if (!gameObject.isEnabled())
                continue;
            gameObject.update();
        }
    }
}
