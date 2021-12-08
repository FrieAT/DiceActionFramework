package DAF.Input;

import org.json.JSONObject;

import DAF.Event.AInputEvent;
import DAF.Event.KeyState;
import DAF.Event.MouseInputEvent;
import DAF.Math.Vector2;
import DAF.Renderer.RenderManager;
import DAF.Renderer.Server.ServerRenderer;
import DAF.Socket.IResource;
import DAF.Socket.IServerSocket;
import DAF.Socket.ISocketListener;
import DAF.Socket.HttpSocket.Resource.JsonBufferResource;

public class MouseServerHandler extends AServerHandler implements ISocketListener {
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
        int controller = jsonData.getInt("controller");

        MouseInputEvent event = new MouseInputEvent(
                KeyState.Up,
                new Vector2(x, y),
                keycode,
                controller
        );

        this.addDelayedEvent(event);
    }

    @Override
    public void onSocketTransmission(IResource resource) {
        
    }

    @Override
    public void onSocketPrepareTransmission(IResource resource) {
        
    }
}
