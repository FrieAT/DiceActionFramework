package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Controller.Components.ControllerView;
import DAF.Controller.Components.IController;
import DAF.Controller.Components.PlayerController;
import DAF.Dice.Components.ADice;
import DAF.GameObject;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.LabelGraphic;
import DAF.Renderer.Components.PictureGraphic;

import java.awt.*;

public class GameFactory {

    public static IController createPlayer(int maxPlayers) {
        double r = 550 / 2.0;
        double dAlpha = 360.0 / maxPlayers;

        GameObject playerObject = new GameObject("Player");
        IController controller = playerObject.addComponent(PlayerController.class);

        ADice diceBag = playerObject.addComponent(POTCDiceBag.class);
        diceBag.getGameObject()
                .addComponent(ControllerView.class)
                .setController(controller);

        addDiceCup(playerObject);

        //addDiceCup(playerObject);

        addReadyButton(playerObject)
                .addComponent(ControllerView.class)
                .setController(controller);

        addRollButton(playerObject)
                .addComponent(ControllerView.class)
                .setController(controller);

        addPeekButton(playerObject)
                .addComponent(ControllerView.class)
                .setController(controller);


        addGuessField(playerObject, controller)
                .addComponent(ControllerView.class)
                .setController(controller);

        //Setze Spieler in einem Spielerkreis von {maxPlayers}-Spieler
        double playerAlpha = Math.toRadians(controller.getPlayerNo() * dAlpha);
        Vector2 circlePosition = new Vector2(r * Math.cos(playerAlpha), (r * Math.sin(playerAlpha)));
        playerObject.getTransform().setPosition(circlePosition);

        return controller;
    }

    public static LabelGraphic createTextHeading(String text, Vector2 position) {
        LabelGraphic label = createText(text, 32, position);
        label.setBold(true);
        return label;
    }

    public static LabelGraphic createText(String text, int fontSize, Vector2 position) {
        GameObject labelObject = new GameObject("Text");
        labelObject.getTransform().setPosition(position);
        LabelGraphic label = labelObject.addComponent(LabelGraphic.class);
        label.setLabelText(text);
        label.setWebColor("black");
        label.setFontSize(fontSize);
        return label;
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

    public static PictureGraphic createPicture(String path, Vector2 position) {
        GameObject pictureObject = new GameObject("Picture");
        pictureObject.getTransform().setPosition(position);
        PictureGraphic picture = pictureObject.addComponent(PictureGraphic.class);
        picture.setPicturePath(path);
        picture.setWidth(64);
        picture.setHeight(64);

        return picture;
    }

    public static GameObject addGuessField(GameObject forObject, IController controller) {
        GameObject guessObject = new GameObject("GuessField");
        guessObject.setParent(forObject);

        GuessFieldComponent guessField = guessObject.addComponent(GuessFieldComponent.class);
        LabelGraphic label = createText("0", 20, new Vector2(60, 93));
        label.getGameObject().setParent(guessObject);
        label.setWebBgColor("rgba(38, 38, 38, 0.5)");
        label.setWebColor("white");

        label.getGameObject()
                .addComponent(ControllerView.class)
                .setController(controller);
        addPlusButton(guessObject)
                .addComponent(ControllerView.class)
                .setController(controller);
        addMinusButton(guessObject)
                .addComponent(ControllerView.class)
                .setController(controller);
        addGuessButton(guessObject)
                .addComponent(ControllerView.class)
                .setController(controller);

        guessObject.setEnabled(false);
        return guessObject;
    }

    public static GameObject addPlusButton(GameObject forObject) {
        ButtonGraphic button = createButton(" + ", new Vector2(80, 90), PlusButtonComponent.class);
        button.getGameObject().setParent(forObject);
        button.setFontSize(20);
        button.setBorderRadius(10);
        button.setWebBgColor("rgba(106, 138, 94)");
        button.setWebColor("white");

        return button.getGameObject();
    }

    public static GameObject addMinusButton(GameObject forObject) {
        ButtonGraphic button = createButton(" - ", new Vector2(35, 90), MinusButtonComponent.class);
        button.getGameObject().setParent(forObject);
        button.setFontSize(20);
        button.setBorderRadius(10);
        button.setWebBgColor("rgba(145, 101, 100)");
        button.setWebColor("white");

        return button.getGameObject();
    }

    public static GameObject addGuessButton(GameObject forObject) {
        ButtonGraphic button = createButton("Guess", new Vector2(-60, 90), GuessDiceButtonComponent.class);
        button.getGameObject().setParent(forObject);
        button.setFontSize(20);
        button.setBorderRadius(5);
        button.setWebBgColor("rgba(191, 175, 147)");
        return button.getGameObject();
    }

    public static GameObject addReadyButton(GameObject forObject) {
        ButtonGraphic button = createButton("Ready", new Vector2(-20, 90), ReadyButtonComponent.class);
        button.getGameObject().setParent(forObject);
        button.setFontSize(20);
        button.setBorderRadius(5);
        button.setWebBgColor("rgba(153, 184, 140)");

        return button.getGameObject();
    }

    public static GameObject addRollButton(GameObject forObject) {
        ButtonGraphic button = createButton("Roll", new Vector2(-5, 90), RollDiceButtonComponent.class);
        button.getGameObject().setParent(forObject);
        button.setFontSize(20);
        button.setBorderRadius(5);
        button.setWebBgColor("rgba(191, 175, 147)");
        button.getGameObject().setEnabled(false);
        return button.getGameObject();
    }

    public static GameObject addPeekButton(GameObject forObject) {
        ButtonGraphic button = createButton(" Peek ", new Vector2(-60, 10), PeekButtonComponent.class);
        button.getGameObject().setParent(forObject);
        button.setFontSize(20);
        button.setBorderRadius(5);
        button.setWebBgColor("rgba(191, 175, 147)");
        //button.getGameObject().setEnabled(true);
        return button.getGameObject();
    }

    public static GameObject addDiceCup(GameObject forObject) {
        GameObject cupObject = new GameObject("Cup");
        cupObject.getTransform().setPosition(new Vector2(35, -20));
        cupObject.setParent(forObject);

        DiceCupComponent cup = forObject.addComponent(DiceCupComponent.class);
        PictureGraphic cupState;

        cupState = createPicture("images/dice_cup_closed.png", new Vector2(35, -20));
        cupState.getGameObject().setParent(cup.getGameObject());
        cup.setClosedCup(cupState);

        cupState = createPicture("images/dice_cup_open.png", new Vector2(35, -20));
        cupState.getGameObject().setParent(cup.getGameObject());
        cup.setOpenCup(cupState);

        cupState = createPicture("images/dice_cup_peek.png", new Vector2(35, -20));
        cupState.getGameObject().setParent(cup.getGameObject());
        cup.setPeekCup(cupState);

        return cup.getGameObject();
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

    @SafeVarargs
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