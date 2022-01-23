package DAF.Socket.HttpSocket.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import DAF.Socket.HttpSocket.HttpServerSocket;

public abstract class AFileResource extends HttpResource
{
    private File _file;

    protected AFileResource() {
        super();
    }

    public AFileResource(HttpServerSocket server, String path, File file) {
        super(server, path);

        if(file != null && !file.exists()) {
            file = new File(getClass().getClassLoader().getResource(file.getPath()).toString());
        }

        this._file = file;
    }

    @Override
    public String getContentType() { return "text/plain"; }

    public String[] getFileExtension() { return new String[]{ ".txt" }; }

    @Override
    public byte[] getBufferedData() {
        try
        {
            this._data = Files.readAllBytes(this._file.toPath());
        }
        catch(IOException e) {
            //TODO: Make custom exception on IOException.
            throw new NullPointerException("Error reading input file.");
        }

        byte[] data = super.getBufferedData();

        //this._data = ""; //Clear memory usage, because we re-read file again on need.

        return data;
    }
}
