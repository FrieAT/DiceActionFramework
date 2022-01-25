package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.LabelGraphic;

public class PlusButtonComponent extends AbstractComponent implements IInputListener {

    LabelGraphic _counter;
    @Override
    public void start() {
        _counter = this.getGameObject().getParent().getComponentInChildren(LabelGraphic.class);
        if (this._counter == null) {
            throw new NullPointerException("There is no LabelGraphic referenced with GuessFieldComponent.");
        }
        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent)event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);
        if (buttonEvent.getSource() == buttonGraphic) {
            int cnt = Integer.parseInt(_counter.getLabelText()) + 1;
            _counter.setLabelText(String.valueOf(cnt));
        }
    }
}
