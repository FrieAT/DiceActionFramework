package POTCGame;

import DAF.Components.AbstractComponent;
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
    private int _cupState;
    private PictureGraphic _closedCup;
    private PictureGraphic _openCup;
    private PictureGraphic _peekCup;

    @Override
    public void start() {
        this._cupState = 1;
        this._closedCup.getGameObject().setEnabled(false);
        this._peekCup.getGameObject().setEnabled(false);
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
    }

    public PictureGraphic getCupState() {
        switch (_cupState) {
            case 0: return _closedCup;
            case 1: return _openCup;
            case 2: return _peekCup;
        }
        return null;
    }
}
