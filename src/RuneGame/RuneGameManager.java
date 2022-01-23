package RuneGame;

import java.util.ArrayList;

import DAF.AbstractManager;
import DAF.GameObject;
import DAF.Controller.Components.IController;
import DAF.Controller.Components.PlayerController;
import DAF.Math.Vector2;
import DAF.Renderer.RenderManager;
import DAF.Renderer.Components.LabelGraphic;

public class RuneGameManager extends AbstractManager {
    protected static RuneGameManager _instance;
    public static RuneGameManager getInstance() {
        if (_instance == null)
            _instance = new RuneGameManager();
        return _instance;
    }

    enum GameState {
        AWAITING_READY,
        THROW_DICES,
        AND_MORE,
    }
    
    LabelGraphic txtCurAction;

    int _maxPlayers = 2;

    GameState _state = GameState.AWAITING_READY;

    ArrayList<IController> _controllers = new ArrayList<>();
    
    @Override
    public void init() {
        txtCurAction = RunGameFactory.createText("<<ActionText>>", new Vector2(512, 300));

        GameObject playerCenter = new GameObject("PlayerRoot");
        playerCenter.getTransform().setPosition(new Vector2(450, 300));
        for(int i = 0; i < _maxPlayers; i++) {
            IController playerController = RunGameFactory.createPlayer(_maxPlayers);
            playerController.getGameObject().setParent(playerCenter);
            _controllers.add(playerController);
            Vector2 position = playerController.getGameObject().getTransform().getPosition();
            System.out.println("Position: "+position.x+" / "+position.y);
        }
    }

    @Override
    public void update() {
        switch(_state) {
            case AWAITING_READY:
                stateAwaitingReady();
                break;
            case THROW_DICES:
                stateThrowDices();
                break;
        }
    }

    private void stateThrowDices() {

    }

    private void stateAwaitingReady() {
        int playerCountReady = 0;
        for (IController controller : _controllers) {
            for(GameObject child : controller.getGameObject().getChildren()) {
                ReadyButtonComponent readyButton = child.getComponent(ReadyButtonComponent.class);
                if(readyButton != null && readyButton.isReady()) {
                    playerCountReady++;
                    break;
                }
            }
        }

        if(playerCountReady == _maxPlayers) {
            txtCurAction.setLabelText("Es sind alle bereit, es geeeeht loooos!");
            _state = GameState.THROW_DICES;
            return;
        }
        else {
            txtCurAction.setLabelText(String.format("Es sand %d von %d Spieler bereit... Oida bitte, duads weiter!", playerCountReady, _maxPlayers));
        }
    }
}
