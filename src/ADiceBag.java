import java.util.ArrayList;

public class ADiceBag extends ADice implements IDice {
    private ArrayList<ADice> dices;

    public ADiceBag () {
        this.dices = new ArrayList<>();
    }

    @Override
    public void roll() {
        for (ADice dice : dices)
            dice.roll();
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
        for (ADice dice : dices) {
            dice.setGameObject(this.getGameObject());
            dice.start();
        }
    }

    @Override
    public void setGameObject(GameObject owner) {
        this.owner = owner;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (ADice dice : dices) {
            s.append(dice.toString()).append("\n");
        }
        return s.toString();
    }

}

