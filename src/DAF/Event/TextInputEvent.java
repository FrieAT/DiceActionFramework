package DAF.Event;

import DAF.Renderer.Components.AGraphic;

public class TextInputEvent extends AInputEvent
{
    private AGraphic _source;

    public TextInputEvent(AGraphic source)
    {
        this._source = source;
    }

    @Override
    public String getType() { return "text"; }

    public AGraphic getSource() { return this._source; }
}