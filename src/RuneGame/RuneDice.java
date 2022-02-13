package RuneGame;

import DAF.GameObject;
import DAF.Controller.Components.AbstractController;
import DAF.Controller.Components.ControllerView;
import DAF.Controller.Components.IController;
import DAF.Dice.Components.*;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.PictureGraphic;
import javafx.scene.control.Button;

/**
 * 1: Bow man
 * 2: Shield Bearer
 * 3: Stairs
 * 4: Walls
 * 5: Stairs + Walls
 * 6: Joker (needs 2 for a custom choice)
 */

public class RuneDice extends ADice {
    private static final int MAX_ROLLS = 3;
    
    enum Rune {
        UNKNOWN, // 0
        BOW_MAN, // 1
        SHIELD_BEARER, // 2
        STAIRS, // 3
        WALLS, // 4
        STAIRSANDWALLS, // 5
        JOKER, // 6
    }

    private GameObject _clickableDice;

    private boolean _ready = false;

    private boolean _persistent = false;

    private int _rollCount = 0;

    @Override
    public void start() {
        super.start();

        addCustomFaces();
        /*
        for (int i = 1; i <= 6; i++) {

            //FIXME: Replace faces with custom images.
            this.addFace(new Face(i, "images/classic_dice_"+(i)+".png"));
        }
        */
    }

    @Override
    public void roll() {
        this._rollCount++;
        
        if(this._ready) {
            this._persistent = true;
            return;
        }

        this._rollCount++;

        super.roll();
    }

    public void resetReady() {
        this._ready = false;
        this._persistent = false;
        this._rollCount = 0;


    }

    public void resetReadyAndFace() {
        this.resetReady();

        int i = 0;
        while(getFace(i) != null) {
            setTopFace(getFace(i));
            setReady(false);
            i++;
        }
        //setTopFace(null);
    }

    public boolean isReady() {
        return this._ready;
    }

    public void setReady(boolean state) {
        if(!this._persistent) {
            this._ready = state;
        }

        ButtonGraphic faceGraphic = getTopFace().getPictureGraphic().getGameObject().getComponentInChildren(ButtonGraphic.class);
        if(faceGraphic != null) {
            if(isReady()) {
                faceGraphic.setWebBgColor("rgba(179, 23, 11, 0.2)");
            } else {
                faceGraphic.setWebBgColor("rgba(255, 255, 255, 0.0)");
            }
        }
    }

    public int getRollCount() {
        return this._rollCount;
    }

    public Rune getTopFaceRune() {
        if(getTopFace() == null) {
            return Rune.UNKNOWN;
        }

        return Rune.values()[getTopFace().getValue()];
    }

    @Override
    public GameObject addFace(Face face) {
        GameObject faceObject = super.addFace(face);

        GameObject faceClickableObject = new GameObject("Clickable", faceObject);
        //PictureGraphic picture = faceObject.getComponent(PictureGraphic.class);
        ButtonGraphic button = faceClickableObject.addComponent(ButtonGraphic.class);
        button.bindWidthToParent(true);
        button.bindHeightToParent(true);
        button.setLabelText(" ");
        //button.setWebColor("rgba(0, 0, 0, 0.0)");
        button.setWebBgColor("rgba(0, 0, 0, 0.0)");

        IController controller = this.getGameObject().getComponentInParent(AbstractController.class);
        faceClickableObject.addComponent(ControllerView.class).setController(controller.getPlayerNo());

        faceClickableObject.addComponent(ToggleReadyRuneDice.class);

        return faceObject;
    }

    public void addCustomFaces() {
        this.addFace(new Face(1, "images/archer.png"));
        this.addFace(new Face(2, "images/shield_bearer.png"));
        this.addFace(new Face(3, "images/stairs.png"));
        this.addFace(new Face(4, "images/wall.png"));
        this.addFace(new Face(5, "images/wall_and_stairs.png"));
        this.addFace(new Face(6, "images/joker.png"));
        //this.setTopFace(null);
    }
}
