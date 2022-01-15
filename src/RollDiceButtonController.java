import DAF.Components.AbstractComponent;
import DAF.Dice.Components.ADice;
import DAF.Event.*;
import DAF.Input.InputManager;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;

public class RollDiceButtonController extends AbstractComponent implements IInputListener {
    private ADice _dice;
    
    public void setControllableDice(ADice dice) {
        this._dice = dice;
    }

    @Override
    public void start() {
        if(this._dice == null) {
            throw new NullPointerException("Please define a correct ADice component as a reference.");
        }

        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }
    
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent)event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);

        if(buttonGraphic != null && buttonEvent.getSource() == buttonGraphic) {
            //If mouse position is near on button position
            this._dice.roll();
        }
    }
    
}
