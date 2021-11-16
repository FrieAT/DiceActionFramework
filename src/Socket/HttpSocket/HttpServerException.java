package Socket.HttpSocket;

import java.io.IOException;

public class HttpServerException extends IOException
{
    public HttpServerException(String message) { super(message); }

    public HttpServerException(String message, Exception cause) { super(message, cause); }
}
