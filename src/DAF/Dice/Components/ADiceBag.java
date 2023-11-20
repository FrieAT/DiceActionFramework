package DAF.Dice.Components;

import java.util.ArrayList;

import DAF.GameObject;
import DAF.Dice.DiceManager;
import DAF.Math.Vector2;

public class ADiceBag extends ADice implements IDice {
    private ArrayList<ADice> dices;

    public ADiceBag () {
        this.dices = new ArrayList<>();
    }

    @Override
    public void roll() {

        for (ADice dice : dices)
            dice.roll();

        /*
        dices.get(0).roll();
        setNewPosition(dices.get(0), this.getGameObject().getTransform().getPosition());

        for (int i = 1; i < dices.size(); i++) {
            dices.get(i).roll();
            setNewPosition(dices.get(i), dices.get(i-1).getPosition());
        }

         */


    }

    @Override
    public void setTopFace(Face face) {
        for(ADice dice : this.getDices()) {
            dice.setTopFace(face);
        }
    }

    public <T extends ADice>
    boolean add(Class<T> dice) {
        GameObject go_dice = new GameObject("dice_" + dices.size() + 1, this.getGameObject());
        T diceComp = go_dice.addComponent(dice);

        return dices.add(diceComp);
    }
    
    public boolean add(GameObject dice) {
    	return dices.add(dice.getComponent(ADice.class));
    }

    public boolean remove(ADice dice) {
        return dices.remove(dice);
    }

    public ArrayList<ADice> getDices () {
        return this.dices;
    }

    @Override
    public void start() {
        DiceManager.getInstance().add(this);

        if (this.getTransform().getPosition().x < 200
                || this.getTransform().getPosition().x > 600) {
            int y = 0;
            for (ADice dice : dices) {
                //dice.start();
                dice.getTransform().setPosition(new Vector2(50, y));
                y += 30;
            }
        } else {
            int x = 0;
            for (ADice dice : dices) {
                //dice.start();
                dice.getTransform().setPosition(new Vector2(x, 50));
                x += 30;
            }
        }
    }

    public void setNewPosition(ADice dice, Vector2 previousPos) {
        dice.getTransform().setPosition(new Vector2(previousPos.x + 30, previousPos.y));
    }
}

