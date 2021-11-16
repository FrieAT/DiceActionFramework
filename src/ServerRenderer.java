import Socket.IResource;
import Socket.IServerSocket;
import Socket.ISocketListener;
import Socket.HttpSocket.HttpServerSocket;
import Socket.HttpSocket.Resource.JsonBufferResource;

public class ServerRenderer extends AGraphicRenderer implements ISocketListener {

    private ASerializer serializer;

    StringBuilder fetchFrame;

    private IServerSocket _socket;

    public ServerRenderer() {
        this.fetchFrame = new StringBuilder();
        this.serializer = null;
        this._socket = null;
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

        JsonBufferResource jsonBuffer = new JsonBufferResource((HttpServerSocket)this._socket, "/api/fetchFrame.json", fetchFrame.toString());
        this._socket.transmit(jsonBuffer, false);

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

        //FIXME: Shouldn't be the socket bind-ed here?

        this._socket.addListener("/api/fetchFrame.json", this);

        fetchFrame.append("[");
    }

    public void onSocketTransmission(IResource resource) {

    }
}
