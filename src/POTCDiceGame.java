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

        view.addBackground("InGameBackground",
                "images/wooden_floor.jpg",
                800, 600,
                0, 0
        );

        view.addDice("Dice_1",
                350, 475,
                POTCDiceBag.class
        );

        view.addDice("Dice_2",
                25, 250,
                POTCDiceBag.class
        );

        view.addDice("Dice_3",
                350, 25,
                POTCDiceBag.class
        );

        view.addDice("Dice_4",
                675, 250,
                POTCDiceBag.class
        );

        view.addButton("Roll_Button",
                "Roll",
                400, 300,
                100, 50,
                0, 0,
                30,
                RollDiceButtonController.class
        );

        views.add(inGameScreen);
    }

    public static void setUpInGameScreenV2() {
        GameObject inGameScreen = new GameObject("InGame");
        View view = inGameScreen.addComponent(View.class);

        view.addBackground("InGameBackground",
                "images/wooden_floor.jpg",
                800, 600,
                0, 0
        );

        view.addDice("Dice_1",
                350, 450,
                POTCDiceBag.class
        );

        view.addDice("Dice_2",
                25, 250,
                POTCDiceBag.class
        );

        view.addDice("Dice_3",
                350, 25,
                POTCDiceBag.class
        );

        view.addDice("Dice_4",
                650, 250,
                POTCDiceBag.class
        );

        RollDiceButtonControllerV2 bc1 = new RollDiceButtonControllerV2("Dice_1");
        RollDiceButtonControllerV2 bc2 = new RollDiceButtonControllerV2("Dice_2");
        RollDiceButtonControllerV2 bc3 = new RollDiceButtonControllerV2("Dice_3");
        RollDiceButtonControllerV2 bc4 = new RollDiceButtonControllerV2("Dice_4");

        view.addButton("Roll_Button",
                "Roll",
                400, 550,
                100, 50,
                0, 0,
                30,
                bc1
        );

        view.addButton("Roll_Button",
                "Roll",
                25, 310,
                100, 50,
                0, 0,
                30,
                bc2
        );

        view.addButton("Roll_Button",
                "Roll",
                400, 25,
                100, 50,
                0, 0,
                30,
                bc3
        );

        view.addButton("Roll_Button",
                "Roll",
                730, 310,
                100, 50,
                0, 0,
                30,
                bc4
        );

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

            socket.addResource(JsonBufferResource.class, "/api/fetchFrame.json");

            socket.addResource(JsonBufferResource.class, "/api/event.json");
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

        //Initialization of the managers.
        for(AbstractManager m : _managers) {
            m.init();
        }

        //Initialize all game objects with their components.
        GameObject.startAll();

        //Game loop
        while(true) {
            //TODO: If game quits or exception happens?

            for(AbstractManager m : _managers) {
                m.update();
            }

            GameObject.updateAll();

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
