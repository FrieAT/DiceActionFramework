package Socket.HttpSocket.Resource;

import java.io.IOException;

import Socket.HttpSocket.HttpServerSocket;

import com.sun.net.httpserver.HttpExchange;

public class GifFileResource extends FileResource
{
    public GifFileResource(HttpServerSocket server, String path) {
        super(server, path);
    }

    @Override
    public String getContentType() { return "image/gif"; }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        
    }
}
