package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Dice.Components.ADice;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.GameObject;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;

public class PeekButtonComponent extends AbstractComponent implements IInputListener {

    DiceCupComponent _cup;

    @Override
    public void start() {

        this._cup = this.getGameObject().getComponentInParent(DiceCupComponent.class);
        if (this._cup == null)
            throw new NullPointerException("There is no correct DiceCupComponent reference.");

        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent)event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);
        if (buttonEvent.getSource() == buttonGraphic) {
            peek();
        }
    }

    public void peek() {
        // state peek
        if (this._cup.isClosed()) {
            this._cup.setCupState(2);
        }
        // state close cup
        else if (this._cup.isPeek()) {
            this._cup.setCupState(0);
        }
    }
}
