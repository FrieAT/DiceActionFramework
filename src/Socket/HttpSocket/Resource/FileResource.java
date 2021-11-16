package Socket.HttpSocket.Resource;

import java.io.IOException;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import Socket.HttpSocket.HttpServerSocket;

public class FileResource extends HttpResource
{
    public FileResource(HttpServerSocket server, String path) {
        super(server, path);
    }

    @Override
    public String getContentType() { return "text/plain"; }
}
