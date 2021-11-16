package Socket.HttpSocket.Resource;

import java.io.File;
import java.io.IOException;

import Socket.HttpSocket.HttpServerSocket;

import com.sun.net.httpserver.HttpExchange;

public class JpegFileResource extends AFileResource
{
    protected JpegFileResource() {
        super();
    }

    public JpegFileResource(HttpServerSocket server, String path, File file) {
        super(server, path, file);
    }

    @Override
    public String getContentType() { return "image/jpeg"; }

    @Override
    public String[] getFileExtension() { return new String[]{ ".jpg", ".jpeg" }; }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        super.handle(exchange);
    }
}
