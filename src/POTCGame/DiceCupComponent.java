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

public class DiceCupComponent extends AbstractComponent implements IInputListener {

    /**
     * Status
     * 0: closed
     * 1: open
     * 2: peek
     */
    private int _cupStatus;

    @Override
    public void start() {
        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent)event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);
        if (buttonEvent.getSource() == buttonGraphic) {
            switch (_cupStatus) {
                case 0:
                    ;
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        }
    }

    public void setCupStatus(int status) {
        this._cupStatus = status;
    }
}
