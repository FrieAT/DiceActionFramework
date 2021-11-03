
import java.util.Arrays;
import java.util.LinkedList;

public class Demo {

    public static void main (String[] args) {
        GameObject g1 = new GameObject(1, "Backgammon");

        g1.addComponent(new ClassicDice());
        g1.addComponent(new ClassicDice());
        //System.out.println(classicDice.getDiceSides());

        ADice test_dice = new ClassicDice();
        System.out.println(Arrays.deepToString(test_dice.getDiceFaces()));

        for (int i = 0; i < 20; i++) {
            System.out.println(Arrays.toString(test_dice.roll()));
        }

        System.out.println(g1.getComponents());

        GameObject background = new GameObject(2, "MainBackground");

        PictureGraphic bgImage = new PictureGraphic();
        bgImage.setPicturePath("images/gameboard.png");
        bgImage.setHeight(320);
        bgImage.setWidth(320);
        bgImage.setLeft(0);
        bgImage.setTop(0);
        background.addComponent(bgImage);

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
