package DAF.Input;

import java.util.concurrent.ConcurrentLinkedQueue;

import DAF.GameObject;
import DAF.Controller.ControllerManager;
import DAF.Event.AInputEvent;
import DAF.Event.IInputListener;

public class AServerHandler extends AInputHandler {
    private ConcurrentLinkedQueue<AInputEvent> _delayedEvents;
    
    private int _memorizedPlayerIndex = -1;

    private AInputEvent _currentDelayedEvent = null;

    public AServerHandler() {
        super();

        this._delayedEvents = new ConcurrentLinkedQueue<>();
    }

    public void addDelayedEvent(AInputEvent newEvent) {
        this._delayedEvents.add(newEvent);
    }

    @Override
    public void update() {
        //Discard current delayed event, as we have not found a valid update cycle to emit on.
        if(this._currentDelayedEvent != null && this._memorizedPlayerIndex == ControllerManager.getInstance().GetControllerAtCycle()) {
            this._currentDelayedEvent = null;
        }
        
        //Fetch next delayed event if exists.
        if(this._currentDelayedEvent == null) {
            this._currentDelayedEvent = this._delayedEvents.poll();
            if(this._currentDelayedEvent != null) {
                this._memorizedPlayerIndex = ControllerManager.getInstance().GetControllerAtCycle();
            } else {
                this._memorizedPlayerIndex = -1;
            }
        }

        AInputEvent event = this._currentDelayedEvent;
        if(event != null) {
            //Loop through subscribers O(n)
            for(IInputListener listener : _subscribers) {
                //Before isEnabled-check convert gameObject through loop of events. O(1)
                for(GameObject gameObject : GameObject.getGameObject(listener.getGameObject())) {
                    if(!gameObject.isEnabled()) {
                        continue;
                    }
    
                    if(!ControllerManager.getInstance().IsControllerAtCycle(event.getControllerIndex())) {
                        continue;
                    }
    
                    listener.onInput(event);
                }
            }
        }
    }
}
