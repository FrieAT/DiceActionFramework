import java.util.ArrayList;

public class ADiceBag extends ADice implements IDice {
    private ArrayList<ADice> dices;

    public ADiceBag () {
        this.dices = new ArrayList<>();
    }

    @Override
    public void roll() {
        dices.get(0).roll();
        setNewPosition(dices.get(0), this.getGameObject().getTransform().getPosition());

        for (int i = 1; i < dices.size(); i++) {
            dices.get(i).roll();
            setNewPosition(dices.get(i), dices.get(i-1).getPosition());
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
        dices.get(0).start();
        setNewPosition(dices.get(0), this.getGameObject().getTransform().getPosition());

        for (int i = 1; i < dices.size(); i++) {
            dices.get(i).start();
            setNewPosition(dices.get(i), dices.get(i-1).getPosition());
        }
    }

    public void setNewPosition(ADice dice, Vector2 previousPos) {
        dice.setPosition(previousPos.x + 30, previousPos.y);
    }
}

