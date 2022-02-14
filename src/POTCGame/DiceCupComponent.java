package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Controller.Components.AbstractController;
import DAF.Controller.Components.ControllerView;
import DAF.Controller.Components.IController;
import DAF.Dice.Components.ADice;
import DAF.Dice.Components.ADiceBag;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.GameObject;
import DAF.Input.InputManager;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.PictureGraphic;

public class DiceCupComponent extends AbstractComponent {

    /**
     * Status
     * 0: closed
     * 1: open
     * 2: peek
     */
    private ADice _dice;
    private int _cupState;
    private PictureGraphic _closedCup;
    private PictureGraphic _openCup;
    private PictureGraphic _peekCup;

    @Override
    public void start() {

        this._dice = this.getGameObject().getComponentInChildren(POTCDiceBag.class);
        if (this._dice == null)
            throw new NullPointerException("There is no correct ADice reference.");

        this._closedCup.getGameObject().setEnabled(false);
        this._peekCup.getGameObject().setEnabled(false);

        setCupState(1);
    }

    @Override
    public void update() {
    }

    public void setClosedCup(PictureGraphic picture) {
        this._closedCup = picture;
    }

    public void setOpenCup(PictureGraphic picture) {
        this._openCup = picture;
    }

    public void setPeekCup(PictureGraphic picture) {
        this._peekCup = picture;
    }

    public void setCupState(int state) {
        getCupState().getGameObject().setEnabled(false);
        this._cupState = state;
        getCupState().getGameObject().setEnabled(true);

        switch (_cupState) {
            case 0:
                System.out.println("CupState " + _cupState);
                toggleVisibilityOfDices(false, false);
                break;
            case 1:
                System.out.println("CupState " + _cupState);
                toggleVisibilityOfDices(true, true);
                break;
            case 2:
                System.out.println("CupState " + _cupState);
                toggleVisibilityOfDices(true, false);
                break;
        }

    }

    public PictureGraphic getCupState() {
        switch (_cupState) {
            case 0: return _closedCup;
            case 1: return _openCup;
            case 2: return _peekCup;
        }
        return null;
    }

    public boolean isClosed() {
        return _cupState == 0;
    }

    public boolean isOpen() {
        return _cupState == 1;
    }

    public boolean isPeek() {
        return _cupState == 2;
    }

    public void toggleVisibilityOfDices(boolean visible, boolean publicVisible) {
        System.out.println("toggleVisibilityOfDices");
        ADiceBag diceBag = this.getGameObject().getComponentInChildren(ADiceBag.class);
        if(diceBag != null) {
            ControllerView diceView = diceBag.getGameObject().getComponent(ControllerView.class);
            IController controllerOwner = this.getGameObject().getComponent(AbstractController.class);
            if (diceView != null && controllerOwner != null) {
                diceView.setHideFromEveryone(!visible);
                diceView.setController(publicVisible ? null : controllerOwner);
            }
        }
    }
}
