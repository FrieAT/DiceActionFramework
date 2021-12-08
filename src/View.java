import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import DAF.AbstractManager;
import DAF.GameObject;
import DAF.Components.AbstractComponent;
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

    public <T extends AbstractComponent>
    GameObject addGraphic(String name, String path, double posX, double posY, int width, int height, int left, int top, Class<T>... components) {
        GameObject graphic = new GameObject(name);
        PictureGraphic bgImage = graphic.addComponent(PictureGraphic.class);
        bgImage.setPicturePath(path);
        bgImage.setWidth(width);
        bgImage.setHeight(height);
        bgImage.setLeft(left);
        bgImage.setTop(top);

        for (Class<T> c: components)
            graphic.addComponent(c);

        gameObjects.add(graphic);

        return graphic;
    }


}
