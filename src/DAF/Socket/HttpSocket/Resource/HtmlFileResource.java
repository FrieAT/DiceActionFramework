package DAF.Socket.HttpSocket.Resource;

import java.io.File;

import DAF.Socket.HttpSocket.HttpServerSocket;

public class HtmlFileResource extends AFileResource
{
    protected HtmlFileResource() {
        super();
    }

    public HtmlFileResource(HttpServerSocket server, String path, File file) {
        super(server, path, file);
    }

    @Override
    public String getContentType() { return "text/html"; }

    @Override
    public String[] getFileExtension() { return new String[]{ ".html" }; }
}
