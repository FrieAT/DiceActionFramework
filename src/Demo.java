
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

import Socket.HttpSocket.SocketServerException;
import Socket.HttpSocket.HttpServerSocket;
import Socket.HttpSocket.HttpResourceExistsException;
import Socket.HttpSocket.Resource.DirectoryResource;
import Socket.HttpSocket.Resource.GifFileResource;
import Socket.HttpSocket.Resource.HtmlFileResource;
import Socket.HttpSocket.Resource.HttpResource;
import Socket.HttpSocket.Resource.JpegFileResource;
import Socket.HttpSocket.Resource.JsonBufferResource;
import Socket.HttpSocket.Resource.PngFileResource;
import javafx.scene.transform.Transform;

public class Demo {

    public static void main (String[] args) {
        //g1.addComponent(ClassicDice.class);
        //System.out.println(classicDice.getDiceSides());

        /*
        GameObject g2 = new GameObject("BackgammonDice");
        ADiceBag bag = new ADiceBag();
        bag.add(new ClassicDice());
        bag.add(new ClassicDice());
        System.out.println(g2.getComponents());
        */

        
        GameObject background = new GameObject("MainBackground");

        
        PictureGraphic bgImage = background.addComponent(PictureGraphic.class);
        bgImage.setPicturePath("images/gameboard.png");
        bgImage.setWidth(1024);
        bgImage.setHeight(768);
        bgImage.setLeft(0);
        bgImage.setTop(0);

        //background.addComponent(new StupidComponent());
        
        GameObject gameName = new GameObject("GameName");
        LabelGraphic gameNameLabel = gameName.addComponent(LabelGraphic.class);
        gameNameLabel.setLeft(400);
        gameNameLabel.setTop(0);
        gameNameLabel.setFontSize(20);
        gameNameLabel.setBold(true);
        gameNameLabel.setLabelText("Man, Don't Get Angry");
        gameName.getTransform().setPosition(new Vector2(400, 20));

        GameObject gameName2 = new GameObject("GameName(2)");
        LabelGraphic gameNameLabel2 = gameName2.addComponent(LabelGraphic.class);
        gameNameLabel.setLeft(400);
        gameNameLabel.setTop(50);
        gameNameLabel.setFontSize(20);
        gameNameLabel.setBold(true);
        gameNameLabel.setLabelText("Okay?");
        gameName2.getTransform().setPosition(new Vector2(400, 50));

        /*
        GameObject g1 = new GameObject("Wuerfel");
        g1.addComponent(ClassicDice.class);
        g1.getTransform().setPosition(new Vector2(600, 600));
        g1.getTransform().setScale(new Vector2(4, 4));

         */

        GameObject g2 = new GameObject("Wuerfel");
        g2.addComponent(ClassicDice.class);
        g2.getTransform().setPosition(new Vector2(400, 400));
        g2.getTransform().setScale(new Vector2(15, 15));

        GameObject rollButton = new GameObject("Roll_Button");
        ButtonGraphic buttonG = rollButton.addComponent(ButtonGraphic.class);
        buttonG.setLabelText("Würfeln!");
        buttonG.setWidth(300);
        buttonG.setHeight(50);
        rollButton.getTransform().setPosition(new Vector2(800, 600));
        rollButton.addComponent(RollDiceButtonController.class);

        GameObject player1 = new GameObject("PlayerOne");
        PictureGraphic playerGraphic = player1.addComponent(PictureGraphic.class);
        playerGraphic.setPicturePath("images/player2.png");
        playerGraphic.setWidth(50);
        playerGraphic.setHeight(75);
        playerGraphic.setLeft(480);
        playerGraphic.setTop(550);
        player1.getTransform().setScale(new Vector2(0.5, 0.5));
        player1.addComponent(PlayerController.class);

        GameObject controllerSocket = new GameObject("ControllerSocket");
        controllerSocket.addComponent(ControllerSocket.class);
        
        LinkedList<AbstractManager> _managers = new LinkedList<>();

        //Pre-initialization
        JavaFXRenderer renderer = new JavaFXRenderer();
        renderer.add(new PictureGraphicJavaFXRenderer());
        renderer.add(new LabelGraphicJavaFXRenderer());
        renderer.add(new ButtonGraphicJavaFXRenderer());
        RenderManager.getInstance().addRenderer(renderer);

        ASerializer jsonSerializer = new JsonSerializer();
        
        HttpServerSocket socket = new HttpServerSocket("localhost:1337");
        try {
            socket.bind();
            DirectoryResource dir = (DirectoryResource)socket.addResource(DirectoryResource.class, "/images", new File("images/"));
            dir.addResource(JpegFileResource.class);
            dir.addResource(PngFileResource.class);
            dir.addResource(GifFileResource.class);

            socket.addResource(JsonBufferResource.class, ServerRenderer.apiFetchFrame);
            socket.addResource(JsonBufferResource.class, ServerRenderer.apiEvent);

            socket.addResource(JsonBufferResource.class, ControllerSocket.apiFetchFrameForPlayer);
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

        AInputHandler input = new MouseJavaFXHandler();
        InputManager.getInstance().addInputHandler(input);
        InputManager.getInstance().addInputHandler(new MouseServerHandler());



        //Adding the managers.
        _managers.add(RenderManager.getInstance());
        _managers.add(DiceManager.getInstance());
        _managers.add(InputManager.getInstance());

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
