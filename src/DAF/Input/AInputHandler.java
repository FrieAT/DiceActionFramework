package DAF.Input;

import java.util.LinkedList;

import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;

public abstract class AInputHandler {
    protected LinkedList<IInputListener> _subscribers;

    public AInputHandler() {
        this._subscribers = new LinkedList<>();
    }

    public Class<? extends AInputEvent> getInputEventType() {
        throw new NullPointerException("Not implemented input event.");
    }

    public void init() {

    }

    public void update() {

    }

    public void registerListener(IInputListener listener) {
        this._subscribers.add(listener);
    }

    public void unregisterListener(IInputListener listener) {
        this._subscribers.remove(listener);
    }

    public void callSubscribers(AInputEvent event)
    {
        for(IInputListener listener : _subscribers) {
            if(!listener.getGameObject().isEnabled()) {
                continue;
            }

            listener.onInput(event);
        }
    }
}
