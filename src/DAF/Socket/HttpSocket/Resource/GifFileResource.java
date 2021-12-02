package Socket.HttpSocket.Resource;

import java.io.File;

import Socket.HttpSocket.HttpServerSocket;

public class GifFileResource extends AFileResource
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
