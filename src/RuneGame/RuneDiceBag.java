package RuneGame;

import java.util.ArrayList;

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

    public ArrayList<RuneDice> getRuneDices() {
        ArrayList<RuneDice> dices = new ArrayList<>();
        for(ADice d : this.getDices()) {
            RuneDice rd = (RuneDice)d;
            if(rd != null) {
                dices.add(rd);
            }
        }
        return dices;
    }

    public int getRollCount() {
        int maxRolls = 0;
        for(ADice dice : this.getDices()) {
            RuneDice runeDice = (RuneDice)dice;
            if(runeDice.getRollCount() > maxRolls) {
                maxRolls = runeDice.getRollCount();
            }
        }
        return maxRolls;
    }
}