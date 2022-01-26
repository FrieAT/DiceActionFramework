package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.LabelGraphic;

public class GuessFieldComponent extends AbstractComponent {

    private int _guessDice;
    private int _guessCount;

    @Override
    public void start() {
        this._guessDice = 0;
        this._guessCount = 0;
    }

    public int getGuessDice() {
        return this._guessDice;
    }

    public int getGuessCount() {
        return this._guessCount;
    }

    public int[] getGuess() {
        return new int[]{_guessDice, _guessCount};
    }

    public void setGuess(int guessDice, int guessCount) {
        this._guessDice = guessDice;
        this._guessCount = guessCount;
    }
}
