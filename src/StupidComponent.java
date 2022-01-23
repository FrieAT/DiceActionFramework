import DAF.Components.AbstractComponent;
import DAF.Renderer.Components.PictureGraphic;

public class StupidComponent extends AbstractComponent
{
    @Override
    public void update() {
        PictureGraphic g = this.getGameObject().getComponent(PictureGraphic.class);
        if(g != null) {
             g.setTop((g.getTop() + 1) % 50);
             g.setLeft((g.getLeft() - 1) % 50);
        }
    }
}
