package Socket.HttpSocket;

import java.io.IOException;

public class HttpResourceExistsException extends IOException
{
    public HttpResourceExistsException(String message) { super(message); }

    public HttpResourceExistsException(String message, Exception cause) { super(message, cause); }
}