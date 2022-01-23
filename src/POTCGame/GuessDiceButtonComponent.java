package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Controller.Components.ControllerView;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;

public class GuessDiceButtonComponent extends AbstractComponent implements IInputListener {

    private int _playerIndex;
    @Override
    public void start() {
        _playerIndex = this.getGameObject().getComponent(ControllerView.class).getController().getPlayerNo();
        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent)event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);
        if (buttonEvent.getSource() == buttonGraphic) {
            guess();
        }
    }

    public void guess() {
        System.out.println("guessed");
        this.getGameObject().setEnabled(false);
    }

    public boolean isPlayersTurn(int idx) {
        return this._playerIndex == idx;
    }
}
