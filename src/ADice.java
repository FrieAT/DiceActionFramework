import java.util.ArrayList;

public abstract class ADice extends AbstractComponent {

    // use Object [Object, IGraphic]

    protected ArrayList<Face> diceFaces;
    protected final int MAX_FACES = 20;

    public ADice() {
        this.diceFaces = new ArrayList<>();
    }

    public ADice(ArrayList<Face> diceFaces) {
        this.diceFaces = diceFaces;
    }

    // returns dice sides and the graphics of its key
    public ArrayList<Face> getDiceFaces() {
        return this.diceFaces;
    }

    public boolean addFace(int value) {
        if (diceFaces.size() == 10)
            return false;

        diceFaces.add(new Face(value));
        return true;
    }

    public Face getFace(int idx) {
        return diceFaces.get(idx);
    }

    // returns an array of length 2 which contains the rolled result
    public ArrayList<Face> roll() {
        ArrayList<Face> faces = new ArrayList<>();
        faces.add(diceFaces.get((int) (Math.random() * diceFaces.size())));
        return faces;
    }

    @Override
    public String toString() {
        return diceFaces.toString();
    }
}
