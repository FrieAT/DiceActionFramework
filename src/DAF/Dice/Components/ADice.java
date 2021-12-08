package DAF.Dice.Components;
import java.util.ArrayList;

import DAF.GameObject;
import DAF.Components.AbstractComponent;
import DAF.Dice.DiceManager;

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
        
        GameObject faceObject = new GameObject("Face", this.getGameObject());
        faceObject.addComponent(face.getPictureGraphic());
        faceObject.setEnabled(false);

        if (topFace == null)
            this.setTopFace(face);
        
        return diceFaces.add(face);
    }

    public void setTopFace(Face face) {
        //Deactivate old face.
        if(topFace != null) {
            topFace.getPictureGraphic().getGameObject().setEnabled(false);
        }
        
        topFace = face;
        
        //Activate old face.
        if(topFace != null) {
            topFace.getPictureGraphic().getGameObject().setEnabled(true);
        }
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