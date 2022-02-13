package RuneGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import DAF.GameObject;
import DAF.Dice.Components.ADice;
import DAF.Dice.Components.ADiceBag;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.PictureGraphic;
import RuneGame.RuneDice.Rune;

public class RuneDiceBag extends ADiceBag {
    private ADice _lastMarked = null;
    
    @Override
    public void start() {
        for(int i = 0; i < 6; i++) {
            this.add(RuneDice.class);
        }
        int x = -50;
        for (ADice dice : getDices()) {
            dice.getTransform().setPosition(new Vector2(x, 50));
            x += 35;
        }
        super.start();
    }

    public void markRuneDice(RuneDice toMark, boolean attack, boolean defense) {
        PictureGraphic button;
        if(_lastMarked != null) {
            button = _lastMarked.getTopFace().getPictureGraphic();
            button.setWebBgColor("white");
        }
        button = toMark.getTopFace().getPictureGraphic();
        if(attack && !defense) {
            button.setWebBgColor("#a83232");
        } else {
            button.setWebBgColor("#143d96");
        }
        _lastMarked = toMark;
    }

    public RuneDice getRuneDice(Rune element) {
        for(ADice dice : getDices()) {
            RuneDice runeDice = (RuneDice)dice;
            if(runeDice != null && runeDice.getTopFaceRune() == element) {
                return runeDice;
            }
        }
        return null;
    }

    public List<RuneDice> getRuneDices(Rune element) {
        LinkedList<RuneDice> runeDices = new LinkedList<>();
        for(ADice dice : getDices()) {
            RuneDice runeDice = (RuneDice)dice;
            if(runeDice != null && runeDice.getTopFaceRune() == element) {
                runeDices.push(runeDice);
            }
        }
        return runeDices;
    }

    public void resetReady() {
        this._lastMarked = null;
        for(ADice dice : this.getDices()) {
            RuneDice runeDice = (RuneDice)dice;
            if(runeDice == null) {
                continue;
            }

            runeDice.resetReady();
        }
    }

    public void resetReadyAndFace() {
        this._lastMarked = null;
        for(ADice dice : this.getDices()) {
            RuneDice runeDice = (RuneDice)dice;
            if(runeDice == null) {
                continue;
            }

            runeDice.resetReadyAndFace();
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