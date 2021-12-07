import java.util.ArrayDeque;

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
