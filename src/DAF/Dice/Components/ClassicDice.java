package DAF.Dice.Components;
public class ClassicDice extends ADice {

    @Override
    public void start() {
        super.start();
        for (int i = 1; i <= 6; i++) {
            this.addFace(new Face(i, "images/classic_dice_"+(i)+".png"));
        }
    }

}
