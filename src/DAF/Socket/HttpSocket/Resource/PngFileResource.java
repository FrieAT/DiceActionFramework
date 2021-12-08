package DAF.Socket.HttpSocket.Resource;

import java.io.File;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import DAF.Socket.HttpSocket.HttpServerSocket;

public class PngFileResource extends AFileResource
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
