package Socket;

import Socket.HttpSocket.Resource.HttpResource;

public interface IServerSocket {
    void transmitBufferedResource(HttpResource data, boolean clear);
    
    void addReceiveDataListener(String uri, ISocketListener listener);
}
