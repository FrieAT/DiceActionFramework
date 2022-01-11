package DAF.Controller.Components;

import DAF.Components.AbstractComponent;
import DAF.Controller.ControllerManager;
import DAF.Dice.Components.ADice;

public class AbstractController extends AbstractComponent implements IController {
    private int _playerNo;
    
    public AbstractController() {
        this._playerNo = ControllerManager.getInstance().GetNextPlayer();
    }

    public int getPlayerNo() {
        return this._playerNo;
    }

    @Override
    public void start()
    {
        ControllerManager.getInstance().add(this);
    }
}
