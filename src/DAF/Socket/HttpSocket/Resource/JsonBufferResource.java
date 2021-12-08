package DAF.Socket.HttpSocket.Resource;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import DAF.Socket.HttpSocket.HttpServerSocket;

public class JsonBufferResource extends ABufferResource {
    public JsonBufferResource(HttpServerSocket server, String path) {
        super(server, path);

        this._buffer = "";
    }

    public JsonBufferResource(HttpServerSocket server, String path, String data) {
        super(server, path);
        
        this._buffer = data;
    }
    
    @Override
    public String getContentType() { return "application/json"; }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        super.handle(exchange);
    }
}
