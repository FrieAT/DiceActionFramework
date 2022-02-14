package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Controller.Components.AbstractController;
import DAF.Controller.Components.IController;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;

public class ReadyButtonComponent extends AbstractComponent implements IInputListener {

    private boolean _ready;

    private IController _controller;

    @Override
    public void start() {

        InputManager.getInstance().add(ButtonInputEvent.class, this);

        this._controller = this.getGameObject().getComponentInParent(AbstractController.class);
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

        if(ready) {
            PlayerSaysComponent playerSays = this._controller.getGameObject().getComponentInChildren(PlayerSaysComponent.class);
            playerSays.showPlayerSays(false);
        }
    }

    public boolean isReady() {
        return _ready;
    }
}
