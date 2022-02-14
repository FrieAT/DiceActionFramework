package DAF.Dice.Components;
import DAF.Renderer.Components.PictureGraphic;

public class Face {
    private int value;

    private String faceResPath;

    private PictureGraphic picture;

    public Face(int value, String faceResPath) {
        PictureGraphic picture = new PictureGraphic();
        picture.setPicturePath(faceResPath);

        this.value = value;
        this.picture = picture;
        this.faceResPath = faceResPath;

        setFaceDimensions(32, 32);
    }

    public Face(int value, PictureGraphic g) {
        this.value = value;
        this.picture = g;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getPath() {
        return this.faceResPath;
    }

    public void setFaceResPath(String path) {
        this.picture.setPicturePath(path);
        this.faceResPath = faceResPath;
    }

    public void setFaceDimensions(int val) {
        this.picture.setWidth(val);
        this.picture.setHeight(val);
    }

    public void setFaceDimensions(int height, int width) {
        this.picture.setHeight(height);
        this.picture.setWidth(width);
    }

    public void setPictureGraphic(PictureGraphic g) {
        this.picture = g;
    }

    public PictureGraphic getPictureGraphic() {
        return this.picture;
    }

}
