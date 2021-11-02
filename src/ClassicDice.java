
public class ClassicDice extends ADice {

    public ClassicDice() {
        for (int i = 0; i < 6; i++)
            this.addFace(i + 1, Integer.toString(i + 1));
    }

    @Override
    public Object[][] getDiceFaces() {
        return diceFaces;
    }

}
