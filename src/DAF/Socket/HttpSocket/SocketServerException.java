package DAF.Socket.HttpSocket;

import java.io.IOException;

public class SocketServerException extends IOException
{
    public SocketServerException(String message) { super(message); }

    public SocketServerException(String message, Exception cause) { super(message, cause); }
}
