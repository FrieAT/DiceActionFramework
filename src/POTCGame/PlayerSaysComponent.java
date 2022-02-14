package POTCGame;

import DAF.Components.AbstractComponent;
import DAF.Controller.Components.AbstractController;
import DAF.Controller.Components.IController;
import DAF.Renderer.Components.LabelGraphic;

public class PlayerSaysComponent extends AbstractComponent {
    private LabelGraphic _label;

    private IController _controller;

    @Override
    public void start() {
        this._label = this.getGameObject().getComponent(LabelGraphic.class);
        this._controller = this.getGameObject().getComponentInParent(AbstractController.class);
    }

    public void setPlayerSays(String text) {
        this._label.setLabelText("Spieler "+this._controller.getPlayerNo()+" sagt: "+text);
    }

    public void showPlayerSays(boolean show) {
        this.getGameObject().setEnabled(show);
    }
}
