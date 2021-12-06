import Event.EventDispatcherIterator.NextEvent;

public class ControllerView extends AbstractComponent implements NextEvent<GameObject>
{
    private IController _forController = null;

    private int _forControllerIndex = -1;

    private boolean _previousEnabledState;

    public void setController(IController controller) {
        this._forControllerIndex = -1;
        this._forController = controller;
    }

    public void setController(int controllerIndex) {
        this._forControllerIndex = controllerIndex;
        this._forController = null;
    }

    @Override
    public void start() {
        this.registerController();
        
        GameObject.addIteratorDelegate(this);
    }

    @Override
    public void onBeforeNext(GameObject obj) {
        if(this.getGameObject() != obj) {
            return;
        }
        
        _previousEnabledState = this.getGameObject().isEnabled();
        this.getGameObject().setEnabled(false);
    }

    @Override
    public void onAfterNext(GameObject obj) {
        if(this.getGameObject() != obj) {
            return;
        }

        this.getGameObject().setEnabled(this._previousEnabledState);
    }

    private void registerController() {
        if(this._forController == null) {
            this._forController = ControllerManager.getInstance().GetController(this._forControllerIndex);
        }
    }
}