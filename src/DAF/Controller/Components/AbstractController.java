package DAF.Controller.Components;

import DAF.Components.AbstractComponent;
import DAF.Controller.ControllerManager;
import DAF.Dice.Components.ADice;

import java.util.UUID;

public class AbstractController extends AbstractComponent implements IController {
    private int _playerNo;

    private UUID _token;
    
    public AbstractController() {
        this._playerNo = ControllerManager.getInstance().GetNextPlayer();
    }

    public int getPlayerNo() {
        return this._playerNo;
    }

    public String getTokenId() { return this._token.toString(); }

    @Override
    public void start()
    {
        ControllerManager.getInstance().add(this);

       this._token = UUID.randomUUID();
    }
}
