package Socket.HttpSocket.Resource;

import java.io.IOException;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import Socket.HttpSocket.HttpServerSocket;

public class FileResource extends HttpResource
{
    private String _path;

    private HttpServerSocket _server;

    public FileResource(HttpServerSocket server, String path) {
        this._path = path;
        this._server = server;

        server.getSocket().createContext(path, this);
    }

    public String getResourcePath() { return this._path; }

    public HttpServer getServerContext() { return this._server.getSocket(); }

    @Override
    public String getContentType() { return "text/plain"; }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        this._server.receiveData(exchange);
    }
}
