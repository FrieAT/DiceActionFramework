package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;

public class GuessDiceButtonComponent extends AbstractComponent implements IInputListener {

    private boolean _guessed;

    @Override
    public void start() {
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
        System.out.println("guessed");
    }

    public boolean hasGuessed() {
        return this._guessed;
    }

    public void setGuessState(boolean state) {
        this._guessed = state;
    }
}
