package POTCGame;

import DAF.AbstractManager;
import DAF.Controller.Components.IController;
import DAF.Dice.Components.ADice;
import DAF.Dice.Components.ADiceBag;
import DAF.GameObject;
import DAF.Math.Vector2;
import DAF.Renderer.Components.LabelGraphic;
import DAF.Renderer.Components.PictureGraphic;

import java.util.ArrayList;

public class GameManager extends AbstractManager {

    protected static GameManager _instance;

    public static GameManager getInstance() {
        if (_instance == null)
            _instance = new GameManager();
        return _instance;
    }

    enum GameState {
        READY_CHECK,
        THROW_DICES,
        GUESS_DICES,
        GET_RESULTS,
        AND_MORE,
    }

    LabelGraphic txtCurAction;
    PictureGraphic background;

    int _maxPlayers = 7;
    int _playersTurn = 1;
    int _counter = 0;

    ArrayList<IController> _controllers = new ArrayList<>();
    ArrayList<Integer> results = new ArrayList<>();

    GameState _state = GameState.READY_CHECK;


    @Override
    public void init() {
        background = GameFactory.createBackground("images/wooden_floor.jpg", new Vector2(0, 0));
        // old Vector: x = 340, y = 300
        txtCurAction = GameFactory.createText("<<ActionText>>", 32, new Vector2(340, 350));

        GameObject playerCenter = new GameObject("PlayerRoot");
        playerCenter.getTransform().setPosition(new Vector2(450, 300));
        for(int i = 0; i < _maxPlayers; i++) {
            IController playerController = GameFactory.createPlayer(_maxPlayers);
            playerController.getGameObject().setParent(playerCenter);
            _controllers.add(playerController);
            Vector2 position = playerController.getGameObject().getTransform().getPosition();
            System.out.println("Position: "+position.x+" / "+position.y);
        }
    }

    @Override
    public void update() {
        switch(_state) {
            case READY_CHECK:
                stateReadyCheck();
                break;
            case THROW_DICES:
                stateThrowDices();
                break;
            case GUESS_DICES:
                stateGuessDices();
                break;
            case GET_RESULTS:
                stateGetResults();
                break;
        }
    }

    public void stateReadyCheck() {
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

            // test start
            for (IController controller: _controllers) {
                for (GameObject child : controller.getGameObject().getChildren()) {
                    RollDiceButtonComponent rollButton = child.getComponent(RollDiceButtonComponent.class);

                    if (rollButton != null) {
                        rollButton.getGameObject().setEnabled(true);
                    }
                }
            }
            // test end
            _state = GameState.THROW_DICES;
        }
        else {
            txtCurAction.setLabelText(String.format("Ready: %d / %d<br>Hurry up!", playerCountReady, _maxPlayers));
        }
    }

    public void stateThrowDices() {
        int playerCountRolled = 0;
        for (IController controller : _controllers) {
            for(GameObject child : controller.getGameObject().getChildren()) {
                RollDiceButtonComponent rollButton = child.getComponent(RollDiceButtonComponent.class);

                if (rollButton != null && rollButton.hasRolled()) {
                    playerCountRolled++;
                    break;
                }
            }
        }

        if (playerCountRolled == _maxPlayers) {
            txtCurAction.setLabelText("All players<br>have rolled");

            // test: enable the GuessButtons for next state
            for (IController controller: _controllers) {
                for (GameObject child : controller.getGameObject().getChildren()) {
                    GuessDiceButtonComponent guessButton = child.getComponent(GuessDiceButtonComponent.class);

                    if (guessButton != null) {
                        guessButton.getGameObject().setEnabled(true);
                    }
                }
            }
            // test end

            _state = GameState.GUESS_DICES;
        } else {
            txtCurAction.setLabelText(String.format("Rolled: %d / %d", playerCountRolled, _maxPlayers));
        }
    }

    public void stateGuessDices() {

        for (IController controller : _controllers) {
            //System.out.println("Players Turn: " + _playersTurn + "\n");
            if (controller.getPlayerNo() == _playersTurn) {
                for (GameObject child : controller.getGameObject().getChildren()) {
                    GuessFieldComponent guessField = child.getParent().getComponentInChildren(GuessFieldComponent.class);
                    GuessDiceButtonComponent guessButton = guessField.getGameObject().getComponentInChildren(GuessDiceButtonComponent.class);
                    //System.out.println(guessField == controller.getGameObject().getComponentInChildren(GuessFieldComponent.class));
                    //System.out.println(child.getComponentInChildren(GuessFieldComponent.class));
                    //GuessDiceButtonComponent guessButton = child.getComponent(GuessDiceButtonComponent.class);

                    //if (guessButton != null && /*!child.isEnabled()*/) {
                    if (guessButton != null && !guessField.getGameObject().isEnabled()) {
                        guessField.getGameObject().setEnabled(true);
                        //child.setEnabled(true);
                    }
                    if (guessButton != null && guessButton.hasGuessed()) {
                        if (++_playersTurn > _maxPlayers)
                            _playersTurn = 1;
                        _counter++;
                        guessField.getGameObject().setEnabled(false);
                        //child.setEnabled(false);
                    }
                }
            } else {
                for (GameObject child : controller.getGameObject().getChildren()) {
                    GuessFieldComponent guessField = child.getParent().getComponentInChildren(GuessFieldComponent.class);
                    GuessDiceButtonComponent guessButton = guessField.getGameObject().getComponentInChildren(GuessDiceButtonComponent.class);
                    //GuessDiceButtonComponent guessButton = child.getComponent(GuessDiceButtonComponent.class);

                    //if (guessButton != null && child.isEnabled())
                    if (guessButton != null && guessField.getGameObject().isEnabled())
                        child.setEnabled(false);

                }
            }
        }

        if (_counter == _maxPlayers) {
            _counter = 0;
            _state = GameState.GET_RESULTS;
        }
    }

    public void stateGetResults() {
        for (IController controller : _controllers) {
            int results = 0;
            if (controller.getGameObject().getComponentInChildren(POTCDiceBag.class) != null) {
                for (ADice dice : controller.getGameObject().getComponentInChildren(POTCDiceBag.class).getDices()) {
                    results += dice.getTopFace().getValue();
                }
                System.out.println(results);
            }
        }
        System.out.println("State GET_RESULTS reached.");
    }
}
