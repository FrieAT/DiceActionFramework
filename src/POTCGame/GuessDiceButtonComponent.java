package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Dice.Components.ADice;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.LabelGraphic;

import java.util.Arrays;

public class GuessDiceButtonComponent extends AbstractComponent implements IInputListener {

    private boolean _guessed;
    private LabelGraphic _counter;

    @Override
    public void start() {
        this._counter = this.getGameObject().getParent().getComponentInChildren(LabelGraphic.class);
        this._guessed = false;
        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent)event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);
        if (buttonEvent.getSource() == buttonGraphic) {
            guess();
            setGuessState(true);
        }
    }

    public void guess() {
        //this.getGameObject().getComponentInParent(POTCDiceBag.class);
        GuessFieldComponent guessField = this.getGameObject().getComponentInParent(GuessFieldComponent.class);
        guessField.setGuessCount(Integer.parseInt(_counter.getLabelText()));
        System.out.println("Guessed: " + Arrays.toString(guessField.getGuess()));
    }

    public boolean hasGuessed() {
        return this._guessed;
    }

    public void setGuessState(boolean state) {
        this._guessed = state;
    }
}
