package Socket.HttpSocket.Resource;

import java.io.IOException;

import Socket.HttpSocket.HttpServerSocket;

import com.sun.net.httpserver.HttpExchange;

public class JsonFileResource extends FileResource
{
    @Override
    public String getContentType() { return "text/json"; }

    @Override
    public String[] getFileExtension() { return new String[]{ ".json" }; }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        super.handle(exchange);
    }
}
