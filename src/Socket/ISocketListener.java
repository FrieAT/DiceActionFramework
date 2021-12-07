package Socket;

public interface ISocketListener {
    void onSocketReceive(IResource resource);

    void onSocketTransmission(IResource resource);

    void onSocketPrepareTransmission(IResource resource);
}
