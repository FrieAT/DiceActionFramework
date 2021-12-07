import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle.Control;

import Socket.IResource;
import Socket.IServerSocket;
import Socket.ISocketListener;
import Socket.HttpSocket.HttpResourceExistsException;
import Socket.HttpSocket.HttpServerSocket;
import Socket.HttpSocket.Resource.ABufferResource;
import Socket.HttpSocket.Resource.JsonBufferResource;

public class ServerRenderer extends AGraphicRenderer {
    public static final String apiFetchFrame = "/api/fetchFrame.json";

    public static final String apiEvent = "/api/event.json";

    private ASerializer serializer;

    StringBuilder fetchFrame;

    private IServerSocket _socket;

    private LinkedList<Integer> _renderAcks;

    public ServerRenderer() {
        this.fetchFrame = new StringBuilder();
        this.serializer = null;
        this._socket = null;
        this._renderAcks = new LinkedList<>();
    }

    public ASerializer getSerializer() { return this.serializer; }

    public void setSerializer(ASerializer serializer) { this.serializer = serializer; }

    public IServerSocket getSocket() { return this._socket; }

    public void setSocket(IServerSocket socket) { this._socket = socket; }

    @Override
    public boolean beforeRender() {
        fetchFrame.append("[");
        return true;
    }

    @Override
    public void render(AGraphic g) {
        if(!g.getGameObject().isEnabled()) {
            return;
        }

        fetchFrame.append(serializer.serialize(g)).append(",");
    }

    @Override
    public boolean afterRender() {
        if(fetchFrame.length() > 0) {
            fetchFrame.replace(fetchFrame.length() - 1, fetchFrame.length(), "");
        }
        fetchFrame.append("]");

        JsonBufferResource jsonBuffer = new JsonBufferResource((HttpServerSocket)this._socket, apiFetchFrame, fetchFrame.toString());
        this._socket.transmit(jsonBuffer, true);

        fetchFrame = new StringBuilder("");
        return true;
    }

    @Override
    public void Init() {
        if(this._socket == null) {
            throw new NullPointerException("Please set a "+IServerSocket.class.getName()+" for "+this.getClass().getName());
        }

        if(this.serializer == null) {
            throw new NullPointerException("Please set a "+ASerializer.class.getName()+" for "+this.getClass().getName());
        }

        fetchFrame.append("[");
    }

    public void addControllerAcknowledgement(int controllerIndex) {
        if(!this._renderAcks.contains(controllerIndex)) {
            this._renderAcks.push(controllerIndex);
        }
    }
}
