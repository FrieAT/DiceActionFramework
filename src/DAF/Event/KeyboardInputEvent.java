package DAF.Event;

public class KeyboardInputEvent extends AInputEvent
{
    private KeyState _state;

    private int _keycode;

    public KeyboardInputEvent(KeyState state, int keycode, int controller)
    {
        this._state = state;
        this._keycode = keycode;
        this._controller = controller;
    }

    public String getType() { return "keyboard"; }

    public int getKeycode() { return this._keycode; }

    public KeyState getKeyState() { return this._state; }
}