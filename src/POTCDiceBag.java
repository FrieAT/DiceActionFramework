import DAF.Dice.Components.ADice;
import DAF.Dice.Components.ADiceBag;
import DAF.Dice.Components.ClassicDice;
import DAF.GameObject;
import DAF.Math.Vector2;

public class POTCDiceBag extends ADiceBag {

    public POTCDiceBag () {

    }

    @Override
    public void start() {
        for (int i = 0; i < 5; i++) {
            this.add(ClassicDice.class);
        }
        super.start();
    }

    public int getHighest () {
        int highest = getDices().get(0).getTopFace().getValue();

        for (int i = 1; i < getDices().size(); i++) {
            if (getDices().get(i).getTopFace().getValue() > highest)
                highest = getDices().get(i).getTopFace().getValue();
        }
        return highest;
    }
}
