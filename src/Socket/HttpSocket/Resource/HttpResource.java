package Socket.HttpSocket.Resource;

import java.io.IOException;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class HttpResource implements HttpHandler
{
    public String getContentType() {
        throw new NullPointerException("Not implemented Content-type.");
    }

    public void handle(HttpExchange exchange) throws IOException {
        throw new IOException("Resource doesn't handle connection.");
    }
}