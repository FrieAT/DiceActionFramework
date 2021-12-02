package DAF.Socket.HttpSocket.Resource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import com.sun.net.httpserver.HttpExchange;

import DAF.Socket.HttpSocket.HttpServerSocket;

public class DirectoryResource extends AFileResource
{
    protected LinkedList<AFileResource> _filterResources;

    protected HashMap<String, AFileResource> _fileList;

    private File _localPath;

    private boolean _recursive;

    public DirectoryResource(HttpServerSocket server, String path, File localPath, boolean recursive) {
        super(server, path, localPath);

        if(!localPath.isDirectory()) {
            throw new NullPointerException("Given path is not a directory.");
        }

        this._recursive = recursive;
        this._localPath = localPath;
        this._filterResources = new LinkedList<>();
        this._fileList = new HashMap<>();
    }

    public DirectoryResource(HttpServerSocket server, String path, File localPath) {
        this(server, path, localPath, false);
    }

    @Override
    public String getContentType() { return "text/html"; }

    public <T extends AFileResource>
    void addResource(Class<T> resource)
    {
        try {
            this._filterResources.add(
                resource
                    .getConstructor(
                        HttpServerSocket.class,
                        String.class,
                        File.class
                    )
                    .newInstance(null, "", null));
        }
        catch(Exception e) {
            throw new NullPointerException(e.getMessage());
        }

        refreshFileCache();
    }

    @Override
    public byte[] getBufferedData() {
        String output = "<h1>Directory Info</h1>";

        for(String file : this._fileList.keySet()) {
            output += "<p>"+file+"</p>";
        }

        return output.getBytes();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        refreshFileCache();
        
        super.handle(exchange);
    }

    private void refreshFileCache() {
        for(File f : this._localPath.listFiles()) {
            if(this._fileList.containsKey(f.getName())) {
                continue;
            }

            if(f.isDirectory()) {
                DirectoryResource resource = new DirectoryResource(
                    this.getSocket(),
                    this.getResourcePath()+"/"+f.getName(),
                    f,
                    this._recursive);
                
                this._fileList.put(f.getName(), resource);   
            } else {
                for(AFileResource check : this._filterResources) {
                    for(String fileExtension : check.getFileExtension()) {
                        if(f.getName().endsWith(fileExtension)) {
                            try {
                                AFileResource resource = check.getClass()
                                    .getConstructor(
                                        HttpServerSocket.class,
                                        String.class,
                                        File.class
                                    )
                                    .newInstance(
                                        this.getSocket(),
                                        this.getResourcePath()+"/"+f.getName(),
                                        f
                                    );
                                
                                this._fileList.put(f.getName(), resource);
                            }
                            catch(Exception e) {
                                throw new NullPointerException(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }
}
