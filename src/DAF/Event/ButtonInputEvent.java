package DAF.Event;

import DAF.Renderer.Components.ButtonGraphic;

public class ButtonInputEvent extends KeyboardInputEvent {
    private ButtonGraphic _source;

    public ButtonInputEvent(KeyState state, ButtonGraphic source, int keycode, int controller) {
        super(state, keycode, controller);

        this._source = source;
    }

    @Override
    public String getType() {
        return "button";
    }

    public ButtonGraphic getSource() {
        return this._source;
    }
}