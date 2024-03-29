package DAF.Input;
import DAF.Event.AInputEvent;
import DAF.Event.IInputListener;
import DAF.Event.KeyState;
import DAF.Event.MouseInputEvent;
import DAF.Math.Vector2;
import DAF.Renderer.RenderManager;
import DAF.Renderer.JavaFX.JavaFXRenderer;
import DAF.Renderer.JavaFX.JavaFXWindow;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class MouseJavaFXHandler extends AInputHandler {
    @Override
    public Class<? extends AInputEvent> getInputEventType() {
        return MouseInputEvent.class;
    }
    
    @Override
    public void init() {
        JavaFXRenderer renderer = RenderManager.getInstance().getRenderer(JavaFXRenderer.class);
        if(renderer == null) {
            throw new NullPointerException(this.getClass().getName()+" benötigt einen JavaFXRenderer.");
        }
        
        JavaFXWindow window = null;
        Scene scene = null;

        while(window == null || scene == null) {
            window = renderer.getWindow();
            if(window != null) {
                scene = window.getScene();
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MouseInputEvent event = new MouseInputEvent(
                    KeyState.Down,
                    new Vector2(mouseEvent.getX(), mouseEvent.getY()),
                    mouseEvent.getButton().ordinal(),
                    0
                );

                MouseJavaFXHandler.this.callSubscribers(event);
            }
        });

        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MouseInputEvent event = new MouseInputEvent(
                    KeyState.Up,
                    new Vector2(mouseEvent.getX(), mouseEvent.getY()),
                    mouseEvent.getButton().ordinal(),
                    0
                );

                MouseJavaFXHandler.this.callSubscribers(event);
            }
        });
    }
}
