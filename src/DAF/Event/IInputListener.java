package DAF.Event;

import DAF.Components.IComponent;

public interface IInputListener extends IComponent
{
    void onInput(AInputEvent event);
}