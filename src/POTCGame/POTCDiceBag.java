package POTCGame;

import DAF.Dice.Components.ADice;
import DAF.Dice.Components.ADiceBag;
import DAF.Dice.Components.ClassicDice;
import DAF.Math.Vector2;

public class POTCDiceBag extends ADiceBag {

    private int _guessDice;

    @Override
    public void start() {
        for (int i = 0; i < 5; i++) {
            this.add(POTCDice.class);
        }

        // Code for placing the dices in the area wanted
        int x = -50;
        for (ADice dice : getDices()) {
            dice.getTransform().setPosition(new Vector2(x, 50));
            x += 30;
        }
        super.start();
    }

    public void deactivateGuesses() {
        for (ADice dice : getDices()) {
            ((POTCDice) dice).setToggleState(false);
        }
    }

    public void setGuessDice(int value) {
        this._guessDice = value;
    }

    public int getGuessDice() {

        return this._guessDice;
        /*
        for (ADice dice : this.getDices()) {
            if (((POTCDice) dice).isGuess()) {
                return dice.getTopFace().getValue();
            }
        }
        return -1;

         */
    }
}
