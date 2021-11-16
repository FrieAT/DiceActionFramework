package Socket.HttpSocket.Resource;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Socket.HttpSocket.HttpServerSocket;

public abstract class HttpResource implements HttpHandler
{
    private Queue<HttpResource> _buffer;

    private String _data;

    private String _path;

    private HttpServerSocket _server;

    private HttpResource() {
        this._buffer = new LinkedList<>();
        this._data = "";
        this._server = null;
    }

    public HttpResource(String path, String data) {
        this();

        this._path = path;
    }

    public HttpResource(HttpServerSocket server, String path) {
        this();

        this._path = path;
        this._server = server;

        server.getSocket().createContext(path, this);
    }
    
    public String getResourcePath() { return this._path; }

    public HttpServerSocket getSocket() { return this._server; }

    public HttpServer getServerContext() { return this._server.getSocket(); }

    public void writeBuffer(HttpResource resource) {
        this._buffer.add(resource);
    }

    public String getBufferedData() {
        String data = this._data;

        while(!this._buffer.isEmpty()) {
            data += this._buffer.poll().getBufferedData();
        }

        return data;
    }

    public String getContentType() {
        throw new NullPointerException("Not implemented Content-type.");
    }

    public void handle(HttpExchange exchange) throws IOException {
        try(BufferedOutputStream buffer = new BufferedOutputStream(exchange.getResponseBody()))
        {
            buffer.write(getBufferedData().getBytes());
        }

        this.getSocket().receiveData(exchange);
    }
}