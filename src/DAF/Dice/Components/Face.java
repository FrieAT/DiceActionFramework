package DAF.Dice.Components;
import DAF.Renderer.Components.PictureGraphic;

public class Face {
    private int value;

    private String faceResPath;

    private PictureGraphic picture;

    public Face(int value, String faceResPath) {
        PictureGraphic picture = new PictureGraphic();
        picture.setPicturePath(faceResPath);
        picture.setWidth(20);
        picture.setHeight(20);

        this.value = value;
        this.picture = picture;
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

    public void setPictureGraphic(PictureGraphic g) {
        this.picture = g;
    }

    public PictureGraphic getPictureGraphic() {
        return this.picture;
    }

}
