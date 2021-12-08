package DAF.Controller;

import DAF.AbstractManager;
import DAF.GameObject;
import DAF.Controller.Components.AbstractController;
import DAF.Controller.Components.IController;

public class ControllerManager extends AbstractManager {
    protected static ControllerManager _instance;
    public static ControllerManager getInstance() {
        if (_instance == null)
            _instance = new ControllerManager();
        return _instance;
    }

    private int _localPlayerCounter = 0;

    private int _currentPlayer;

    private ControllerManager() {
        super();
    }

    public int GetPlayerCount() { return _localPlayerCounter; }

    public int GetNextPlayer() { return ++this._localPlayerCounter; }

    public boolean IsControllerAtCycle(int playerIndex) {
        if(this._currentPlayer == 0) {
            return false;
        }

        return playerIndex == this._currentPlayer;
    }

    public int GetControllerAtCycle() { return this._currentPlayer; }

    public IController GetController(int controllerIndex) {
        for(GameObject gameObject : this.gameObjects) {
            AbstractController controller = gameObject.getComponent(AbstractController.class);
            if(controller.getPlayerNo() == controllerIndex) {
                return (IController)controller;
            }
        }
        return null;
    }

    @Override
    public void init() {

    }

    /**
     * Loop after every update cycle over a player id
     * in order to perform custom controlled views.
     * See dependency @ControllerView
     */
    @Override
    public void update() {
        if(++this._currentPlayer > this._localPlayerCounter) {
            this._currentPlayer = 0;
        }
    }

    @Override
    public boolean add(GameObject gameObject) {
        throw new NullPointerException("Please add an AbstractController object and not a gameObject itself.");
    }

    public boolean add(AbstractController controller) {
        return super.add(controller.getGameObject());
    }

    @Override
    public boolean remove(GameObject gameObject) {
        throw new NullPointerException("Please add an AbstractController object and not a gameObject itself.");
    }

    public boolean remove(AbstractController controller) {
        return super.add(controller.getGameObject());
    }
}
