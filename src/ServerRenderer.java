import javafx.scene.Node;

public class ServerRenderer extends AGraphicRenderer {

    private ISerializer serializer;

    public ServerRenderer(ISerializer serializer) {
        this.serializer = serializer;
    }

    public ISerializer getSerializer() {
        return this.serializer;
    }

    @Override
    public boolean beforeRender() {
        return true;
    }

    @Override
    public void render(AGraphic g) {
        ISerializer serializer = this.getSerializer();

        for (AGraphicRenderer renderer : this._graphicRenderer) {
            ServerRenderer fxRenderer = (ServerRenderer) renderer;

            if (fxRenderer == null) {
                System.out.println("WARNING: " + renderer.getClass().getName() + " != " + this.getClass().getName());
                continue;
            }

            Node n = fxRenderer.renderAsSerialized(g);

            // TODO there should not be a serializer
            if (n != null)
                serializer.serialize(g);

        }
    }

    @Override
    public boolean afterRender() {

        ISerializer serializer;
        return true;
    }

    public Node renderAsSerialized(AGraphic g) {
        throw new NullPointerException("Not implemented, implement this in derived subclass.");
    }
}
