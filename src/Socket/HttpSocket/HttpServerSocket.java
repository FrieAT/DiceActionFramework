package Socket.HttpSocket;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import Socket.IServerSocket;
import Socket.ISocketListener;
import Socket.HttpSocket.Resource.HttpResource;

public class HttpServerSocket implements IServerSocket
{
    private String _bindAddress;

    private HttpServer _server;

    private HashMap<String, HttpResource> _resources;

    private HashMap<String, LinkedList<ISocketListener>> _listeners;

    public HttpServerSocket(String bindAddress) {
        this._bindAddress = bindAddress;
        this._server = null;
        this._resources = new HashMap<>();
        this._listeners = new HashMap<>();
    }

    public void bind() throws HttpServerException {
        if(this._server != null) {
            throw new HttpServerException("Server has been already binded.");
        }
        try {
            String[] bindAddressPort = this._bindAddress.split(":");
            this._server = HttpServer.create(new InetSocketAddress(bindAddressPort[0], Integer.parseInt(bindAddressPort[1])), 0);
        }
        catch(IOException|NumberFormatException e) {
            throw new HttpServerException(this.getClass().getName()+" initialization failed", e);
        }
        this._server.start();
    }

    public void close() throws HttpServerException {
        if(this._server == null) {
            throw new HttpServerException("Server hasn't been binded.");
        }
        this._server.stop(2);
        this._server = null;
    }

    public void addResource(Class<HttpResource> resourceClass, String uri) throws HttpResourceExistsException
    {
        if(this._resources.containsKey(uri)) {
            throw new HttpResourceExistsException("Resource "+uri+" is already in use.");
        }

        HttpResource resource = null;

        try {
            resource = resourceClass.getConstructor(this.getClass(), String.class).newInstance(this, uri);
        }
        catch(NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|InvocationTargetException e)
        {
            throw new IllegalArgumentException("An error occured adding a ressource path.", e);
        }

        this._resources.put(uri, resource);
    }

    public void deleteResource(String uri) throws HttpResourceExistsException {
        if(!this._resources.containsKey(uri)) {
            throw new HttpResourceExistsException("Resource "+uri+" is undefined.");
        }

        this._server.removeContext(uri);

        this._resources.remove(uri);
    }

    public void transmitData(HttpResource data) {
        HttpResource resource = this._resources.get(data.getResourcePath());

        if(resource == null) {
            throw new IllegalArgumentException("Resource "+data.getResourcePath()+" is undefined.");
        }

        resource.writeBuffer(data);
    }
    
    public void receiveData(HttpExchange exchange) {
        String uri = exchange.getRequestURI().getPath();
        LinkedList<ISocketListener> list = this._listeners.get(uri);
        HttpResource resource = this._resources.get(uri);
        
        if(list != null && resource != null) {
            for(ISocketListener listener : list) {
                listener.onSocketTransmission(resource);
            }
        }
    }

    public void addReceiveDataListener(String uri, ISocketListener listener) {
        if(!this._resources.containsKey(uri)) {
            throw new IllegalArgumentException("Resource "+uri+" is undefined.");
        }
        
        LinkedList<ISocketListener> list = this._listeners.get(uri);
        //FIXME: What if key already exists and was defined as null?
        if(list == null) {
            list = this._listeners.put(uri, new LinkedList<>());
        }

        list.add(listener);
    }

    public HttpServer getSocket() { return this._server; }
}
