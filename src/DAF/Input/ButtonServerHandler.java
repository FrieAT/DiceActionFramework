package DAF.Input;

import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.KeyState;
import DAF.Event.MouseInputEvent;
import DAF.GameObject;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.RenderManager;
import DAF.Renderer.Server.ServerRenderer;
import DAF.Socket.HttpSocket.Resource.JsonBufferResource;
import DAF.Socket.IResource;
import DAF.Socket.IServerSocket;
import DAF.Socket.ISocketListener;
import org.json.JSONObject;

public class ButtonServerHandler extends AServerHandler implements ISocketListener {
    @Override
    public Class<? extends AInputEvent> getInputEventType() {
        return ButtonInputEvent.class;
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

        String inputType = jsonData.getString("@type");
        if(this.getInputEventType().getName().equalsIgnoreCase(inputType)) {
            // {"keycode":"1","x":"835","y":"662"}
            int keycode = jsonData.getInt("keycode");
            int source = jsonData.getInt("source");
            int controller = jsonData.getInt("controller");

            GameObject sourceObject = GameObject.find(source);
            if(sourceObject == null) {
                throw new NullPointerException("ButtonServerHandler: Unknown gameObject "+source);
            }

            ButtonGraphic buttonGraphic = sourceObject.getComponent(ButtonGraphic.class);
            if(buttonGraphic == null) {
                throw new NullPointerException("ButtonServerHandler: No attached ButtonGraphic to gameObject "+source);
            }

            ButtonInputEvent event = new ButtonInputEvent(
                    KeyState.Up,
                    buttonGraphic,
                    keycode,
                    controller
            );

            this.addDelayedEvent(event);
        }
    }

    @Override
    public void onSocketTransmission(IResource resource) {

    }

    @Override
    public void onSocketPrepareTransmission(IResource resource) {

    }
}