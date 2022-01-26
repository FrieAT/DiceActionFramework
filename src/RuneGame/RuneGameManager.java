package RuneGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle.Control;

import DAF.AbstractManager;
import DAF.GameObject;
import DAF.Controller.ControllerManager;
import DAF.Controller.Components.AbstractController;
import DAF.Controller.Components.ControllerView;
import DAF.Controller.Components.IController;
import DAF.Controller.Components.PlayerController;
import DAF.Dice.Components.ADice;
import DAF.Math.Vector2;
import DAF.Renderer.RenderManager;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.LabelGraphic;
import DAF.Renderer.Components.PictureGraphic;

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
        CHOOSE_OPPONENT,
        AND_MORE,
    }
    
    LabelGraphic txtCurAction;
    PictureGraphic background;

    int _maxPlayers = 2;

    GameState _state = GameState.AWAITING_READY;

    ArrayList<IController> _controllers = new ArrayList<>();

    Random _randomGenerator = new Random();

    double _waitTime;

    int _playersTurn = 0;

    boolean _playersTurnIncreased = true;
    
    @Override
    public void init() {
        background = RunGameFactory.createBackground("images/background_2.png", new Vector2(0, 0));
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

        for(GameObject player : playerCenter.getChildren()) {
            for(GameObject other : playerCenter.getChildren()) {
                if(other == player) {
                    continue;
                }
                IController controller = player.getComponent(AbstractController.class);
                IController otherController = player.getComponent(AbstractController.class);
                GameObject attackPlayer = new GameObject("Attack_"+controller.getPlayerNo()+"_"+otherController.getPlayerNo(), other);
                attackPlayer.addComponent(ControllerView.class).setController(controller);
                ButtonGraphic label = attackPlayer.addComponent(ButtonGraphic.class);
                label.setLabelText("Angreifen");
                attackPlayer.addComponent(AttackButtonComponent.class);

                attackPlayer.setEnabled(false);
            }
        }

        for(int i = 0; i < _maxPlayers; i++) {
            for(int j = 0; i < _maxPlayers; i++) {

            }
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
            case CHOOSE_OPPONENT:
                stateChooseOpponent();
                break;
        }
    }

    public GameState getGameState() {
        return this._state;
    }

    private void stateChooseOpponent() {
        _waitTime -= 1.0 * RenderManager.getInstance().getDeltaTime();
        txtCurAction.setLabelText("Wählt euren Gegner in "+Math.round(_waitTime)+" Sekunden!");
    }

    private void stateMakeDecision() {
        int playersAreDone = 0;
        LinkedList<RuneDice> dices = new LinkedList<>();
        
        _waitTime -= 1.0 * RenderManager.getInstance().getDeltaTime();

        for (IController controller : _controllers) {
            dices.clear();

            RollButtonComponent rollComp = controller.getGameObject().getComponentInChildren(RollButtonComponent.class);
            PlayerLabelGraphic playerText = controller.getGameObject().getComponent(PlayerLabelGraphic.class);
            if(rollComp == null) {
                continue;
            }

            int rollCount;
            RuneDiceBag bag = controller.getGameObject().getComponent(RuneDiceBag.class);
            if(bag != null) {
                rollCount = bag.getRollCount();
                dices.addAll(bag.getRuneDices());
            } else {
                continue;
            }

            //RollButtonComponent rollComp = controller.getGameObject().getComponentInChildren(RollButtonComponent.class);
            
            final int maxRollCount = 3;
            boolean controllerDone = (rollCount >= maxRollCount || _waitTime <= 0.0);
            
            rollComp.getGameObject().setEnabled(!controllerDone);

            if(controllerDone) {
                playersAreDone++;
                playerText.setLabelText("Warten auf andere Spieler ....");
            } else if(rollCount == 0) {
                playerText.setLabelText("Wähle Figuren zum Behalten, bevor du weiter würfelst!");
            }else {
                playerText.setLabelText("Letzte Entscheidung, bevor der Kampf beginnt!");
            }
            
            for(RuneDice d : dices) {
                if(controllerDone && !d.isReady()) {
                    d.setReady(true);
                }
            }
        }

        txtCurAction.setLabelText("Trifft eure Entscheidungen in "+Math.round(_waitTime)+" Sekunden!");

        if(_waitTime <= 0.0 || playersAreDone == ControllerManager.getInstance().GetPlayerCount()) {
            for (IController controller : _controllers) {
                PlayerLabelGraphic playerText = controller.getGameObject().getComponent(PlayerLabelGraphic.class);
                playerText.setLabelText("Welcher Spieler soll angegriffen werden?");
                _state = GameState.CHOOSE_OPPONENT;
                _waitTime = 10.0;
            }
        }
    }

    private void stateThrowDices() {
        
        if(_waitTime <= 0) {
            int endThrowing = _randomGenerator.nextInt(100);
            if(endThrowing >= 80) {
                for (IController controller : _controllers) {
                    for(RuneDice dice : controller.getGameObject().getComponentsInChildren(RuneDice.class)) {
                        dice.resetReady();
                    }
                }
                
                txtCurAction.setLabelText("Trifft eure Entscheidungen!");
                _state = GameState.MAKE_DECISION;
                _waitTime = 20.0;
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
            boolean isReady = false;
            for(ReadyButtonComponent readyButton : controller.getGameObject().getComponentsInChildren(ReadyButtonComponent.class)) {
                if(readyButton.isReady()) {
                    playerCountReady++;
                    isReady = true;
                    break;
                }
            }

            PlayerLabelGraphic playerText = controller.getGameObject().getComponent(PlayerLabelGraphic.class);
            if(playerText != null) {
                if(isReady) {
                    playerText.setLabelText("Warten auf andere Spieler ...");
                } else {
                    playerText.setLabelText("Bestätigen Sie mit Bereit, dass das Spiel beginnen kann!");
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
            txtCurAction.setLabelText(String.format("Es sand %d von %d Spieler bereit...<br>Oida bitte, duads weiter! Delta Time: %f", 
                playerCountReady, 
                _maxPlayers,
                RenderManager.getInstance().getDeltaTime()));
        }
    }
}
