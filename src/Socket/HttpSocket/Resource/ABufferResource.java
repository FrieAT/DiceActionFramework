package Socket.HttpSocket.Resource;

import java.util.LinkedList;
import java.util.Queue;

import Socket.IResource;
import Socket.HttpSocket.HttpServerSocket;

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
        
        while(!this._bufferList.isEmpty()) {
            String appendData = new String(this._bufferList.poll().getBufferedData());
            data += appendData;
        }

        return data.getBytes();
    }

    public void writeBuffer(String data) {
        this._buffer = data;
    }

    public void writeBuffer(IResource resource) {
        this._bufferList.add(resource);
    }

    public void clearBuffer() {
        this._bufferList.clear();
    }
}
