package POTCGame;

import DAF.Components.AbstractComponent;

public class GuessFieldComponent extends AbstractComponent {

    public int _selected;

    public int _count;

    @Override
    public void start() {
        this._selected = 1;
        this._count = 0;
    }



}
