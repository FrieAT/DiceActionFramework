package DAF.Input;

import DAF.Event.*;
import DAF.GameObject;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.InputGraphic;
import DAF.Renderer.RenderManager;
import DAF.Renderer.Server.ServerRenderer;
import DAF.Socket.HttpSocket.Resource.JsonBufferResource;
import DAF.Socket.IResource;
import DAF.Socket.IServerSocket;
import DAF.Socket.ISocketListener;
import org.json.JSONObject;

public class InputServerHandler extends AServerHandler implements ISocketListener {
    @Override
    public Class<? extends AInputEvent> getInputEventType() {
        return TextInputEvent.class;
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
            int source = jsonData.getInt("source");
            int controller = jsonData.getInt("controller");
            String text = jsonData.getString("text");

            GameObject sourceObject = GameObject.find(source);
            if(sourceObject == null) {
                throw new NullPointerException("InputServerHandler: Unknown gameObject "+source);
            }

            InputGraphic inputGraphic = sourceObject.getComponent(InputGraphic.class);
            if(inputGraphic == null) {
                throw new NullPointerException("InputServerHandler: No attached InputGraphic to gameObject "+source);
            }

            inputGraphic.setText(text);

            TextInputEvent event = new TextInputEvent(
                    inputGraphic
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