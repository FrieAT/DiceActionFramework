import javafx.event.Event;
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
                
                for(IInputListener listener : _subscribers) {
                    if(!listener.getGameObject().isEnabled()) {
                        continue;
                    }

                    listener.onInput(event);
                }
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
                
                for(IInputListener listener : _subscribers) {
                    if(!listener.getGameObject().isEnabled()) {
                        continue;
                    }

                    listener.onInput(event);
                }
            }
        });
    }
}
