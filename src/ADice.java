import java.util.ArrayList;

public abstract class ADice extends AbstractComponent {

    /**
     *
     */
    protected ArrayList<Face> diceFaces;
    private Face currentFace;
    private final int MAX_FACES = 20;

    public ADice() {
        this.diceFaces = new ArrayList<>();
        this.currentFace = null;
    }

    // returns dice sides and the graphics of its key
    public Face getCurrentFace() {
        return this.currentFace;
    }

    public boolean addFace(Face face) {
        if (diceFaces.size() == MAX_FACES)
            return false;
        if (currentFace == null)
            currentFace = face;
        return diceFaces.add(face);
    }

    public boolean removeFace(Face face) {
        return diceFaces.remove(face);
    }

    public Face getFace(int idx) {
        return diceFaces.get(idx);
    }

    public void roll() {
        this.currentFace = diceFaces.get((int) (Math.random() * diceFaces.size()));
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
        PictureGraphic g = (PictureGraphic)this.getGameObject().getComponent(EComponentType.AGraphic);
        if(g != null) {
            g.setTop((g.getTop() + 1) % 50);
            g.setLeft((g.getLeft() - 1) % 50);
        }
    }
}
