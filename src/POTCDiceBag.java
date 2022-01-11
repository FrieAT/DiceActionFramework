import DAF.Dice.Components.ADiceBag;
import DAF.Dice.Components.ClassicDice;
import DAF.GameObject;
import DAF.Math.Vector2;

public class POTCDiceBag extends ADiceBag {

    private DiceCup diceCup;

    public POTCDiceBag () {

    }


    private void setDiceCup() {
        GameObject diceCup = new GameObject("diceCup");
        DiceCup dcImage = diceCup.addComponent(DiceCup.class);
        dcImage.setOpenCup("dc_open",
                "images/dice_cup_open.png",
                280, 470,
                64, 64,
                0, 0);
        dcImage.setClosedCup("dc_closed",
                "images/dice_cup_open.png",
                280, 470,
                64, 64,
                0, 0);
        diceCup.getTransform().setPosition(new Vector2(280, 470));
        this.diceCup = diceCup.getComponent(DiceCup.class);
    }



    @Override
    public void start() {
        for (int i = 0; i < 5; i++) {
            this.add(ClassicDice.class);
        }
        super.start();
    }
}
