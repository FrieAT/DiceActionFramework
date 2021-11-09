class MouseInputEvent extends KeyboardInputEvent
{
    private Vector2 _origin;

    public MouseInputEvent(KeyState state, Vector2 origin, int keycode)
    {
        super(state, keycode);

        this._origin = origin;
    }

    @Override
    public String getType() { return "mouse"; }

    Vector2 getPosition() { return this._origin; }
}