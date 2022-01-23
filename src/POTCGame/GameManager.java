package POTCGame;

import DAF.AbstractManager;
import DAF.Controller.Components.IController;
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

    int _maxPlayers = 2;

    GameState _state = GameState.READY_CHECK;

    ArrayList<IController> _controllers = new ArrayList<>();

    @Override
    public void init() {
        txtCurAction = GameFactory.createText("<<ActionText>>", new Vector2(512, 300));
        background = GameFactory.createBackground("./images/wooden_floor.png", new Vector2(0, 0));

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
            txtCurAction.setLabelText(String.format("Es sand %d von %d Spieler bereit... Oida bitte, duads weiter!", playerCountReady, _maxPlayers));
        }
    }

    public void stateThrowDices() {
        int playerCountRolled = 0;
        for (IController controller : _controllers) {
            for(GameObject child : controller.getGameObject().getChildren()) {
                RollDiceButtonComponent rollButton = child.getComponent(RollDiceButtonComponent.class);

                if (rollButton != null && rollButton.diceHasRolled()) {
                    playerCountRolled++;
                    break;
                }
            }
        }

        if (playerCountRolled == _maxPlayers) {
            txtCurAction.setLabelText("Alle haben gewürfelt!");

            // test start
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
            txtCurAction.setLabelText(String.format("%d von %d Spielern haben gewürfelt", playerCountRolled, _maxPlayers));
        }
    }

    public void stateGuessDices() {
        int currentPlayerIndex = 1;
        for (IController controller : _controllers) {
            for(GameObject child : controller.getGameObject().getChildren()) {
                GuessDiceButtonComponent guessButton = child.getComponent(GuessDiceButtonComponent.class);

                if (guessButton != null && guessButton.isPlayersTurn(currentPlayerIndex)) {
                    currentPlayerIndex++;
                    break;
                }
            }
        }

        if (currentPlayerIndex == _maxPlayers) {
            txtCurAction.setLabelText("All have guessed");
            _state = GameState.GET_RESULTS;
        } else {
            txtCurAction.setLabelText(String.format("%d of %d players have guessed", currentPlayerIndex, _maxPlayers));
        }
    }

    public void stateGetResults() {
        System.out.println("State GET_RESULTS reached.");
    }
}
