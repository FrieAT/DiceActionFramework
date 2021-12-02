package DAF.Event;

public class KeyboardInputEvent extends AInputEvent
{
    private KeyState _state;

    private int _keycode;

    public KeyboardInputEvent(KeyState state, int keycode)
    {
        this._state = state;
        this._keycode = keycode;
    }

    public String getType() { return "keyboard"; }

    public int getKeycode() { return this._keycode; }

    public KeyState getKeyState() { return this._state; }
}