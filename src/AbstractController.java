public class AbstractController extends AbstractComponent implements IController {
    private int _playerNo;
    
    public AbstractController() {
        this._playerNo = ControllerManager.getInstance().GetPlayerCount();
    }

    public int getPlayerNo() {
        return this._playerNo;
    }

    @Override
    public void start()
    {
        ControllerManager.getInstance().add(this);
    }

    @Override
    public int throwDice(ADice dice) {
        throw new NullPointerException("Abstract method called");
    }
}
