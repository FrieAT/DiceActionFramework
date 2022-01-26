package RuneGame;

import DAF.GameObject;
import DAF.Components.AbstractComponent;
import DAF.Controller.Components.AbstractController;
import DAF.Controller.Components.ControllerView;
import DAF.Controller.Components.IController;
import DAF.Math.Vector2;
import DAF.Renderer.Components.LabelGraphic;

public class PlayerLabelGraphic extends AbstractComponent {
    private LabelGraphic _label;
    
    @Override
    public void start() {
        GameObject playerLabel = new GameObject("PlayerLabel", this.getGameObject());

        IController controller = this.getGameObject().getComponent(AbstractController.class);
        playerLabel.addComponent(ControllerView.class).setController(controller);

        LabelGraphic label = playerLabel.addComponent(LabelGraphic.class);

        this._label = label;

        label.setFontSize(14);
        label.setLabelText("");
        label.getTransform().setPosition(new Vector2(-50, -50));
        label.setWidth(100);
    }

    public void setLabelText(String text) {
        this._label.setLabelText(text);
    }
}
