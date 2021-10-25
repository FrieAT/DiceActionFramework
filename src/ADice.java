import java.util.Arrays;

public abstract class ADice implements IComponent {

    // use Object [Object, IGraphic]

    protected Object[][] diceFaces;
    protected final int MAX_FACES = 10;

    public ADice() {
        this.diceFaces = new Object[0][2];
    }

    public ADice(Object[][] diceFaces) {
        this.diceFaces = diceFaces;
    }

    // returns dice sides and the graphics of its key
    public Object[][] getDiceFaces() {
        return this.diceFaces;
    }

    public boolean addFace(Object value, Object graphics) {
        if (diceFaces.length == 10)
            return false;
        Object[][] newDiceFaces = new Object[diceFaces.length + 1][2];

        if (diceFaces.length > 0) {
            for (int i = 0; i < diceFaces.length; i++) {
                newDiceFaces[i][0] = diceFaces[i][0];
                newDiceFaces[i][1] = diceFaces[i][1];
            }
        }

        newDiceFaces[diceFaces.length][0] = value;
        newDiceFaces[diceFaces.length][1] = graphics;
        this.diceFaces = newDiceFaces;
        return true;
    }

    public Object[] getFace(int idx) {
        return diceFaces[idx];
    }

    // returns an array of length 2 which contains the rolled result
    public Object[] roll() {
        return diceFaces[(int) (Math.random() * diceFaces.length)];
    }

    @Override
    public String toString() {
        return Arrays.deepToString(diceFaces);
    }


}
