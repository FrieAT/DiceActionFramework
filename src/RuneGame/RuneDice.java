package RuneGame;

import DAF.Dice.Components.*;

/**
 * 1: Bow man
 * 2: Shield Bearer
 * 3: Stairs
 * 4: Walls
 * 5: Stairs + Walls
 * 6: Joker (needs 2 for a custom choice)
 */

public class RuneDice extends ADice {
    enum Rune {
        UNKNOWN, // 0
        BOW_MAN, // 1
        SHIELD_BEARER, // 2
        STAIRS, // 3
        WALLS, // 4
        STAIRSANDWALLS, // 5
        JOKER, // 6
    }

    @Override
    public void start() {
        super.start();

        addCustomFaces();
        /*
        for (int i = 1; i <= 6; i++) {

            //FIXME: Replace faces with custom images.
            this.addFace(new Face(i, "images/classic_dice_"+(i)+".png"));
        }
        */
    }

    public Rune getTopFaceRune() {
        return Rune.values()[super.getTopFace().getValue()];
    }

    public void addCustomFaces() {
        this.addFace(new Face(1, "images/shield_bearer.png"));
        this.addFace(new Face(2, "images/archer.png"));
        this.addFace(new Face(3, "images/stairs.png"));
        this.addFace(new Face(4, "images/wall.png"));
        this.addFace(new Face(5, "images/wall_and_stairs.png"));
        this.addFace(new Face(6, "images/joker.png"));
    }
}
