import DAF.Components.AbstractComponent;
import DAF.GameObject;
import DAF.Math.Vector2;
import DAF.Renderer.Components.PictureGraphic;

import java.util.ArrayList;

public class DiceCup extends AbstractComponent {

    private PictureGraphic openCup;
    private PictureGraphic closedCup;

    // true: open, false: closed
    private boolean isOpen;

    public DiceCup() {
        this.openCup = null;
        this.closedCup = null;
        this.isOpen = false;
    }

    public void setOpenCup(String name, String path, double posX, double posY, int width, int height, int left, int top) {
        GameObject goCup = new GameObject(name);
        openCup = goCup.addComponent(PictureGraphic.class);
        openCup.setPicturePath(path);
        openCup.setWidth(width);
        openCup.setHeight(height);
        openCup.setLeft(left);
        openCup.setTop(top);

        goCup.getTransform().setPosition(new Vector2(posX, posY));

    }

    public void setClosedCup(String name, String path, double posX, double posY, int width, int height, int left, int top) {
        GameObject goCup = new GameObject(name);
        closedCup = goCup.addComponent(PictureGraphic.class);
        closedCup.setPicturePath(path);
        closedCup.setWidth(width);
        closedCup.setHeight(height);
        closedCup.setLeft(left);
        closedCup.setTop(top);

        goCup.getTransform().setPosition(new Vector2(posX, posY));
    }

    public PictureGraphic getCupSide() {
        if (isOpen)
            return openCup;
        return closedCup;
    }

    /**
     * open:   if cupStatus is true
     * closed: if cupStatus is false
     */
    public void setCupStatus(boolean cupStatus) {
        this.isOpen = cupStatus;
        openCup.getGameObject().setEnabled(cupStatus);
        closedCup.getGameObject().setEnabled(!cupStatus);

    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void start() {
        openCup.getGameObject().setEnabled(isOpen);
        closedCup.getGameObject().setEnabled(!isOpen);
    }

}
