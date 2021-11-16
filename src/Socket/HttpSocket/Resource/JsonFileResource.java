package Socket.HttpSocket.Resource;

import java.io.IOException;

import Socket.HttpSocket.HttpServerSocket;

import com.sun.net.httpserver.HttpExchange;

public class JsonFileResource extends FileResource
{
    public JsonFileResource(HttpServerSocket server, String path) {
        super(server, path);
    }

    @Override
    public String getContentType() { return "text/json"; }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        
    }
}
