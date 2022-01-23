package DAF.Socket;

import DAF.Socket.HttpSocket.SocketServerException;

public interface IServerSocket {
    void transmit(IResource data, boolean clear);
    
    void addListener(String uri, ISocketListener listener);

    void bind() throws SocketServerException;

    void close() throws SocketServerException;
}
