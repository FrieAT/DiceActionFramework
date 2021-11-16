package Socket;

import Socket.HttpSocket.Resource.HttpResource;

public interface ISocketListener {
    void onSocketTransmission(HttpResource resource);
}
