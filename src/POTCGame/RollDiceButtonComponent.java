package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Controller.Components.ControllerView;
import DAF.Dice.Components.ADice;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.GameObject;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;

public class RollDiceButtonComponent extends AbstractComponent implements IInputListener {

    private ADice _dice;
    private boolean _rolled;

    private DiceCupComponent _cup;

    @Override
    public void start() {

        this._rolled = false;

        this._dice = this.getGameObject().getParent().getComponentInChildren(POTCDiceBag.class);
        if (this._dice == null) {
            throw new NullPointerException("Please define a correct ADice component as a reference");
        }

        this._cup = this.getGameObject().getParent().getComponent(DiceCupComponent.class);
        if (this._cup == null) {
            throw new NullPointerException("Please define a correct DiceCupComponent as a reference");
        }

        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent) event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);
        if (buttonEvent.getSource() == buttonGraphic) {
            rollDice();
            flipCup();
        }
    }

    public boolean hasRolled () {
        return _rolled;
    }

    public void setRollState(boolean state) {
        this._rolled = state;
    }

    public void rollDice() {
        this._dice.roll();
        setRollState(true);

        this.getGameObject().setEnabled(false);
    }

    // set closed
    public void flipCup() {
        this._cup.setCupState(0);
    }
}
