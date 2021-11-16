package Socket.HttpSocket.Resource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Socket.HttpSocket.HttpServerSocket;

public class FileResource extends HttpResource
{
    private File _file;

    protected FileResource() {
        super();
    }

    public FileResource(HttpServerSocket server, String path, File file) {
        super(server, path);

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
