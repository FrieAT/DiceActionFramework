import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

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


}
