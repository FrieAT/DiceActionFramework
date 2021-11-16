package Socket;

import Socket.HttpSocket.Resource.HttpResource;

public interface IServerSocket {
    void transmitData(HttpResource data);
    
    void addReceiveDataListener(String uri, ISocketListener listener);
}
