public class StupidComponent extends AbstractComponent
{
    @Override
    public void update() {
        PictureGraphic g = (PictureGraphic)this.getGameObject().getComponent(EComponentType.AGraphic);
        if(g != null) {
            g.top = g.top;
            g.left = g.left;
            // g.top = (g.top + 1) % 50;
            // g.left = (g.left - 1) % 50;
        }
    }
}