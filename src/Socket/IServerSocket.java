package Socket;

public interface IServerSocket {
    void transmitData(String data);
    
    void addReceiveDataListener(String uri, ISocketListener listener);
}
