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
        JavaFXRenderer renderer = (JavaFXRenderer)RenderManager.getInstance().getRenderer();
        if(renderer == null) {
            throw new NullPointerException(this.getClass().getName()+" benötigt einen JavaFXRenderer.");
        }
        
        JavaFXWindow window = null;

        while(window == null) {
            window = renderer.getWindow();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        Scene scene = window.getScene();

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MouseInputEvent event = new MouseInputEvent(
                    KeyState.Down,
                    new Vector2((int)mouseEvent.getX(), (int)mouseEvent.getY()),
                    mouseEvent.getButton().ordinal()
                );
                
                for(IInputListener listener : _subscribers) {
                    listener.onInput(event);
                }
            }
        });

        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse click detected! " + mouseEvent.getSource());
                MouseInputEvent event = new MouseInputEvent(
                    KeyState.Up,
                    new Vector2((int)mouseEvent.getX(), (int)mouseEvent.getY()),
                    mouseEvent.getButton().ordinal()
                );
                
                for(IInputListener listener : _subscribers) {
                    listener.onInput(event);
                }
            }
        });
    }
}
