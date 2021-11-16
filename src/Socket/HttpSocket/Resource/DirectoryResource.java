package Socket.HttpSocket.Resource;

import java.io.IOException;

import Socket.HttpSocket.HttpServerSocket;

import com.sun.net.httpserver.HttpExchange;

public class DirectoryResource extends FileResource
{
    public DirectoryResource(HttpServerSocket server, String path, boolean recursive) {
        super(server, path);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        
    }
}
