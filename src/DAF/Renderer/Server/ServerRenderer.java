package DAF.Renderer.Server;

import DAF.Renderer.AGraphicRenderer;
import DAF.Renderer.Components.AGraphic;
import DAF.Serializer.ASerializer;
import DAF.Socket.IServerSocket;
import DAF.Socket.HttpSocket.HttpServerSocket;
import DAF.Socket.HttpSocket.Resource.JsonBufferResource;
import java.util.LinkedList;

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
