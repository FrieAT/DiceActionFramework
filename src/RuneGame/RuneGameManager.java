package RuneGame;

import java.util.ArrayList;
import java.util.Random;

import DAF.AbstractManager;
import DAF.GameObject;
import DAF.Controller.ControllerManager;
import DAF.Controller.Components.IController;
import DAF.Controller.Components.PlayerController;
import DAF.Dice.Components.ADice;
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
        MAKE_DECISION,
        AND_MORE,
    }
    
    LabelGraphic txtCurAction;

    int _maxPlayers = 2;

    GameState _state = GameState.AWAITING_READY;

    ArrayList<IController> _controllers = new ArrayList<>();

    Random _randomGenerator = new Random();

    double _waitTime;

    int _playersTurn = 0;

    boolean _playersTurnIncreased = true;
    
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
            case MAKE_DECISION:
                stateMakeDecision();
                break;
        }
    }

    private void stateMakeDecision() {
        if(_playersTurnIncreased) {
            for (IController controller : _controllers) {
                if(_playersTurn != controller.getPlayerNo()) {
                    continue;
                }

                RollButtonComponent rollComp = controller.getGameObject().getComponentInChildren(RollButtonComponent.class);
                if(rollComp == null) {
                    continue;
                }

                rollComp.getGameObject().setEnabled(false);
            }

            if(++_playersTurn > _controllers.size()) {
                _playersTurn = 1;
            }

            for (IController controller : _controllers) {
                if(_playersTurn != controller.getPlayerNo()) {
                    continue;
                }

                RollButtonComponent rollComp = controller.getGameObject().getComponentInChildren(RollButtonComponent.class);
                if(rollComp == null) {
                    continue;
                }

                rollComp.getGameObject().setEnabled(true);
                rollComp.setRollState(false);
            }

            _playersTurnIncreased = false;
        }

        for (IController controller : _controllers) {
            if(_playersTurn != controller.getPlayerNo()) {
                continue;
            }

            RollButtonComponent rollComp = controller.getGameObject().getComponentInChildren(RollButtonComponent.class);
            if(rollComp == null) {
                _playersTurnIncreased = true;
                continue;
            }

            if(rollComp.hasRolled()) {
                _playersTurnIncreased = true;
            }
        }

        
    }

    private void stateThrowDices() {
        
        if(_waitTime <= 0) {
            int endThrowing = _randomGenerator.nextInt(100);
            if(endThrowing >= 80) {
                _state = GameState.MAKE_DECISION;
                return;
            }
        } else {
            _waitTime -= 1.0 * RenderManager.getInstance().getDeltaTime();
        }
        
        for (IController controller : _controllers) {
            for(ADice dice : controller.getGameObject().getComponentsInChildren(ADice.class)) {
                dice.roll();
            }
        }
    }

    private void stateAwaitingReady() {
        int playerCountReady = 0;

        for (IController controller : _controllers) {
            for(ReadyButtonComponent readyButton : controller.getGameObject().getComponentsInChildren(ReadyButtonComponent.class)) {
                if(readyButton.isReady()) {
                    playerCountReady++;
                    break;
                }
            }
        }

        if(playerCountReady == _maxPlayers) {
            txtCurAction.setLabelText("Es sind alle bereit, es geeeeht loooos!");
            _waitTime = 3;
            _state = GameState.THROW_DICES;
            return;
        }
        else {
            txtCurAction.setLabelText(String.format("Es sand %d von %d Spieler bereit... Oida bitte, duads weiter! Delta Time: %f", 
                playerCountReady, 
                _maxPlayers,
                RenderManager.getInstance().getDeltaTime()));
        }
    }
}
