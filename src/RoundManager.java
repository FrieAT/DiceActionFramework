import DAF.AbstractManager;
import DAF.GameObject;

public class RoundManager extends AbstractManager {

    protected static RoundManager _instance;

    private RoundManager() {
        super();
    }

    public static RoundManager getInstance() {
        if (_instance == null)
            _instance = new RoundManager();
        return _instance;
    }


    @Override
    public boolean add(GameObject gameObject) {
        throw new NullPointerException("Please add a Round object and not a GameObject.");
    }

    public boolean add(ARound round) {
        return super.add(round.getGameObject());
    }

    @Override
    public boolean remove(GameObject gameObject) {
        throw new NullPointerException("Please remove a round and not a GameObject itself.");
    }

    public boolean remove(ARound round) {
        return super.remove(round.getGameObject());
    }

    @Override
    public void update() {
        for (GameObject gameObject : gameObjects)
            gameObject.update();

    }

}
