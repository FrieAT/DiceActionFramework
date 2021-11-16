package Socket.HttpSocket.Resource;

import java.io.IOException;

import Socket.HttpSocket.HttpServerSocket;

import com.sun.net.httpserver.HttpExchange;

public class JpegFileResource extends FileResource
{
    public JpegFileResource(HttpServerSocket server, String path) {
        super(server, path);
    }

    @Override
    public String getContentType() { return "image/jpeg"; }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        super.handle(exchange);
    }
}
