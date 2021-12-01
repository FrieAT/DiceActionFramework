public class ClassicDice extends ADice {

    public ClassicDice() {
        for (int i = 1; i <= 6; i++) {
            this.addFace(new Face(i, "images/classic_dice_"+(i)+".png"));
        }
    }

    @Override
    public void start() {
        super.start();
    }

}
