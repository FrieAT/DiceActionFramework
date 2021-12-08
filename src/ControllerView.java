import Event.EventDispatcherIterator.NextEvent;

public class ControllerView extends AbstractComponent implements NextEvent<GameObject>
{
    private IController _forController = null;

    private int _forControllerIndex = -1;

    private boolean _previousEnabledState;

    private boolean _cycled = false;

    private boolean _isStarted = false;

    public void setController(IController controller) {
        this._forControllerIndex = -1;
        this._forController = controller;
        this.registerController();
    }

    public void setController(int controllerIndex) {
        this._forControllerIndex = controllerIndex;
        this._forController = null;
        this.registerController();
    }

    public IController getController() {
        return this._forController;
    }

    @Override
    public void start() {
        this._isStarted = true;

        this.registerController();
        
        GameObject.addIteratorDelegate(this);
    }

    @Override
    public void onBeforeNext(GameObject obj) {
        if(this._cycled || this.getGameObject() != obj) {
            return;
        }

        this._cycled = true;

        if(!ControllerManager.getInstance().IsControllerAtCycle(this._forController.getPlayerNo())) {
            this._previousEnabledState = this.getGameObject().isEnabled();
            this.getGameObject().setEnabled(false);
        }
    }

    @Override
    public void onAfterNext(GameObject obj) {
        if(!this._cycled || this.getGameObject() != obj) {
            return;
        }

        this._cycled = false;

        if(!ControllerManager.getInstance().IsControllerAtCycle(this._forController.getPlayerNo())) {
            this.getGameObject().setEnabled(this._previousEnabledState);
        }
    }

    private void registerController() {
        if(!this._isStarted) {
            return;
        }

        if(this._forController == null) {
            this._forController = ControllerManager.getInstance().GetController(this._forControllerIndex);
        }
    }
}