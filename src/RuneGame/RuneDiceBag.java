package RuneGame;

import DAF.GameObject;
import DAF.Dice.Components.ADice;
import DAF.Dice.Components.ADiceBag;

public class RuneDiceBag extends ADiceBag {
    @Override
    public void start() {
        for(int i = 0; i < 6; i++) {
            this.add(RuneDice.class);
        }
        super.start();
    }

    public void resetReady() {
        for(ADice dice : this.getDices()) {
            RuneDice runeDice = (RuneDice)dice;
            if(runeDice == null) {
                continue;
            }

            runeDice.resetReady();
        }
    }
}