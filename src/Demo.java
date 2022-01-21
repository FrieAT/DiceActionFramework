
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
import DAF.Renderer.JavaFX.*;
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

        IController playerOneController, playerTwoController;

        GameObject player1 = new GameObject("PlayerOne");
        PictureGraphic playerGraphic = player1.addComponent(PictureGraphic.class);
        playerGraphic.setPicturePath("images/player2.png");
        playerGraphic.setWidth(50);
        playerGraphic.setHeight(75);
        playerGraphic.setLeft(480);
        playerGraphic.setTop(550);
        player1.getTransform().setScale(new Vector2(0.5, 0.5));
        player1.getTransform().setPosition(new Vector2(100, 650));
        playerOneController = player1.addComponent(PlayerController.class);

        GameObject player2 = new GameObject("PlayerTwo");
        PictureGraphic playerGraphic2 = player2.addComponent(PictureGraphic.class);
        playerGraphic2.setPicturePath("images/player1.png");
        playerGraphic2.setWidth(50);
        playerGraphic2.setHeight(75);
        playerGraphic2.setLeft(480);
        playerGraphic2.setTop(550);
        player2.getTransform().setScale(new Vector2(0.5, 0.5));
        player2.getTransform().setPosition(new Vector2(600, 700));
        playerTwoController = player2.addComponent(PlayerController.class);

        ControllerView controllerView;
        RollDiceButtonController buttonController;
        PlayerMovement playerMovement;
        ADice currentDice;

        GameObject g2 = new GameObject("Wuerfel2");
        currentDice = g2.addComponent(ClassicDice.class);
        g2.getTransform().setPosition(new Vector2(800, 650));
        g2.getTransform().setScale(new Vector2(15, 15));

        GameObject rollButton2 = new GameObject("Roll_Button1");
        ButtonGraphic buttonG2 = rollButton2.addComponent(ButtonGraphic.class);
        buttonG2.setLabelText("Würfeln!");
        buttonG2.setWidth(300);
        buttonG2.setHeight(50);
        rollButton2.getTransform().setPosition(new Vector2(800, 600));
        buttonController = rollButton2.addComponent(RollDiceButtonController.class);
        buttonController.setControllableDice(currentDice);
        controllerView = rollButton2.addComponent(ControllerView.class);
        controllerView.setController(playerTwoController);

        GameObject g3 = new GameObject("Wuerfel1");
        currentDice = g3.addComponent(ClassicDice.class);
        g3.getTransform().setPosition(new Vector2(100, 650));
        g3.getTransform().setScale(new Vector2(15, 15));

        GameObject rollButton = new GameObject("Roll_Button2");
        ButtonGraphic buttonG = rollButton.addComponent(ButtonGraphic.class);
        buttonG.setLabelText("Würfeln!");
        buttonG.setWidth(300);
        buttonG.setHeight(50);
        rollButton.getTransform().setPosition(new Vector2(70, 600));
        buttonController = rollButton.addComponent(RollDiceButtonController.class);
        buttonController.setControllableDice(currentDice);
        //controllerView = rollButton.addComponent(ControllerView.class);
        //controllerView.setController(playerOneController);

        GameObject rollAmountText = new GameObject("Roll2_Amount", rollButton);
        rollAmountText.getTransform().setPosition(new Vector2(70, 0));
        InputGraphic inputG = rollAmountText.addComponent(InputGraphic.class);
        inputG.setWidth(50);
        inputG.setText("1");
        inputG.setFontSize(16);
        inputG.setBold(true);

        GameObject movementPlayerOne = new GameObject("Player1_Movement");
        movementPlayerOne.addComponent(PlayerMovement.class);
        controllerView = movementPlayerOne.addComponent(ControllerView.class);
        controllerView.setController(playerOneController);

        GameObject movementPlayerTwo = new GameObject("Player2_Movement");
        movementPlayerTwo.addComponent(PlayerMovement.class);
        controllerView = movementPlayerTwo.addComponent(ControllerView.class);
        controllerView.setController(playerTwoController);

        GameObject controllerSocket = new GameObject("ControllerSocket");
        controllerSocket.addComponent(ControllerSocket.class);
        
        LinkedList<AbstractManager> _managers = new LinkedList<>();

        //Pre-initialization
        JavaFXRenderer renderer = new JavaFXRenderer();
        renderer.add(new PictureGraphicJavaFXRenderer());
        renderer.add(new LabelGraphicJavaFXRenderer());
        renderer.add(new ButtonGraphicJavaFXRenderer());
        renderer.add(new InputGraphicJavaFXRenderer());
        RenderManager.getInstance().addRenderer(renderer);

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

        InputManager.getInstance().addInputHandler(new MouseJavaFXHandler());
        InputManager.getInstance().addInputHandler(new ButtonJavaFXHandler());
        InputManager.getInstance().addInputHandler(new InputJavaFXHandler());
        InputManager.getInstance().addInputHandler(new MouseServerHandler());
        InputManager.getInstance().addInputHandler(new ButtonServerHandler());
        InputManager.getInstance().addInputHandler(new InputServerHandler());

        //Adding the managers.
        _managers.add(RenderManager.getInstance());
        _managers.add(DiceManager.getInstance());
        _managers.add(InputManager.getInstance());
        _managers.add(ControllerManager.getInstance());

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
