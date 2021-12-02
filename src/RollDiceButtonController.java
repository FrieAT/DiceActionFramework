import java.util.ArrayDeque;

import DAF.GameObject;
import DAF.Components.AbstractComponent;
import DAF.Dice.Components.ADice;
import DAF.Event.AInputEvent;
import DAF.Event.IInputListener;
import DAF.Event.KeyState;
import DAF.Event.MouseInputEvent;
import DAF.Input.InputManager;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;

public class RollDiceButtonController extends AbstractComponent implements IInputListener {
    private ADice _dice;
    
    @Override
    public void start() {
        GameObject dice = GameObject.find("Wuerfel");
        if(dice == null) {
            throw new NullPointerException("Can't find the dice to roll.");
        }
        
        ADice diceComponent = dice.getComponent(ADice.class);
        if(diceComponent == null) {
            throw new NullPointerException("Given gameObject doesn't own a dice-component.");
        }

        this._dice = diceComponent;

        InputManager.getInstance().add(MouseInputEvent.class, this);
    }
    
    public void onInput(AInputEvent event) {
        MouseInputEvent mouseEvent = (MouseInputEvent)event;
        if(mouseEvent != null && mouseEvent.getKeyState() == KeyState.Up) {
            Vector2 ownPosition = this.getTransform().getPosition();
            Vector2 mousePosition = mouseEvent.getPosition();
            Vector2 way = new Vector2(
                Math.abs(ownPosition.x - mousePosition.x), 
                Math.abs(ownPosition.y - mousePosition.y)
            );

            ButtonGraphic buttonGraphic = this.getGameObject().getComponent(ButtonGraphic.class);
            double xDist = buttonGraphic.getWidth()/2.0;
            double yDist = buttonGraphic.getHeight()/2.0;
            if(buttonGraphic != null && way.x < xDist && way.y < yDist) {
                //If mouse position is near on button position
                this._dice.roll();
            }
        }
    }
    
}
