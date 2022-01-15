package DAF.Input;

import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Renderer.JavaFX.JavaFXRenderer;
import DAF.Renderer.RenderManager;

public class ButtonJavaFXHandler extends AInputHandler {
    @Override
    public Class<ButtonInputEvent> getInputEventType() {
        return ButtonInputEvent.class;
    }

    @Override
    public void init() {
        JavaFXRenderer renderer = RenderManager.getInstance().getRenderer(JavaFXRenderer.class);
        if (renderer == null) {
            throw new NullPointerException(this.getClass().getName() + " benötigt einen JavaFXRenderer.");
        }
    }

    public void callSubscribers(ButtonInputEvent event)
    {
        for(IInputListener listener : _subscribers) {
            if(!listener.getGameObject().isEnabled()) {
                continue;
            }

            listener.onInput(event);
        }
    }
}
