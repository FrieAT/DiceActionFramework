import java.util.ArrayList;
import java.util.Arrays;

public class RollDiceButtonController extends AbstractComponent implements IInputListener {

    private ArrayList<ADice> _dices = new ArrayList<>();
    
    public void addControllableDice(ADice newDice) {
        this._dices.add(newDice);
    }

    @Override
    public void start() {

        if(this._dices.size() == 0) {
            ArrayList<GameObject> dices = GameObject.findAll("Dice");

            //System.out.println(_dices);

            for (GameObject dice : dices) {
                if (dice == null) {
                    throw new NullPointerException("Can't find the dice to roll.");
                }

                ADice diceComponent = dice.getComponent(ADice.class);

                if (diceComponent == null) {
                    throw new NullPointerException("Given gameObject doesn't own a dice-component.");
                }

                this._dices.add(diceComponent);
            }
        }

        //this._dice = diceComponent;

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
                for (ADice dice : _dices)
                    dice.roll();
            }
        }
    }
    
}
