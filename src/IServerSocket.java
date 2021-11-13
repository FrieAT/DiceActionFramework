

public interface IServerSocket {
    void transmitData(String data);
    
    void addReceiveDataListener(ISocketListener listener);
}
