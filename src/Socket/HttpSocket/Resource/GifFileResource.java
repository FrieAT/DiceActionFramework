package Socket.HttpSocket.Resource;

import java.io.File;
import java.io.IOException;

import Socket.HttpSocket.HttpServerSocket;

import com.sun.net.httpserver.HttpExchange;

public class GifFileResource extends FileResource
{
    protected GifFileResource() {
        super();
    }

    public GifFileResource(HttpServerSocket server, String path, File file) {
        super(server, path, file);
    }

    @Override
    public String getContentType() { return "image/gif"; }

    @Override
    public String[] getFileExtension() { return new String[]{ ".gif" }; }
}
