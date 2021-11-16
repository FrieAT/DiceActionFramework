import javafx.scene.Node;

import java.util.HashMap;

public class PictureGraphicServerRenderer extends ServerRenderer {


    public PictureGraphicServerRenderer(ASerializer serializer) {
        super(serializer);
    }

    @Override
    public Node renderAsSerialized(AGraphic g) {
        PictureGraphic pictureGraphic;

        try {
            pictureGraphic = (PictureGraphic) g;
        } catch (ClassCastException e) {
            return null;
        }

        // TODO
        return null;
    }


}
