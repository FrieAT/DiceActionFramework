import javafx.scene.Node;

import java.util.ArrayList;

public class ServerRenderer extends AGraphicRenderer {

    private ASerializer serializer;

    StringBuilder fetchFrame;

    public ServerRenderer(ASerializer serializer) {
        this.fetchFrame = new StringBuilder();
        this.serializer = serializer;
    }

    public ASerializer getSerializer() {
        return this.serializer;
    }

    @Override
    public boolean beforeRender() {
        return true;
    }

    @Override
    public void render(AGraphic g) {

        for (AGraphicRenderer renderer : this._graphicRenderer) {
            ServerRenderer serverRenderer = (ServerRenderer) renderer;

            if (serverRenderer == null) {
                System.out.println("WARNING: " + renderer.getClass().getName() + " != " + this.getClass().getName());
                continue;
            }

            fetchFrame.append(serializer.serialize(g)).append(",");

        }
    }

    @Override
    public boolean afterRender() {
        fetchFrame.replace(fetchFrame.length() - 1, fetchFrame.length(), "");
        fetchFrame.append("]");
        System.out.println(fetchFrame.toString());
        fetchFrame = new StringBuilder("");
        Init();
        return true;
    }

    @Override
    public void Init() {
        fetchFrame.append("[");
    }

    public Node renderAsSerialized(AGraphic g) {
        throw new NullPointerException("Not implemented, implement this in derived subclass.");
    }
}
