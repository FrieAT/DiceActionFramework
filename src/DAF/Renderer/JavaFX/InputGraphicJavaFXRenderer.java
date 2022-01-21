package DAF.Renderer.JavaFX;

import java.util.HashMap;

import DAF.Event.TextInputEvent;
import DAF.Input.InputJavaFXHandler;
import DAF.Input.InputManager;
import DAF.Math.Vector2;
import DAF.Renderer.Components.AGraphic;
import DAF.Renderer.Components.InputGraphic;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InputGraphicJavaFXRenderer extends JavaFXRenderer {
    private static final Font _defaultFont = new Font(1);

    private HashMap<String, Image> _cachedImages; //TODO: Replace with own FlyweightAssetManager-class.

    private HashMap<Integer, TextField> _cachedNodes;

    public InputGraphicJavaFXRenderer() {
        super();

        this._cachedImages = new HashMap<>();

        this._cachedNodes = new HashMap<>();
    }

    @Override
    public Node renderNode(AGraphic g) {
        InputGraphic inputGraphic;
        try {
            inputGraphic = (InputGraphic)g;
        } catch(ClassCastException e) {
            return null;
        }

        Vector2 scale = g.getTransform().getScale();
        double rotation = g.getTransform().getRotation();
        int gameObjectId = g.getGameObject().getId();

        TextField textField = this._cachedNodes.get(gameObjectId);
        if(textField == null || !textField.getText().equals(inputGraphic.getText())) {
            textField = new TextField (inputGraphic.getText());
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    inputGraphic.setText(newValue);

                    InputJavaFXHandler inputHandler = InputManager.getInstance().getInputHandler(InputJavaFXHandler.class);
                    if (inputHandler != null) {
                        TextInputEvent event = new TextInputEvent(inputGraphic);
                        inputHandler.callSubscribers(event);
                    }
                }
            });
            this._cachedNodes.put(gameObjectId, textField);
        }

        textField.setPrefWidth(inputGraphic.getWidth());
        textField.setPrefHeight(inputGraphic.getHeight());

        FontWeight curWeight = (inputGraphic.getBold() ? FontWeight.BOLD : FontWeight.NORMAL);
        if(Math.abs(textField.getFont().getSize() - inputGraphic.getFontSize()) > 0.1
        || !textField.getFont().getStyle().equals(curWeight)) {
            textField.setFont(Font.font(_defaultFont.getFamily(), curWeight, inputGraphic.getFontSize()));
        }

        textField.setLayoutX(inputGraphic.getLeft());
        textField.setLayoutY(inputGraphic.getTop());
        textField.setScaleX(scale.x);
        textField.setScaleY(scale.y);
        textField.setRotate(rotation);

        if(inputGraphic.getGameObject().isEnabled()) {
            //FIXME: On macOS wrong width gets examined.
            if (textField.getWidth() > inputGraphic.getWidth()) {
                inputGraphic.setWidth((int) textField.getWidth());
            }

            //FIXME: On macOS wrong height gets examined.
            if (textField.getHeight() > inputGraphic.getHeight()) {
                inputGraphic.setHeight((int) textField.getHeight());
            }
        }

        return textField;
    }

    @Override
    public void render(AGraphic g) {
        throw new NullPointerException("Need a Window to render in it, can't be alone called.");
    }
}
