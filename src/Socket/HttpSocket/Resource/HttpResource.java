package Socket.HttpSocket.Resource;

import java.io.BufferedOutputStream;
import java.io.IOException;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Socket.IResource;
import Socket.HttpSocket.HttpServerSocket;

public abstract class HttpResource implements HttpHandler, IResource
{
    protected byte[] _data;

    private String _path;

    private HttpServerSocket _server;

    protected HttpResource() {
        this._data = new byte[0];
        this._server = null;
    }

    public HttpResource(HttpServerSocket server, String path) {
        this();

        this._path = path;
        this._server = server;

        if(server != null) {
            server.getSocket().createContext(path, this);
        }
    }

    public String getResourcePath() { return this._path; }

    public HttpServerSocket getSocket() { return this._server; }

    public HttpServer getServerContext() { return this._server.getSocket(); }

    public byte[] getBufferedData() {
        byte[] data = this._data;

        return data;
    }

    public String getContentType() {
        throw new NullPointerException("Not implemented Content-type.");
    }

    public void handle(HttpExchange exchange) throws IOException {
        byte[] response = getBufferedData();

        exchange.getResponseHeaders().add("Content-type", this.getContentType()); 
        
        exchange.sendResponseHeaders(200, response.length);
        try(BufferedOutputStream buffer = new BufferedOutputStream(exchange.getResponseBody()))
        {
            buffer.write(response);
        }

        this.getSocket().receiveData(exchange);
    }
}