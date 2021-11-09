public class Face extends GameObject {
    private int value;

    private Face() {

    }

    public Face(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
