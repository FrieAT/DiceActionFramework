import DAF.Components.AbstractComponent;
import DAF.Dice.Components.ADice;
import DAF.Event.*;
import DAF.GameObject;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.InputGraphic;

public class RollDiceButtonController extends AbstractComponent implements IInputListener {
    private ADice _dice;

    private int _rollAmountDice;

    private java.util.Timer _timer;
    
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

        this._rollAmountDice = 1;

        if(buttonGraphic != null && buttonEvent.getSource() == buttonGraphic) {
            for(GameObject child : this.getGameObject().getChildren()) {
                InputGraphic input = child.getComponent(InputGraphic.class);
                if(input != null) {
                    this._rollAmountDice = Integer.parseInt(input.getText());
                }
            }

            if(this._timer != null) {
                this._timer.cancel();
            }

            this._timer = new java.util.Timer();
            this._timer.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            _dice.roll();

                            if(--_rollAmountDice == 0) {
                                _timer.cancel();
                                _timer = null;
                            }
                        }
                    },
                    250, 250
            );
        }
    }
    
}
