package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Controller.Components.ControllerView;
import DAF.Dice.Components.ADice;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;

public class RollDiceButtonComponent extends AbstractComponent implements IInputListener {

    private ADice _dice;
    private boolean _diceHasRolled;

    @Override
    public void start() {

        this._dice = this.getGameObject().getParent().getComponent(POTCDiceBag.class);
        this._diceHasRolled = false;
        if (this._dice == null) {
            throw new NullPointerException("Please define a correct ADice component as a reference");
        }

        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent) event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);
        if (buttonEvent.getSource() == buttonGraphic) {
            rollDice();
        }
    }

    public void setControllableDice (ADice dice) {
        this._dice = dice;
    }

    public boolean diceHasRolled () {
        return _diceHasRolled;
    }

    public void rollDice() {
        this._dice.roll();
        this._diceHasRolled = true;
        this.getGameObject().setEnabled(false);
    }

}
