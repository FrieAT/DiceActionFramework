package DAF.Dice.Components;
import java.util.ArrayList;

import DAF.GameObject;
import DAF.Components.AbstractComponent;
import DAF.Dice.DiceManager;
import DAF.Math.Vector2;

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

    public GameObject addFace(Face face) {
        if (diceFaces.size() == MAX_FACES)
            return null;
        
        GameObject faceObject = new GameObject("Face", this.getGameObject());
        faceObject.addComponent(face.getPictureGraphic());
        faceObject.setEnabled(false);

        if (topFace == null)
            this.setTopFace(face);
        diceFaces.add(face);

        return faceObject;
    }

    public void setTopFace(Face face) {
        //Deactivate old face.
        if(topFace != null) {
            topFace.getPictureGraphic().getGameObject().setEnabled(false);
        }
        
        topFace = face;
        
        //Activate new face.
        if(topFace != null) {
            topFace.getPictureGraphic().getGameObject().setEnabled(true);
        }
    }

    public void setPosition(double x, double y) {
        this.getTopFace().getPictureGraphic().getTransform().setPosition(new Vector2(x, y));
    }

    public Vector2 getPosition() {
        return this.getTopFace().getPictureGraphic().getTransform().getPosition();
    }

    public boolean removeFace(Face face) {
        //TODO: Delete GameObject.
        return diceFaces.remove(face);
    }

    public Face getFace(int idx) {
        return diceFaces.get(idx);
    }

    @Override
    public void roll() {
        Face rolledFace = diceFaces.get((int) (Math.random() * diceFaces.size()));
        this.setTopFace(rolledFace);

    }

    @Override
    public String toString() {
        return diceFaces.toString();
    }

    @Override
    public void start() {
        DiceManager.getInstance().add(this);
    }
}
