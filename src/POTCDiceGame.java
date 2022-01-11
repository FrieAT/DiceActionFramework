import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
import DAF.Input.AInputHandler;
import DAF.Input.InputManager;
import DAF.Input.MouseJavaFXHandler;
import DAF.Input.MouseServerHandler;
import DAF.Math.Vector2;
import DAF.Renderer.RenderManager;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.LabelGraphic;
import DAF.Renderer.Components.PictureGraphic;
import DAF.Renderer.JavaFX.ButtonGraphicJavaFXRenderer;
import DAF.Renderer.JavaFX.JavaFXRenderer;
import DAF.Renderer.JavaFX.LabelGraphicJavaFXRenderer;
import DAF.Renderer.JavaFX.PictureGraphicJavaFXRenderer;
import DAF.Renderer.Server.ServerRenderer;
import DAF.Serializer.ASerializer;
import DAF.Serializer.JsonSerializer;
import DAF.Socket.HttpSocket.HttpResourceExistsException;
import DAF.Socket.HttpSocket.HttpServerSocket;
import DAF.Socket.HttpSocket.SocketServerException;
import DAF.Socket.HttpSocket.Resource.DirectoryResource;
import DAF.Socket.HttpSocket.Resource.GifFileResource;
import DAF.Socket.HttpSocket.Resource.HtmlFileResource;
import DAF.Socket.HttpSocket.Resource.HttpResource;
import DAF.Socket.HttpSocket.Resource.JpegFileResource;
import DAF.Socket.HttpSocket.Resource.JsonBufferResource;
import DAF.Socket.HttpSocket.Resource.PngFileResource;
import javafx.scene.transform.Transform;

public class POTCDiceGame {

    static ArrayList<GameObject> views = new ArrayList<>();

    public static void main(String[] args) {
        start();
    }


    public static void start() {
        setUpInGameScreenV2();
        init();
    }

    public static void setUpStartScreen() {

        GameObject mainMenuScreen = new GameObject("mainScreen");
        View view = mainMenuScreen.addComponent(View.class);

        view.addBackground("MenuBackground",
                "images/flying_dutchman.jpeg",
                800, 600,
                0, 0
        );
        view.addLabel("GameTitle",
                "Pirates of the Caribbean",
                300, 100,
                250, 0,
                80, true
        );
        view.addButton("StartGame",
                "Starrrt Game",
                340, 400,
                150, 80,
                0, 0,
                20,
                StartGameButtonController.class
        );

        views.add(mainMenuScreen);
    }

    public static void setUpInGameScreen() {
        GameObject inGameScreen = new GameObject("InGame");
        View view = inGameScreen.addComponent(View.class);
        GameObject obj;

        obj = view.addBackground("InGameBackground",
                "images/wooden_floor.jpg",
                800, 600,
                0, 0
        );
        obj.addComponent(ControllerSocket.class);

        obj = view.addDice("Dice_1",
                350, 475,
                POTCDiceBag.class
        );
        obj.addComponent(PlayerController.class);

        obj = view.addDice("Dice_2",
                25, 250,
                POTCDiceBag.class
        );
        obj.addComponent(PlayerController.class);

        obj = view.addDice("Dice_3",
                350, 25,
                POTCDiceBag.class
        );
        obj.addComponent(PlayerController.class);

        obj = view.addDice("Dice_4",
                675, 250,
                POTCDiceBag.class
        );
        obj.addComponent(PlayerController.class);

        views.add(inGameScreen);
    }

