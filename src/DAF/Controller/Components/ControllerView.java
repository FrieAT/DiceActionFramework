package DAF.Controller.Components;

import DAF.GameObject;
import DAF.Components.AbstractComponent;
import DAF.Controller.ControllerManager;
import DAF.Event.EventDispatcherIterator.NextEvent;

public class ControllerView extends AbstractComponent implements NextEvent<GameObject>
{
    private IController _forController = null;

    private int _forControllerIndex = -1;

    private boolean _previousEnabledState;

    private boolean _cycled = false;

    private boolean _isStarted = false;

    private boolean _hideFromEveryone = false;

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

    public void setHideFromEveryone(boolean state) {
        this._hideFromEveryone = state;
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
        if(this._cycled || this._forController == null && !this._hideFromEveryone) {
            return;
        }

        ControllerView otherView = obj.getComponent(ControllerView.class);
        while(obj != null && otherView == null) {
            obj = obj.getParent();
            if(obj != null) {
                otherView = obj.getComponent(ControllerView.class);
            }
        }

        if(this != otherView) {
            return;
        }

        boolean isEnabled = obj.isEnabled();
        if(!isEnabled) {
            return;
        }

        this._cycled = true;

        if(this._hideFromEveryone || !ControllerManager.getInstance().IsControllerAtCycle(this._forController.getPlayerNo())) {
            this._previousEnabledState = isEnabled;
            obj.setEnabled(false);
        }
    }

    @Override
    public void onAfterNext(GameObject obj) {
        if(!this._cycled || this._forController == null && !this._hideFromEveryone) {
            return;
        }

        ControllerView otherView = obj.getComponent(ControllerView.class);
        while(obj != null && otherView == null) {
            obj = obj.getParent();
            if(obj != null) {
                otherView = obj.getComponent(ControllerView.class);
            }
        }

        if(this != otherView) {
            return;
        }

        this._cycled = false;

        if(this._hideFromEveryone || !ControllerManager.getInstance().IsControllerAtCycle(this._forController.getPlayerNo())) {
            if(!obj.isEnabled()) {
                obj.setEnabled(true);
            }
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