package RuneGame;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import DAF.GameObject;
import DAF.Components.AbstractComponent;
import DAF.Controller.Components.AbstractController;
import DAF.Controller.Components.IController;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;
import javafx.scene.control.Button;

public class AttackButtonComponent extends AbstractComponent implements IInputListener {
    private IController _toAttack;

    private long _attackTime = 0;
    
    public void reset() {
        this.setAttack(null);
    }

    public long getAttackTime() {
        return this._attackTime;
    }

    public IController getTarget() {
        return this._toAttack;
    }

    @Override
    public void start() {
        InputManager.getInstance().add(ButtonInputEvent.class, this);

        this.reset();
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent)event;
        ButtonGraphic buttonGraphic = getGameObject().getComponent(ButtonGraphic.class);
        if(buttonEvent.getSource() == buttonGraphic) {
            IController parentController = this.getGameObject().getComponentInParent(AbstractController.class);
            this.setAttack(parentController);
        }
    }

    private void setAttack(IController toAttack)
    {
        for(AttackButtonComponent sibling : this.getSiblings()) {
            ButtonGraphic graphic = sibling.getGameObject().getComponent(ButtonGraphic.class);
            graphic.setWebBgColor("#eee");
            graphic.setWebColor("#333");
            sibling.setAttack(null);
        }

        ButtonGraphic graphic = this.getGameObject().getComponent(ButtonGraphic.class);
        if(toAttack != null) {
            graphic.setWebBgColor("#A83232");
            graphic.setWebColor("#eee");

            this._attackTime = new Date().getTime();
        } else {
            graphic.setWebBgColor("#eee");
            graphic.setWebColor("#333");
        }

        this._toAttack = toAttack;
    }

    public List<AttackButtonComponent> getSiblings() {
        LinkedList<AttackButtonComponent> siblings = new LinkedList<>();
        GameObject possiblePlayerRoot = this.getGameObject().getParent();
        for(IController otherController : possiblePlayerRoot.getComponentsInChildren(AbstractController.class)) {
            AttackButtonComponent otherAttack = otherController.getGameObject().getComponentInChildren(AttackButtonComponent.class);
            if(otherAttack != null && otherAttack != this) {
                siblings.add(otherAttack);
            }
        }
        return siblings;
    }
}
