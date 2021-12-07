import java.util.ArrayList;
import java.util.ResourceBundle.Control;

import Socket.IResource;
import Socket.IServerSocket;
import Socket.ISocketListener;
import Socket.HttpSocket.HttpResourceExistsException;
import Socket.HttpSocket.HttpServerSocket;
import Socket.HttpSocket.Resource.ABufferResource;
import Socket.HttpSocket.Resource.JsonBufferResource;

/**
 * Needed as a helper component if a IServerSocket is in use.
 * This component modifies data send to the client based on ControllerView.
 */
public class ControllerSocket extends AbstractComponent implements ISocketListener {
    public static final String apiFetchFrameForPlayer = "/api/player.json";

    public static final String apiConfirmFrameForPlayer = "/api/confirm.json";

    public static final String apiFetchFrame = ServerRenderer.apiFetchFrame;
    
    public static final String paramControllerPrefix = "player=";

    private ServerRenderer _renderer;

    private ArrayList<String> _controllerRenderings;

    @Override
    public void start() {
        this._controllerRenderings = new ArrayList<>();
        for(int i = 0; i <= ControllerManager.getInstance().GetPlayerCount(); i++) {
            this._controllerRenderings.add("[]");
        }

        this._renderer = RenderManager.getInstance().getRenderer(ServerRenderer.class);
        if(this._renderer != null) {
            IServerSocket socket = this._renderer.getSocket();
            socket.addListener(apiFetchFrame, this);
            socket.addListener(apiFetchFrameForPlayer, this);
            socket.addListener(apiConfirmFrameForPlayer, this);
        }
    }

    @Override
    public void onSocketReceive(IResource resource) {
        ABufferResource buffer = (ABufferResource)resource;
        if(buffer != null) {
            String params = new String(buffer.getBufferedData());
            int index = params.indexOf(paramControllerPrefix);
            if(index != -1) {
                try {
                    String rawPlayerIndex = params.substring(index+paramControllerPrefix.length(), index+paramControllerPrefix.length()+1);
                    int controllerIndex = Integer.parseInt(rawPlayerIndex);

                    String requestPath = resource.getResourcePath();
                    if(requestPath.compareTo(apiConfirmFrameForPlayer) == 0) {
                        this._renderer.addControllerAcknowledgement(controllerIndex);
                        buffer.writeBuffer("{\"ok\": true}");
                    } else if(requestPath.compareTo(apiFetchFrameForPlayer) == 0) {
                        String fetchFrame = this._controllerRenderings.get(controllerIndex);
                        //buffer.writeBuffer(fetchFrame);
                        JsonBufferResource jsonBuffer = new JsonBufferResource((HttpServerSocket)this._renderer.getSocket(), apiFetchFrameForPlayer, fetchFrame);
                        this._renderer.getSocket().transmit(jsonBuffer, true);

                    }
                }
                catch(NumberFormatException e) {
                    //If not a valid controller index, then just ignore malformed data.
                }
            }
        }
    }

    @Override
    public void onSocketTransmission(IResource resource) {

    }

    @Override
    public synchronized void onSocketPrepareTransmission(IResource resource) {
        ABufferResource buffer = (ABufferResource)resource;
        if(buffer != null) {
            String requestPath = resource.getResourcePath();
            if(requestPath.compareTo(apiFetchFrame) == 0) {
                int currentControllerIndex = ControllerManager.getInstance().GetControllerAtCycle();
                this._controllerRenderings.set(currentControllerIndex, new String(buffer.getBufferedData()));
            }
        }
    }
}
