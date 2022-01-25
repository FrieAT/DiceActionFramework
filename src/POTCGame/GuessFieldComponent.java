package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.LabelGraphic;

public class GuessFieldComponent extends AbstractComponent {

    private int _count;

    @Override
    public void start() {
        this._count = 0;
        //_counterLabel = GameFactory.createText(String.valueOf(_count), 20, new Vector2(0, -75));

    }

    public void increment() {
        this._count++;
    }

    public void decrement() {
        this._count--;
    }

    public int getCount() {
        return this._count;
    }

}
