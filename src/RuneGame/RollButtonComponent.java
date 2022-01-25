package RuneGame;

import DAF.Components.AbstractComponent;
import DAF.Dice.Components.ADice;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;

public class RollButtonComponent extends AbstractComponent implements IInputListener {

    private ADice _dice;

    private boolean _rolled;

    @Override
    public void start() {
        this._dice = this.getGameObject().getParent().getComponent(RuneDiceBag.class);
        if (this._dice == null) {
            throw new NullPointerException("There is no referenced ADice for this player.");
        }
        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent)event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);
        if(buttonEvent.getSource() == buttonGraphic) {
            rollDice();
        }
    }

    public void rollDice() {
        this._dice.roll();
        setRollState(true);
    }

    public void setRollState(boolean state) {
        this._rolled = state;
    }

    public boolean hasRolled() {
        return this._rolled;
    }
}