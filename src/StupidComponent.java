public class StupidComponent extends AbstractComponent
{
    @Override
    public void update() {
        PictureGraphic g = (PictureGraphic)this.getGameObject().getComponent(EComponentType.AGraphic);
        if(g != null) {
             g.setTop((g.getTop() + 1) % 50);
             g.setLeft((g.getLeft() - 1) % 50);
        }
    }
}
