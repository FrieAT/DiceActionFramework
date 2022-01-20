package DAF.Controller.Event;

import DAF.Controller.Components.IController;
import DAF.Event.AInputEvent;

public class ControllerConnectionEvent extends AInputEvent {

    private ConnectionState _state;

    private IController _controller;

    public ControllerConnectionEvent(ConnectionState state, IController controller) {
        this._state = state;
        this._controller = controller;
    }

    public ConnectionState getState() { return this._state; }

    public IController getController() { return this._controller; }
}
