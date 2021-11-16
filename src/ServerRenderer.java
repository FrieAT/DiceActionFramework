import javafx.scene.Node;

import java.util.ArrayList;

import Socket.IServerSocket;
import Socket.ISocketListener;
import Socket.HttpSocket.SocketServerException;

public class ServerRenderer extends AGraphicRenderer {

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
        return true;
    }

    @Override
    public void render(AGraphic g) {

        for (AGraphicRenderer renderer : this._graphicRenderer) {
            ServerRenderer serverRenderer = (ServerRenderer) renderer;

            if (serverRenderer == null) {
                System.out.println("WARNING: " + renderer.getClass().getName() + " != " + this.getClass().getName());
                continue;
            }

            fetchFrame.append(serializer.serialize(g)).append(",");

        }
    }

    @Override
    public boolean afterRender() {
        fetchFrame.replace(fetchFrame.length() - 1, fetchFrame.length(), "");
        fetchFrame.append("]");
        System.out.println(fetchFrame.toString());
        fetchFrame = new StringBuilder("");
        Init();
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

        fetchFrame.append("[");
    }

    public Node renderAsSerialized(AGraphic g) {
        throw new NullPointerException("Not implemented, implement this in derived subclass.");
    }
}
