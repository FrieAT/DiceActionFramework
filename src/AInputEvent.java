public abstract class AInputEvent
{
    protected int _controller;

    public String getType() { return "undefined"; }

    public int getControllerIndex() { return this._controller; }
}