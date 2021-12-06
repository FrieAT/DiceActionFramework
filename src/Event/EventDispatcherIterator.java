package Event;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Custom iterator witch dispatches events when accessing new elements from an iterator.
 */
public class EventDispatcherIterator<E> implements Iterator<E> {
    public interface AnyEvent<E> { }
    
    public interface NextEvent<E> extends AnyEvent<E>
    {
        void onBeforeNext(E obj);
        
        void onAfterNext(E obj);
    }

    public interface RemoveEvent<E> extends AnyEvent<E>
    {
        void onRemove(E obj);
    }

    private Iterator<E> _it;

    private HashMap<String, LinkedList<AnyEvent<E>>> _delegates;

    private E _current;

    /**
     * Mask iterator for event-broadcasting.
     * @param it
     * @initDelegates delegates to add on construction @Nullable()
     */
    public EventDispatcherIterator(Iterator<E> it, List<AnyEvent<E>> initDelegates) {
        this._current = null;
        this._it = it;
        this._delegates = new HashMap<>();

        if(initDelegates != null) {
            for(AnyEvent<E> eventDelegate : initDelegates) {
                this.addDelegate(eventDelegate);
            }
        }
    }

    public void addDelegate(AnyEvent<E> delegate)
    {
        for(Class genericEvent : delegate.getClass().getInterfaces()) {
            //Check if interface is type on an AnyEvent<E>
            if(genericEvent.getSuperclass() == AnyEvent.class) {
                String eventInterface = genericEvent.getName();
                LinkedList<AnyEvent<E>> delegateList = this._delegates.get(eventInterface);
                
                if(delegateList == null) {
                    delegateList = new LinkedList<>();
                }
                
                delegateList.push(delegate);

                this._delegates.put(genericEvent.getName(), delegateList);
            }
        }
    }

    public void removeDelegate(AnyEvent<E> delegate)
    {
        throw new NullPointerException("Not Implemented! Buy a beer for the responsible programmer (#ref STUB#removeDelegate).");
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E next() {
        E obj = this._it.next();
        LinkedList<AnyEvent<E>> eventTypes = this._delegates.get(NextEvent.class.getName());
        if(eventTypes != null) {
            for(AnyEvent<E> eventType : eventTypes) {
                ((NextEvent<E>)eventType).onBeforeNext(obj);

                if(this._current != null) {
                    ((NextEvent<E>)eventType).onAfterNext(this._current);
                }
            }
        }
        return obj;
    }

    @Override
    public void remove() {
        if(this._current == null) {
            return;
        }
        
        LinkedList<AnyEvent<E>> eventTypes = this._delegates.get(RemoveEvent.class.getName());
        if(eventTypes != null) {
            for(AnyEvent<E> eventType : eventTypes) {
                ((RemoveEvent<E>)eventType).onRemove(this._current);
            }
        }
        this._it.remove();
    }
}
