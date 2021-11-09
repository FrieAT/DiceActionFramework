
import java.util.ArrayList;
import java.util.HashMap;

public class DiceManager extends AbstractManager {

    private ArrayList<ADice> _dices;

    protected static DiceManager _instance;

    public static DiceManager getInstance() {
        if (_instance == null)
            _instance = new DiceManager();
        return _instance;
    }

    protected DiceManager () {
        super();
        this._dices = new ArrayList<>();
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

    public void update() {

    }
}
