package POTCGame;

import DAF.Dice.Components.ADiceBag;
import DAF.Dice.Components.ClassicDice;

public class POTCDiceBag extends ADiceBag {

    @Override
    public void start() {
        for (int i = 0; i < 5; i++) {
            this.add(ClassicDice.class);
        }

        super.start();
    }
}
