import DAF.Components.AbstractComponent;
import DAF.GameObject;
import DAF.Math.Vector2;
import DAF.Renderer.Components.PictureGraphic;

public class DiceCup extends AbstractComponent {

    private PictureGraphic openCup;
    private PictureGraphic closedCup;
    private PictureGraphic peekCup;

    /**
     * 0: open
     * 1: closed
     * 2: peek
     */
    private int cupStatus;

    public DiceCup() {
        this.openCup = null;
        this.closedCup = null;
        this.cupStatus = 0;
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

    public void setPeekCup(String name, String path, double posX, double posY, int width, int height, int left, int top) {
        GameObject goCup = new GameObject(name);
        peekCup = goCup.addComponent(PictureGraphic.class);
        peekCup.setPicturePath(path);
        peekCup.setWidth(width);
        peekCup.setHeight(height);
        peekCup.setLeft(left);
        peekCup.setTop(top);

        goCup.getTransform().setPosition(new Vector2(posX, posY));
    }

    public PictureGraphic getCupSide() {
        if (cupStatus == 0)
            return openCup;
        else if (cupStatus == 1)
            return closedCup;
        else
            return peekCup;
    }

    /**
     * open:   if cupStatus is true
     * closed: if cupStatus is false
     */
    public void setCupStatus(int cupStatus) {
        getCupSide().getGameObject().setEnabled(false);
        this.cupStatus = cupStatus;
        getCupSide().getGameObject().setEnabled(true);

    }

    public void setCupStatus(String cupStatus) {
        getCupSide().getGameObject().setEnabled(false);
        switch (cupStatus) {
            case "open":
                this.cupStatus = 0;
                break;
            case "closed":
                this.cupStatus = 1;
                break;
            case "peek":
                this.cupStatus = 2;
                break;
        }
        getCupSide().getGameObject().setEnabled(true);
    }

    public boolean isOpen() {
        return this.cupStatus == 0;
    }

    public boolean isClosed() {
        return this.cupStatus == 1;
    }

    public boolean isPeek() {
        return this.cupStatus == 2;
    }

    public int getCupStatus() {
        return this.cupStatus;
    }

    @Override
    public void start() {
        openCup.getGameObject().setEnabled(true);
        closedCup.getGameObject().setEnabled(false);
        peekCup.getGameObject().setEnabled(false);
    }

    @Override
    public void update() {

    }

}
