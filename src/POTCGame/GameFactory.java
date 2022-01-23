package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Controller.Components.ControllerView;
import DAF.Controller.Components.IController;
import DAF.Controller.Components.PlayerController;
import DAF.GameObject;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.LabelGraphic;
import DAF.Renderer.Components.PictureGraphic;

import java.awt.*;

public class GameFactory {
    public static LabelGraphic createTextHeading(String text, Vector2 position) {
        LabelGraphic label = createText(text, position);
        label.setFontSize(32);
        label.setBold(true);
        return label;
    }

    public static LabelGraphic createText(String text, Vector2 position) {
        GameObject labelObject = new GameObject("Text");
        labelObject.getTransform().setPosition(position);
        LabelGraphic label = labelObject.addComponent(LabelGraphic.class);
        label.setLabelText(text);
        label.setWebColor("black");
        return label;
    }

    public static IController createPlayer(int maxPlayers) {
        double r = 550 / 2.0;
        double dAlpha = 360.0 / maxPlayers;

        GameObject playerObject = new GameObject("Player");
        IController controller = playerObject.addComponent(PlayerController.class);
        playerObject.addComponent(POTCDiceBag.class);

        addReadyButton(playerObject)
                .addComponent(ControllerView.class)
                .setController(controller);

        addRollButton(playerObject)
                .addComponent(ControllerView.class)
                .setController(controller);

        addGuessButton(playerObject)
                .addComponent(ControllerView.class)
                .setController(controller);


        //Setze Spieler in einem Spielerkreis von {maxPlayers}-Spieler
        double playerAlpha = Math.toRadians(controller.getPlayerNo() * dAlpha);
        Vector2 circlePosition = new Vector2(r * Math.cos(playerAlpha), (r * Math.sin(playerAlpha)));
        playerObject.getTransform().setPosition(circlePosition);

        return controller;
    }

    public static GameObject addReadyButton(GameObject forObject) {
        ButtonGraphic button = createButton("Ready", new Vector2(-20, 20), ReadyButtonComponent.class);
        button.getGameObject().setParent(forObject);
        return button.getGameObject();
    }

    public static GameObject addRollButton(GameObject forObject) {
        ButtonGraphic button = createButton("Roll", new Vector2(20, 20),RollDiceButtonComponent.class);
        button.getGameObject().setParent(forObject);
        button.getGameObject().setEnabled(false);
        return button.getGameObject();
    }

    public static GameObject addGuessButton(GameObject forObject) {
        ButtonGraphic button = createButton("Guess", new Vector2(-20, 40), GuessDiceButtonComponent.class);
        button.getGameObject().setParent(forObject);
        button.getGameObject().setEnabled(false);
        return button.getGameObject();
    }

    public static PictureGraphic createBackground(String path, Vector2 position) {
        GameObject pictureObject = new GameObject("Background");
        pictureObject.getTransform().setPosition(position);
        PictureGraphic background = pictureObject.addComponent(PictureGraphic.class);
        background.setPicturePath(path);
        background.setWidth(1024);
        background.setHeight(768);
        return background;
    }

    @SafeVarargs
    public static <T extends AbstractComponent> ButtonGraphic createButton(String text, Vector2 position, Class<T> ...components) {
        GameObject buttonObject = new GameObject("Button");
        buttonObject.getTransform().setPosition(position);
        ButtonGraphic button = buttonObject.addComponent(ButtonGraphic.class);
        button.setLabelText(text);

        for (Class<T> c : components) {
            buttonObject.addComponent(c);
        }

        return button;
    }

    public static <T extends AbstractComponent> ButtonGraphic createButton(String text, Vector2 position, String path, Class<T> ...components) {
        GameObject buttonObject = new GameObject("Button");
        buttonObject.getTransform().setPosition(position);
        ButtonGraphic button = buttonObject.addComponent(ButtonGraphic.class);
        button.setLabelText(text);
        button.setPicturePath(path);

        for (Class<T> c : components) {
            buttonObject.addComponent(c);
        }

        return button;
    }
}
