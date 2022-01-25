package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.LabelGraphic;

public class GuessDiceButtonComponent extends AbstractComponent implements IInputListener {

    private boolean _guessed;
    LabelGraphic _counter;

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
        System.out.println("Guessed: " + _counter.getLabelText());
    }

    public boolean hasGuessed() {
        return this._guessed;
    }

    public void setGuessState(boolean state) {
        this._guessed = state;
    }
}
