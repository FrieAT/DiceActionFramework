package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;

public class ToggleGuessPOTCDice extends AbstractComponent implements IInputListener {

    @Override
    public void start() {
        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent) event;
        ButtonGraphic buttonGraphic = this.getGameObject().getComponent(ButtonGraphic.class);
        if (buttonEvent.getSource() == buttonGraphic) {
            POTCDice dice = this.getGameObject().getComponentInParent(POTCDice.class);
            if (dice != null) {
                dice.setToggleState(!dice.isSelected());
            }
        }
    }
}
