package Socket.HttpSocket.Resource;

import java.io.File;
import java.io.IOException;

import Socket.HttpSocket.HttpServerSocket;

import com.sun.net.httpserver.HttpExchange;

public class PngFileResource extends FileResource
{
    protected PngFileResource() {
        super();
    }

    public PngFileResource(HttpServerSocket server, String path, File file) {
        super(server, path, file);
    }

    @Override
    public String getContentType() { return "image/png"; }

    @Override
    public String[] getFileExtension() { return new String[]{ ".png" }; }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        super.handle(exchange);
    }
}
