import java.util.ArrayList;

public class View extends AbstractComponent {

    ArrayList<GameObject> gameObjects;

    public View() {
        gameObjects = new ArrayList<>();
    }

    public <T extends AbstractComponent>
    GameObject addBackground(String name, String path, int width, int height, int left, int top, Class<T>... components) {
        GameObject background = new GameObject(name);
        PictureGraphic bgImage = background.addComponent(PictureGraphic.class);
        bgImage.setPicturePath(path);
        bgImage.setWidth(width);
        bgImage.setHeight(height);
        bgImage.setLeft(left);
        bgImage.setTop(top);

        for (Class<T> c: components)
            background.addComponent(c);

        gameObjects.add(background);

        return background;
    }

    public <T extends AbstractComponent>
    GameObject addLabel(String name, String text, double posX, double posY, int left, int top, int fontSize, boolean bold, Class<T>... components) {
        GameObject label = new GameObject(name);
        LabelGraphic lGraphic = label.addComponent(LabelGraphic.class);
        lGraphic.setLabelText(text);
        lGraphic.setLeft(left);
        lGraphic.setTop(top);
        lGraphic.setFontSize(fontSize);
        lGraphic.setBold(bold);
        label.getTransform().setPosition(new Vector2(posX, posY));

        for (Class<T> c: components)
            label.addComponent(c);

        gameObjects.add(label);

        return label;
    }

    public <T extends AbstractComponent>
    GameObject addButton(String name, String text, double posX, double posY, int width, int height, int left, int top, int fontSize, Class<T>... components) {
        GameObject button = new GameObject(name);
        ButtonGraphic bGraphic = button.addComponent(ButtonGraphic.class);
        bGraphic.setLabelText(text);
        bGraphic.setWidth(width);
        bGraphic.setHeight(height);
        bGraphic.setFontSize(fontSize);
        button.getTransform().setPosition(new Vector2(posX, posY));

        for (Class<T> c : components)
            button.addComponent(c);

        gameObjects.add(button);

        return button;
    }

    public GameObject addButton(String name, String text, double posX, double posY, int width, int height, int left, int top, int fontSize, AbstractComponent... components) {
        GameObject button = new GameObject(name);
        ButtonGraphic bGraphic = button.addComponent(ButtonGraphic.class);
        bGraphic.setLabelText(text);
        bGraphic.setWidth(width);
        bGraphic.setHeight(height);
        bGraphic.setFontSize(fontSize);
        button.getTransform().setPosition(new Vector2(posX, posY));

        for (AbstractComponent c : components)
            button.addComponent(c);

        gameObjects.add(button);

        return button;
    }

    public <T extends AbstractComponent>
    GameObject addDice(String name, double posX, double posY, Class<T>... components) {
        GameObject dice = new GameObject(name);
        dice.getTransform().setPosition(new Vector2(posX, posY));

        for (Class<T> c : components)
            dice.addComponent(c);

        gameObjects.add(dice);

        return dice;
    }

}
