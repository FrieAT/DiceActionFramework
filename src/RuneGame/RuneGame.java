
import java.io.File;
import java.util.LinkedList;

import DAF.AbstractManager;
import DAF.GameObject;
import DAF.Controller.ControllerManager;
import DAF.Controller.Components.ControllerSocket;
import DAF.Controller.Components.ControllerView;
import DAF.Controller.Components.IController;
import DAF.Controller.Components.PlayerController;
import DAF.Dice.DiceManager;
import DAF.Dice.Components.ADice;
import DAF.Dice.Components.ClassicDice;
import DAF.Input.*;
import DAF.Math.Vector2;
import DAF.Renderer.Components.InputGraphic;
//import DAF.Renderer.JavaFX.*;
import DAF.Renderer.RenderManager;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.LabelGraphic;
import DAF.Renderer.Components.PictureGraphic;
import DAF.Renderer.Server.ServerRenderer;
import DAF.Serializer.ASerializer;
import DAF.Serializer.JsonSerializer;
import DAF.Socket.HttpSocket.HttpResourceExistsException;
import DAF.Socket.HttpSocket.HttpServerSocket;
import DAF.Socket.HttpSocket.SocketServerException;
import DAF.Socket.HttpSocket.Resource.DirectoryResource;
import DAF.Socket.HttpSocket.Resource.GifFileResource;
import DAF.Socket.HttpSocket.Resource.HtmlFileResource;
import DAF.Socket.HttpSocket.Resource.JpegFileResource;
import DAF.Socket.HttpSocket.Resource.JsonBufferResource;
import DAF.Socket.HttpSocket.Resource.PngFileResource;
import RuneGame.RuneGameManager;

public class RuneGame {

    public static void main (String[] args) {

        GameObject controllerSocket = new GameObject("ControllerSocket");
        controllerSocket.addComponent(ControllerSocket.class);
        
        LinkedList<AbstractManager> _managers = new LinkedList<>();

        //Pre-initialization
        /*JavaFXRenderer renderer = new JavaFXRenderer();
        renderer.add(new PictureGraphicJavaFXRenderer());
        renderer.add(new LabelGraphicJavaFXRenderer());
        renderer.add(new ButtonGraphicJavaFXRenderer());
        renderer.add(new InputGraphicJavaFXRenderer());
        RenderManager.getInstance().addRenderer(renderer);*/

        ASerializer jsonSerializer = new JsonSerializer();
        
        HttpServerSocket socket = new HttpServerSocket("localhost:1337");
        try {
            socket.bind();
            DirectoryResource dir = (DirectoryResource)socket.addResource(DirectoryResource.class, "/images", new File("images/"));
            dir.addResource(JpegFileResource.class);
            dir.addResource(PngFileResource.class);
            dir.addResource(GifFileResource.class);

            socket.addResource(JsonBufferResource.class, ControllerSocket.apiFetchFrameForPlayer);
            socket.addResource(JsonBufferResource.class, ServerRenderer.apiFetchFrame);
            socket.addResource(JsonBufferResource.class, ServerRenderer.apiEvent);

            
            socket.addResource(JsonBufferResource.class, ControllerSocket.apiConfirmFrameForPlayer);

            socket.addResource(HtmlFileResource.class, "/", new File("www/index.html"));
        }
        catch(SocketServerException|HttpResourceExistsException e) {
            throw new NullPointerException(e.getMessage());
        }

        ServerRenderer serverRenderer = new ServerRenderer();
        serverRenderer.setSerializer(jsonSerializer);
        serverRenderer.setSocket(socket);
        RenderManager.getInstance().addRenderer(serverRenderer);

        /*
        InputManager.getInstance().addInputHandler(new MouseJavaFXHandler());
        InputManager.getInstance().addInputHandler(new ButtonJavaFXHandler());
        InputManager.getInstance().addInputHandler(new InputJavaFXHandler());
        */
        InputManager.getInstance().addInputHandler(new MouseServerHandler());
        InputManager.getInstance().addInputHandler(new ButtonServerHandler());
        InputManager.getInstance().addInputHandler(new InputServerHandler());

        //Adding the managers.
        _managers.add(RenderManager.getInstance());
        _managers.add(DiceManager.getInstance());
        _managers.add(InputManager.getInstance());
        _managers.add(ControllerManager.getInstance());
        _managers.add(RuneGameManager.getInstance());

        //Initialization of the managers.
        for(AbstractManager m : _managers) {
            m.init();
        }

        //Initialize all game objects with their components.
        GameObject.startAll();

        //Game loop
        while(true) {
            //TODO: If game quits or exception happens?

            GameObject.updateAll();

            for(AbstractManager m : _managers) {
                m.update();
            }

            //FIXME: Just used as a delay for main thread to reduce CPU usage.
            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
