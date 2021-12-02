package DAF.Dice.Components;
import java.util.ArrayList;

import DAF.Math.Vector2;

public class ADiceBag extends ADice implements IDice {
    private ArrayList<ADice> dices;

    public ADiceBag () {
        this.dices = new ArrayList<>();
    }

    @Override
    public void roll() {
        int i = 50;
        for (ADice dice : dices) {
            dice.roll();
            dice.getTopFace().getPictureGraphic().getTransform().setPosition(new Vector2(i, 50));
            i += 30;
        }
    }

    public boolean add(ADice dice) {
        return dices.add(dice);
    }

    public boolean remove(ADice dice) {
        return dices.remove(dice);
    }

    public ArrayList<ADice> getDices () {
        return this.dices;
    }

    @Override
    public void start() {
        int i = 50;
        for (ADice dice : dices) {
            dice.start();
            dice.getTopFace().getPictureGraphic().getTransform().setPosition(new Vector2(i, 50));
            i += 30;
        }
    }
}
