package Socket.HttpSocket.Resource;

import java.io.IOException;

import Socket.HttpSocket.HttpServerSocket;

import com.sun.net.httpserver.HttpExchange;

public class PngFileResource extends FileResource
{
    public PngFileResource(HttpServerSocket server, String path) {
        super(server, path);
    }

    @Override
    public String getContentType() { return "image/png"; }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        
    }
}
