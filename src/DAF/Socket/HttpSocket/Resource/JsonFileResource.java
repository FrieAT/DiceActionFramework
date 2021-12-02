package Socket.HttpSocket.Resource;

import java.io.File;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import Socket.HttpSocket.HttpServerSocket;

public class JsonFileResource extends AFileResource
{
    protected JsonFileResource() {
        super();
    }

    public JsonFileResource(HttpServerSocket server, String path, File file) {
        super(server, path, file);
    }

    @Override
    public String getContentType() { return "application/json"; }

    @Override
    public String[] getFileExtension() { return new String[]{ ".json" }; }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        super.handle(exchange);
    }
}
