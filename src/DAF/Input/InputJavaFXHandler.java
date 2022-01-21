package DAF.Input;

import DAF.Event.ButtonInputEvent;
import DAF.Event.TextInputEvent;
import DAF.Renderer.JavaFX.JavaFXRenderer;
import DAF.Renderer.RenderManager;
import org.w3c.dom.Text;

public class InputJavaFXHandler extends AInputHandler {
    @Override
    public Class<TextInputEvent> getInputEventType() {
        return TextInputEvent.class;
    }

    @Override
    public void init() {
        JavaFXRenderer renderer = RenderManager.getInstance().getRenderer(JavaFXRenderer.class);
        if (renderer == null) {
            throw new NullPointerException(this.getClass().getName() + " benötigt einen JavaFXRenderer.");
        }
    }
}
