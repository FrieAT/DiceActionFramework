import Socket.HttpSocket.HttpResourceExistsException;
import Socket.HttpSocket.HttpServerSocket;
import Socket.HttpSocket.Resource.*;
import Socket.HttpSocket.SocketServerException;

import java.io.File;
import java.util.LinkedList;

public class POTCDiceGame {

    static GameObject background = new GameObject("MainBackground");

    public static void main(String[] args) {
        start();
    }


    public static void start() {
        setUpStartScreen();
    }

    public static void setUpStartScreen() {
        PictureGraphic bgImage = background.addComponent(PictureGraphic.class);
        bgImage.setPicturePath("images/flying_dutchman.jpeg");
        bgImage.setWidth(800);
        bgImage.setHeight(600);
        bgImage.setLeft(0);
        bgImage.setTop(0);

        GameObject gameName = new GameObject("GameName");
        LabelGraphic gameNameLabel = gameName.addComponent(LabelGraphic.class);
        gameNameLabel.setLeft(250);
        gameNameLabel.setTop(0);
        gameNameLabel.setFontSize(80);
        gameNameLabel.setBold(true);
        gameNameLabel.setLabelText("Pirates of the Caribbean");
        gameName.getTransform().setPosition(new Vector2(300, 100));

        GameObject button_startGame = new GameObject("Start_New_Game");
        ButtonGraphic bg_startGame = button_startGame.addComponent(ButtonGraphic.class);
        bg_startGame.setLabelText("Starrrt Game");
        bg_startGame.setWidth(500);
        bg_startGame.setHeight(80);
        bg_startGame.setFontSize(20);
        button_startGame.getTransform().setPosition(new Vector2(340, 400));
        button_startGame.addComponent(StartGameButtonController.class);


        /**
         * Roll Button Test
         */
        GameObject diceBag = new GameObject("Wuerfel");
        diceBag.addComponent(POTCDiceBag.class);
        diceBag.getTransform().setPosition(new Vector2(350, 540));
        diceBag.getTransform().setScale(new Vector2(20, 20));

        GameObject rollButton = new GameObject("Roll_Button");
        ButtonGraphic buttonG = rollButton.addComponent(ButtonGraphic.class);
        buttonG.setLabelText("Roll");
        buttonG.setWidth(300);
        buttonG.setHeight(50);
        rollButton.getTransform().setPosition(new Vector2(350, 500));
        rollButton.addComponent(RollDiceButtonController.class);

        /*
        GameObject g1 = new GameObject("Wuerfel");
        g1.addComponent(ClassicDice.class);
        g1.getTransform().setPosition(new Vector2(50, 50));
        g1.getTransform().setScale(new Vector2(50, 50));

         */

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
