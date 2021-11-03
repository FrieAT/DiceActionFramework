
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

        this.gameObjects = new ArrayList<>();
    }

    public HashMap<ADice, ADice> compareDices(ArrayList<ADice> dices) {
        return null;
    }
}
