package RuneGame;

import DAF.Components.AbstractComponent;
import DAF.Event.AInputEvent;
import DAF.Event.ButtonInputEvent;
import DAF.Event.IInputListener;
import DAF.Input.InputManager;
import DAF.Renderer.Components.ButtonGraphic;
import javafx.scene.control.Button;

public class ToggleReadyRuneDice extends AbstractComponent implements IInputListener {

    @Override
    public void start() {
        InputManager.getInstance().add(ButtonInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        ButtonInputEvent buttonEvent = (ButtonInputEvent)event;
        ButtonGraphic buttonGraphic = this.getGameObject().getComponent(ButtonGraphic.class);
        if(buttonEvent.getSource() == buttonGraphic) {
            RuneDice runeComp = this.getGameObject().getComponentInParent(RuneDice.class);
            if(runeComp != null) {
                runeComp.setReady(!runeComp.isReady());

                if(runeComp.isReady()) {
                    buttonGraphic.setWebBgColor("rgba(0, 255, 255, 0.2)");
                } else {
                    buttonGraphic.setWebBgColor("rgba(255, 255, 255, 0.0)");
                } 
            }
        }
    }
    
}
