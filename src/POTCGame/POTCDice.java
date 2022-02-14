package POTCGame;

import DAF.Controller.Components.AbstractController;
import DAF.Controller.Components.ControllerView;
import DAF.Controller.Components.IController;
import DAF.Dice.Components.ADice;
import DAF.Dice.Components.Face;
import DAF.GameObject;
import DAF.Renderer.Components.ButtonGraphic;

import java.util.Arrays;

public class POTCDice extends ADice {

    private boolean _selected;

    @Override
    public void start() {
        super.start();

        addCustomFaces();
    }

    public void addCustomFaces() {
        for (int i = 1; i <= 6; i++) {
            this.addFace(new Face(i, "images/classic_dice_"+(i)+".png"));
        }
    }

    @Override
    public GameObject addFace(Face face) {
        GameObject faceObject = super.addFace(face);

        GameObject faceClickableObject = new GameObject("Clickable", faceObject);

        ButtonGraphic button = faceClickableObject.addComponent(ButtonGraphic.class);
        button.bindWidthToParent(true);
        button.bindHeightToParent(true);
        button.setLabelText(" ");
        button.setWebBgColor("rgba(0, 0, 0, 0.0)");
        button.setBorderRadius(5);

        IController controller = this.getGameObject().getComponentInParent(AbstractController.class);
        faceClickableObject.addComponent(ControllerView.class).setController(controller.getPlayerNo());
        faceClickableObject.addComponent(ToggleGuessPOTCDice.class);

        return faceObject;
    }

    public void setToggleState(boolean state) {

        this._selected = state;

        ButtonGraphic faceGraphic = getTopFace().getPictureGraphic().getGameObject().getComponentInChildren(ButtonGraphic.class);

        if (faceGraphic != null) {
            if (isSelected()) {
                POTCDiceBag diceBag = this.getGameObject().getComponentInParent(POTCDiceBag.class);
                GuessFieldComponent guessField = this.getGameObject().getComponentInParent(AbstractController.class).getGameObject().getComponentInChildren(GuessFieldComponent.class);
                if (diceBag != null) {
                    diceBag.deactivateGuesses();
                    guessField.setGuessDice(this.getTopFace().getValue());
                }
                //System.out.println(Arrays.toString(guessField.getGuess()));

                faceGraphic.setWebBgColor("rgba(145, 17, 6, 0.5)");
            } else {
                faceGraphic.setWebBgColor("rgba(255, 255, 255, 0.0)");
            }
        }

    }

    public boolean isSelected() {
        return this._selected;
    }

}
