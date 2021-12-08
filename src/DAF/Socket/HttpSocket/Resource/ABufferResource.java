package DAF.Socket.HttpSocket.Resource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.sun.net.httpserver.HttpExchange;

import DAF.Socket.IResource;
import DAF.Socket.HttpSocket.HttpServerSocket;

public abstract class ABufferResource extends HttpResource {
    protected String _buffer;
    
    private Queue<IResource> _bufferList;

    protected ABufferResource() {
        this._bufferList = new LinkedList<>();
    }

    public ABufferResource(HttpServerSocket server, String path) {
        super(server, path);

        this._bufferList = new LinkedList<>();
    }

    @Override
    public byte[] getBufferedData() {
        String data = this._buffer;
        
        Iterator<IResource> it = this._bufferList.iterator();
        while(it.hasNext()) {
            String peekBuffer = new String(it.next().getBufferedData());
            data += peekBuffer;
        }

        return data.getBytes();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this._buffer = "";
        
        //[optional] POST-data
        try(BufferedInputStream buffer = new BufferedInputStream(exchange.getRequestBody()))
        {
            this._buffer = new String(buffer.readAllBytes());
            
            if(this._buffer.length() > 0) {
                String dataPrefix = "data=";
                if(this._buffer.startsWith(dataPrefix)) {
                    this._buffer = this._buffer.substring(dataPrefix.length());
                }

                this._buffer = URLDecoder.decode(this._buffer, "UTF-8");
            }
        }

        //[optional] GET-data
        String requestedUri = exchange.getRequestURI().toString();
        int getParamIndex = requestedUri.indexOf("?");
        if(getParamIndex != -1) {
            String getData = requestedUri.substring(getParamIndex+1);
            if(this._buffer.length() > 0) {
                this._buffer += "&";
            }
            this._buffer += getData;
        }

        super.handle(exchange);
    }

    public void writeBuffer(String data) {
        this._buffer = data;
    }

    public void writeBuffer(IResource resource) {
        this._bufferList.add(resource);
    }

    public void clearBuffer() {
        this._data = new byte[0];
        this._buffer = "";
        this._bufferList.clear();
    }
}
