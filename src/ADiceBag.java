import java.util.ArrayList;

public class ADiceBag extends ADice {
    private ArrayList<ADice> dices;

    public ADiceBag () {
        this.dices = new ArrayList<>();
    }

    @Override
    public ArrayList<Face> roll() {
        ArrayList<Face> faces = new ArrayList<>();
        for (ADice dice : dices) {
            faces.add(diceFaces.get((int) (Math.random() * diceFaces.size())));
        }
        return faces;
    }

    @Override
    public void setGameObject(GameObject owner) {

    }
}
