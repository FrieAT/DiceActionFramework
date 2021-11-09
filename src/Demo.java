
import java.util.Arrays;
import java.util.LinkedList;

public class Demo {

    public static void main (String[] args) {
        GameObject g1 = new GameObject("Backgammon");

        g1.addComponent(ClassicDice.class);
        g1.addComponent(ClassicDice.class);
        //System.out.println(classicDice.getDiceSides());

        GameObject g2 = new GameObject("BackgammonDice");
        ADiceBag bag = new ADiceBag();
        bag.add(new ClassicDice());
        bag.add(new ClassicDice());
        PictureGraphic faceImage = g2.addComponent(PictureGraphic.class);
        faceImage.setPicturePath("images/classic_dice_1.png");
        faceImage.setWidth(20);
        faceImage.setHeight(20);
        faceImage.setLeft(0);
        faceImage.setTop(0);
        System.out.println(g2.getComponents());

        
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

        GameObject player1 = new GameObject("PlayerOne");
        PictureGraphic playerGraphic = player1.addComponent(PictureGraphic.class);
        playerGraphic.setPicturePath("images/player2.png");
        playerGraphic.setWidth(50);
        playerGraphic.setHeight(75);
        playerGraphic.setLeft(480);
        playerGraphic.setTop(550);
        player1.getTransform().setScale(new Vector2(2, 2));
        player1.addComponent(PlayerController.class);
        
        LinkedList<AbstractManager> _managers = new LinkedList<>();
        
        //Pre-initialization.
        JavaFXRenderer renderer = new JavaFXRenderer();
        renderer.add(new PictureGraphicJavaFXRenderer());
        renderer.add(new LabelGraphicJavaFXRenderer());
        RenderManager.getInstance().setRenderer(renderer);

        AInputHandler input = new MouseJavaFXHandler();
        InputManager.getInstance().addInputHandler(input);

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
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
