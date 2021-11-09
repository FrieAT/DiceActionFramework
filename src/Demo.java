
import java.util.Arrays;
import java.util.LinkedList;

public class Demo {

    public static void main (String[] args) {
        GameObject g1 = new GameObject("Backgammon");

        g1.addComponent(new ClassicDice());
        g1.addComponent(new ClassicDice());
        //System.out.println(classicDice.getDiceSides());

        GameObject g2 = new GameObject("BackgammonDice");
        g2.addComponent(new ClassicDice());
        g2.addComponent(new ClassicDice());
        g2.addComponent(new PictureGraphic());

        
        GameObject background = new GameObject("MainBackground");

        
        PictureGraphic bgImage = new PictureGraphic();
        bgImage.setPicturePath("images/gameboard.png");
        bgImage.setWidth(1024);
        bgImage.setHeight(768);
        bgImage.setLeft(0);
        bgImage.setTop(0);
        background.addComponent(bgImage);
        //background.addComponent(new StupidComponent());
        
        GameObject gameName = new GameObject("GameName");
        LabelGraphic gameNameLabel = new LabelGraphic();
        gameNameLabel.setLeft(400);
        gameNameLabel.setTop(0);
        gameNameLabel.setFontSize(20);
        gameNameLabel.setBold(true);
        gameNameLabel.setLabelText("Man, Don't Get Angry");
        gameName.addComponent(gameNameLabel);

        GameObject gameName2 = new GameObject("GameName(2)");
        LabelGraphic gameNameLabel2 = new LabelGraphic();
        gameNameLabel.setLeft(400);
        gameNameLabel.setTop(50);
        gameNameLabel.setFontSize(20);
        gameNameLabel.setBold(true);
        gameNameLabel.setLabelText("Okay?");
        gameName2.addComponent(gameNameLabel2);
        
        LinkedList<AbstractManager> _managers = new LinkedList<>();
        
        //Pre-initialization.
        AGraphicRenderer renderer = new JavaFXRenderer();
        renderer.add(new PictureGraphicJavaFXRenderer());
        renderer.add(new LabelGraphicJavaFXRenderer());
        RenderManager.getInstance().setRenderer(renderer);

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
        }
    }
}
