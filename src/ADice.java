import java.util.ArrayList;

public abstract class ADice extends AbstractComponent implements IDice {

    protected ArrayList<Face> diceFaces;
    private Face topFace;
    private final int MAX_FACES = 20;

    public ADice() {
        this.diceFaces = new ArrayList<>();
        this.topFace = null;
    }

    // returns dice sides and the graphics of its key
    public Face getTopFace() {
        return this.topFace;
    }

    public boolean addFace(Face face) {
        if (diceFaces.size() == MAX_FACES)
            return false;
        if (topFace == null)
            topFace = face;
        return diceFaces.add(face);
    }

    public boolean removeFace(Face face) {
        return diceFaces.remove(face);
    }

    public Face getFace(int idx) {
        return diceFaces.get(idx);
    }

    @Override
    public void roll() {
        this.topFace = diceFaces.get((int) (Math.random() * diceFaces.size()));
    }

    @Override
    public String toString() {
        return diceFaces.toString();
    }

    @Override
    public void start() {
        this.type = EComponentType.ADice;

        DiceManager.getInstance().add(this);
    }

    @Override
    public void update() {
        for (Face face: diceFaces) {
            if (face.equals(topFace))
                face.setEnabled(true);
            face.setEnabled(false);
        }
    }
}
