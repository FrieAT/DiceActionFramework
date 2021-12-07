import Socket.HttpSocket.Resource.JsonBufferResource;
import Socket.IResource;
import Socket.IServerSocket;
import Socket.ISocketListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

public class MouseServerHandler extends AInputHandler implements ISocketListener {
    @Override
    public Class<? extends AInputEvent> getInputEventType() {
        return MouseInputEvent.class;
    }

    @Override
    public void init() {
        ServerRenderer serverRenderer = RenderManager.getInstance().getRenderer(ServerRenderer.class);
        IServerSocket socket = serverRenderer.getSocket();

        socket.addListener("/api/event.json", this);

    }

    public void onSocketReceive(IResource resource) {
        assert (resource.getClass() == JsonBufferResource.class);
        JsonBufferResource json = (JsonBufferResource) resource;

        String jsonString = new String(json.getBufferedData());
        JSONObject jsonData = new JSONObject(jsonString);

        // {"keycode":"1","x":"835","y":"662"}
        int keycode = jsonData.getInt("keycode");
        double x = jsonData.getDouble("x");
        double y = jsonData.getDouble("y");

        MouseInputEvent event = new MouseInputEvent(
                KeyState.Up,
                new Vector2(x, y),
                keycode
        );

        for(IInputListener listener : _subscribers) {
            if(!listener.getGameObject().isEnabled()) {
                continue;
            }

            listener.onInput(event);
        }
    }

    @Override
    public void onSocketTransmission(IResource resource) {
        
    }

    @Override
    public void onSocketPrepareTransmission(IResource resource) {
        
    }
}
