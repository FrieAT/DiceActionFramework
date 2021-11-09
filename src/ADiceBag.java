import java.util.ArrayList;

public class ADiceBag extends ADice {
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
    public void setGameObject(GameObject owner) {

    }
}
