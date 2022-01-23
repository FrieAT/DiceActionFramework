package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;

public class ReadyButtonComponent extends AbstractComponent implements IInputListener {

    private boolean _ready;

    @Override
    public void start() {
        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent)event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);
        if (buttonEvent.getSource() == buttonGraphic) {
            setReady(true);
        }
    }

    public void setReady(boolean ready) {
        this._ready = ready;
        this.getGameObject().setEnabled(!ready);
    }

    public boolean isReady() {
        return _ready;
    }
}
