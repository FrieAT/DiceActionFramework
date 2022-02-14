package POTCGame;

import DAF.AbstractManager;
import DAF.Controller.Components.IController;
import DAF.Dice.Components.ADice;
import DAF.GameObject;
import DAF.Math.Vector2;
import DAF.Renderer.Components.LabelGraphic;
import DAF.Renderer.Components.PictureGraphic;

import java.util.ArrayList;
import java.util.Arrays;

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
        PREPARE_NEXT_ROUND,
        END_DRAW,
        END_GAME,
        AND_MORE,
    }

    LabelGraphic txtCurAction;
    LabelGraphic txtResults;
    PictureGraphic background;

    int _maxPlayers = 2;
    int _playersTurn = 1;

    int _maxRounds = 3;
    int _currentRound = 1;

    ArrayList<IController> _controllers = new ArrayList<>();
    int[] _roundResults = new int[6];
    int[] _endResults = new int[_maxPlayers];


    GameState _state = GameState.READY_CHECK;


    @Override
    public void init() {

        background = GameFactory.createBackground("images/wooden_floor.jpg", new Vector2(0, 0));
        // old Vector: x = 340, y = 300
        txtCurAction = GameFactory.createText("<<ActionText>>", 32, new Vector2(380, 310));
        txtCurAction.setWebColor("rgb(255, 255, 255)");
        txtCurAction.setWebBgColor("rgb(38, 38, 38, 0.7)");
        txtCurAction.setWidth(1024);

        Arrays.fill(_roundResults, 0);
        Arrays.fill(_endResults, 0);

        GameObject playerCenter = new GameObject("PlayerRoot");
        playerCenter.getTransform().setPosition(new Vector2(450, 300));
        for(int i = 0; i < _maxPlayers; i++) {
            IController playerController = GameFactory.createPlayer(_maxPlayers);
            playerController.getGameObject().setParent(playerCenter);
            _controllers.add(playerController);
            Vector2 position = playerController.getGameObject().getTransform().getPosition();
            System.out.println("Position: "+position.x+" / "+position.y);
        }

        txtResults = GameFactory.createText(resultsToWebString(_endResults), 15, new Vector2(750, 600));
        txtResults.setWebColor("white");
        txtResults.setWebBgColor("rgb(38, 38, 38, 0.7)");
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
            case PREPARE_NEXT_ROUND:
                statePrepareNextRound();
                break;
            case END_DRAW:
                stateEndDraw();
                break;
            case END_GAME:
                stateEndGame();
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
            txtCurAction.setLabelText("All players are ready!");

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
            txtCurAction.setLabelText(String.format("Round %d / %d<br>Ready: %d / %d",
                    _currentRound, _maxRounds, playerCountReady, _maxPlayers));
        }
    }

    public void stateThrowDices() {
        int playerCountRolled = 0;
        int [] result = new int[6];
        Arrays.fill(result, 0);

        for (IController controller : _controllers) {
            for(GameObject child : controller.getGameObject().getChildren()) {
                RollDiceButtonComponent rollButton = child.getComponent(RollDiceButtonComponent.class);

                if (rollButton != null && rollButton.hasRolled()) {
                    ArrayList<ADice> dices = controller.getGameObject().getComponent(POTCDiceBag.class).getDices();
                    for (ADice dice : dices) {
                        result[dice.getTopFace().getValue() - 1]++;
                    }
                    playerCountRolled++;
                    break;
                }
            }
        }

        if (playerCountRolled == _maxPlayers) {
            txtCurAction.setLabelText("All players<br>have rolled");

            _roundResults = result;
            _state = GameState.GUESS_DICES;
        } else {
            txtCurAction.setLabelText(String.format("Rolled: %d / %d", playerCountRolled, _maxPlayers));
        }
    }

    public void stateGuessDices() {

        int counter = 0;

        for (IController controller : _controllers) {
            if (controller.getPlayerNo() == _playersTurn) {
                GuessFieldComponent guessField = controller.getGameObject().getComponentInChildren(GuessFieldComponent.class);
                GuessDiceButtonComponent guessButton = guessField.getGameObject().getComponentInChildren(GuessDiceButtonComponent.class);

                if (guessButton != null && !guessField.getGameObject().isEnabled()) {
                    guessField.getGameObject().setEnabled(true);
                }

                if (guessButton != null && guessButton.hasGuessed()) {
                    if (++_playersTurn > _maxPlayers)
                        _playersTurn = 1;
                    counter++;

                    //guessField.setGuess(1, Integer.parseInt(guessField.getGameObject().getComponentInChildren(LabelGraphic.class).getLabelText()));
                    System.out.println("Player " + controller.getPlayerNo() + " guess: " + Arrays.toString(guessField.getGuess()));
                    guessField.getGameObject().setEnabled(false);
                }
            } else {
                GuessFieldComponent guessField = controller.getGameObject().getComponentInChildren(GuessFieldComponent.class);
                GuessDiceButtonComponent guessButton = guessField.getGameObject().getComponentInChildren(GuessDiceButtonComponent.class);

                if (guessButton != null && guessField.getGameObject().isEnabled())
                    guessField.getGameObject().setEnabled(false);
            }
        }

        if (counter == _maxPlayers) {
            if (++_playersTurn > _maxPlayers)
                _playersTurn = 1;
            _state = GameState.GET_RESULTS;
        }
    }

    public void stateGetResults() {

        // each entry contains [playerIndex, guessDice]
        ArrayList<int[]> correctGuesses = new ArrayList<>();

        GuessFieldComponent guessField;
        for (int i = 0; i < _controllers.size(); i++) {
            guessField = _controllers.get(i).getGameObject().getComponentInChildren(GuessFieldComponent.class);
            int guessDice = guessField.getGuessDice();
            int guessCount = guessField.getGuessCount();
            System.out.println("GuessDice: " + guessDice + "; GuessCount: " + guessCount);

            // Case 1: 0 > guessCount < total amount of dices
            // check if guess is in greater than the total amount of dices
            if (guessCount < 0 || guessCount >= _roundResults.length * _maxPlayers){
                continue;
            }

            if (guessDice <= 0)
                continue;

            // Case 2: guessDice not correct
            if (_roundResults[guessDice - 1] != guessCount) {
                continue;
            }

            // Case 3: guessDice is correct
            if (_roundResults[guessDice - 1] == guessCount) {
                correctGuesses.add(new int[]{i+1, guessDice});
            }
        }

        // no correct guesses
        if (correctGuesses.isEmpty()) {
            if (_currentRound == _maxRounds) {
                txtCurAction.setLabelText(resultsToWebString(_endResults));
                _state = GameState.END_GAME;
            } else {
                _state = GameState.PREPARE_NEXT_ROUND;
            }
        }

        // one Player won the round
        if (correctGuesses.size() == 1) {
            _endResults[correctGuesses.get(0)[0] - 1]++;

            txtCurAction.setLabelText(String.format("Player %d won.", correctGuesses.get(0)[0]));
            txtResults.setLabelText(resultsToWebString(_endResults));

            if (_currentRound == _maxRounds) {
                txtCurAction.setLabelText(resultsToWebString(_endResults));
                _state = GameState.END_GAME;
            } else {
                _state = GameState.PREPARE_NEXT_ROUND;
            }

        }

        // more than one player had correct guesses
        if (correctGuesses.size() > 1) {

            ArrayList<int[]> highestGuesses = new ArrayList<>();
            highestGuesses.add(correctGuesses.get(0));

            for (int i = 1; i < correctGuesses.size(); i++) {
                // the player had the same guess as another player
                if (correctGuesses.get(i)[1] == highestGuesses.get(0)[1]) {
                    highestGuesses.add(correctGuesses.get(i));
                }
                // the player had a higher guess than the players before
                if (correctGuesses.get(i)[1] > highestGuesses.get(0)[1]) {
                    highestGuesses.clear();
                    highestGuesses.add(correctGuesses.get(i));
                }
            }

            System.out.println("    Correct Guesses: " + correctGuesses.size());
            System.out.println("    Highest Guesses: " + highestGuesses.size());
            System.out.println("    Current Round:   " + _currentRound);


            if (highestGuesses.size() == 1) {
                _endResults[highestGuesses.get(0)[0] - 1]++;

                txtCurAction.setLabelText(String.format("Player %d won.", highestGuesses.get(0)[0]));

                txtResults.setLabelText(resultsToWebString(_endResults));

                System.out.println("[Player, GuessDice, GuessCount]\n" + Arrays.toString(highestGuesses.get(0)));
                if (_currentRound == _maxRounds) {
                    txtCurAction.setLabelText(resultsToWebString(_endResults));
                    _state = GameState.END_GAME;
                } else {
                    _state = GameState.PREPARE_NEXT_ROUND;
                }
            }

            //System.out.println("Highest Guesses: " + highestGuesses.size());
            else if (highestGuesses.size() > 1) {
                for (int[] highestGuess : highestGuesses) {
                    _endResults[highestGuess[0] - 1]++;
                }

                txtResults.setLabelText(resultsToWebString(_endResults));

                if (_currentRound == _maxRounds) {
                    txtCurAction.setLabelText(resultsToWebString(_endResults));
                    _state = GameState.END_GAME;
                }

                if (_currentRound < _maxRounds) {
                    _state = GameState.PREPARE_NEXT_ROUND;
                }
            }
        }

        System.out.println("State GET_RESULTS reached.");
    }

    public void statePrepareNextRound() {
        for (IController controller : _controllers) {
            ReadyButtonComponent readyButton = controller.getGameObject().getComponentInChildren(ReadyButtonComponent.class);
            RollDiceButtonComponent rollButton = controller.getGameObject().getComponentInChildren(RollDiceButtonComponent.class);
            GuessDiceButtonComponent guessButton = controller.getGameObject().getComponentInChildren(GuessFieldComponent.class)
                    .getGameObject().getComponentInChildren(GuessDiceButtonComponent.class);
            //POTCDiceBag diceBag = controller.getGameObject().getComponentInParent(POTCDiceBag.class);

            readyButton.setReady(false);
            rollButton.setRollState(false);
            guessButton.setGuessState(false);
            //diceBag.deactivateGuesses();
        }
        _currentRound++;
        System.out.println("State PREPARE_NEXT_ROUND reached.");
        _state = GameState.READY_CHECK;
    }
    public void stateEndDraw() {
        System.out.println("State END_DRAW reached.");
        txtCurAction.setLabelText("DRAW");
    }

    public void stateEndGame() {
        System.out.println("State END_GAME reached.");
        txtCurAction.getGameObject().getTransform().setPosition(new Vector2(330, 260));
    }

    public String resultsToWebString(int[] results) {
        StringBuilder s = new StringBuilder("<b>Leaderboard</b><br><br>");

        if (_maxPlayers <= 2) {
            s.append("<table>").append("<tr>");

            System.out.println(_controllers);
            for (IController controller : _controllers) {
                s.append("<th>Player ").append(controller.getPlayerNo()).append("</th>");
            }
            s.append("</tr>");


            s.append("<tr>");
            for (int result : results) {
                s.append("<td>").append(result).append("</td>");
            }
            s.append("</tr>").append("</table>");

            System.out.println("Results: " + Arrays.toString(results));
            return s.toString();
        } else {
            s.append("<table>");

            for (int i = 0; i < _controllers.size(); i++) {
                s.append("<tr>");
                s.append("<th>Player").append(_controllers.get(i).getPlayerNo()).append(": </th>");
                s.append("<td>").append(results[i]).append("</td>");
                s.append("</tr>");
            }
            s.append("</table>");
            return s.toString();
        }
    }
}