    public static void setUpInGameScreenV2() {
        GameObject inGameScreen = new GameObject("InGame");
        View view = inGameScreen.addComponent(View.class);
        ControllerView cw;
        GameObject obj;

        obj = view.addBackground("InGameBackground",
                "images/wooden_floor.jpg",
                800, 600,
                0, 0
        );
        obj.addComponent(ControllerSocket.class);

        obj = view.addDice("Dice_1",
                350, 450,
                POTCDiceBag.class
        );

        obj.addComponent(PlayerController.class);
        cw = obj.addComponent(ControllerView.class);
        cw.setController(1);

        obj = view.addCup("DiceCup_1",
                "Cup_1_open",
                "images/dice_cup_open.png",
                280, 470,
                64, 64,
                0,0,
                "Cup_1_closed",
                "images/dice_cup_closed.png",
                325, 470,
                64, 64,
                0, 0
        );

        obj = view.addDice("Dice_2",
                25, 250,
                POTCDiceBag.class
        );
        obj.addComponent(PlayerController.class);

        obj = view.addDice("Dice_3",
                350, 25,
                POTCDiceBag.class
        );
        obj.addComponent(PlayerController.class);

        obj = view.addDice("Dice_4",
                650, 250,
                POTCDiceBag.class
        );
        obj.addComponent(PlayerController.class);

        obj = view.addButton("Roll_Button_1",
                "Roll",
                400, 550,
                100, 50,
                0, 0,
                30,
                RollDiceButtonControllerV2.class
        );

        obj.getComponent(RollDiceButtonControllerV2.class).addDiceNames("Dice_1");
        obj.getComponent(RollDiceButtonControllerV2.class).addDiceCup("DiceCup_1");

        obj.addComponent(ControllerView.class).setController(1);

        obj = view.addButton("Collect_Button_1",
                "Collect",
                280, 550,
                100, 50,
                0, 0,
                30,
                CollectDiceButtonController.class
        );

        obj.getComponent(CollectDiceButtonController.class).addDiceNames("Dice_1");
        obj.getComponent(CollectDiceButtonController.class).addDiceCup("DiceCup_1");
        obj.addComponent(ControllerView.class).setController(1);

        obj = view.addButton("Roll_Button_2",
                "Roll",
                25, 310,
                100, 50,
                0, 0,
                30,
                RollDiceButtonControllerV2.class
        );
        obj.getComponent(RollDiceButtonControllerV2.class).addDiceNames("Dice_2");
        obj.addComponent(ControllerView.class).setController(2);

        obj = view.addButton("Roll_Button_3",
                "Roll",
                400, 25,
                100, 50,
                0, 0,
                30,
                RollDiceButtonControllerV2.class
        );
        obj.getComponent(RollDiceButtonControllerV2.class).addDiceNames("Dice_3");
        obj.addComponent(ControllerView.class).setController(3);

        obj = view.addButton("Roll_Button_4",
                "Roll",
                730, 310,
                100, 50,
                0, 0,
                30,
                RollDiceButtonControllerV2.class
        );
        obj.getComponent(RollDiceButtonControllerV2.class).addDiceNames("Dice_4");
        obj.addComponent(ControllerView.class).setController(4);
    }

    public static void init() {
        LinkedList<AbstractManager> _managers = new LinkedList<>();

        ASerializer jsonSerializer = new JsonSerializer();

        HttpServerSocket socket = new HttpServerSocket("localhost:1337");
        try {
            socket.bind();
            DirectoryResource dir = (DirectoryResource)socket.addResource(DirectoryResource.class, "/images", new File("images/"));
            dir.addResource(JpegFileResource.class);
            dir.addResource(PngFileResource.class);
            dir.addResource(GifFileResource.class);

            socket.addResource(HtmlFileResource.class, "/", new File("www/index.html"));

            socket.addResource(JsonBufferResource.class, ControllerSocket.apiFetchFrameForPlayer);
            socket.addResource(JsonBufferResource.class, ServerRenderer.apiFetchFrame);
            socket.addResource(JsonBufferResource.class, ServerRenderer.apiEvent);
            socket.addResource(JsonBufferResource.class, ControllerSocket.apiConfirmFrameForPlayer);
        }
        catch(SocketServerException|HttpResourceExistsException e) {
            throw new NullPointerException(e.getMessage());
        }

        ServerRenderer serverRenderer = new ServerRenderer();
        serverRenderer.setSerializer(jsonSerializer);
        serverRenderer.setSocket(socket);
        RenderManager.getInstance().addRenderer(serverRenderer);

        /*
        AInputHandler input = new MouseJavaFXHandler();
        InputManager.getInstance().addInputHandler(input);

         */
        InputManager.getInstance().addInputHandler(new MouseServerHandler());

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
