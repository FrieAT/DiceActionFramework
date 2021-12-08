import Socket.HttpSocket.HttpResourceExistsException;
import Socket.HttpSocket.HttpServerSocket;
import Socket.HttpSocket.Resource.*;
import Socket.HttpSocket.SocketServerException;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

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
        GameObject obj;

        obj = view.addBackground("InGameBackground",
                "images/wooden_floor.jpg",
                800, 600,
                0, 0
        );
        obj.addComponent(ControllerSocket.class);

        obj = view.addBackground("Cup_1",
                "images/wooden_floor.png",
                64, 64,
                0,0);
        obj = view.addDice("Dice_1",
                350, 450,
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
