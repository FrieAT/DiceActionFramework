import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

import javax.net.ssl.ExtendedSSLSession;

import com.sun.net.httpserver.HttpServer;

class HttpServerException extends IOException
{
    public HttpServerException(String message) { super(message); }

    public HttpServerException(String message, Exception cause) { super(message, cause); }
}

class HttpResourceExistsException extends IOException
{
    public HttpResourceExistsException(String message) { super(message); }

    public HttpResourceExistsException(String message, Exception cause) { super(message, cause); }
}

abstract class HttpResource
{
    public String getContentType() {
        throw new NullPointerException("Not implemented Content-type.");
     }
}

class FileResource extends HttpResource
{
    private String _path;

    public FileResource(String path) {

    }

    @Override
    public String getContentType() { return "text/plain"; }
}

class DirectoryResource extends FileResource
{
    public DirectoryResource(String path, boolean recursive) {
        super(path);
    }
}

class JsonFileResource extends FileResource
{
    public JsonFileResource(String path) {
        super(path);
    }

    @Override
    public String getContentType() { return "text/json"; }
}

class JpegFileResource extends FileResource
{
    public JpegFileResource(String path) {
        super(path);
    }

    @Override
    public String getContentType() { return "image/jpeg"; }
}

class PngFileResource extends FileResource
{
    public PngFileResource(String path) {
        super(path);
    }

    @Override
    public String getContentType() { return "image/png"; }
}

class GifFileResource extends FileResource
{
    public GifFileResource(String path) {
        super(path);
    }

    @Override
    public String getContentType() { return "image/gif"; }
}

public class HttpServerSocket implements IServerSocket
{
    private String _bindAddress;

    private HttpServer _server;

    private HashMap<String, HttpResource> _resources;

    public HttpServerSocket(String bindAddress) {
        this._bindAddress = bindAddress;
        this._server = null;
        this._resources = new HashMap<>();
    }

    public void bind() throws HttpServerException {
        try {
            String[] bindAddressPort = this._bindAddress.split(":");
            this._server = HttpServer.create(new InetSocketAddress(bindAddressPort[0], Integer.parseInt(bindAddressPort[1])), 0);
        }
        catch(IOException|NumberFormatException e) {
            throw new HttpServerException(this.getClass().getName()+" initialization failed", e);
        }
    }

    public void close() throws HttpServerException {
        if(this._server == null) {
            throw new HttpServerException("Server hasn't been binded.");
        }
        this._server.stop(2);
        this._server = null;
    }

    public void transmitData(String data) {

    }

    public void addResource(String uri, HttpResource resource) throws HttpResourceExistsException {
        if(this._resources.containsKey(uri)) {
            throw new HttpResourceExistsException("Resource "+uri+" is already in use.");
        }
        this._resources.put(uri, resource);
    }

    public void deleteResource(String uri) throws HttpResourceExistsException {
        if(!this._resources.containsKey(uri)) {
            throw new HttpResourceExistsException("Resource "+uri+" is undefined.");
        }
        this._resources.remove(uri);
    }

    public void addReceiveDataListener(ISocketListener listener) {

    }
}
