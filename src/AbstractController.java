public class AbstractController extends AbstractComponent {
    private int _playerNo;
    
    public AbstractController() {
        this._playerNo = ControllerManager.getInstance().GetPlayerCount();
    }

    public int getPlayerNo() {
        return this._playerNo;
    }
}
