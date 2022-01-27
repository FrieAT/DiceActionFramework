package RuneGame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.ResourceBundle.Control;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.Icon;

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
import RuneGame.RuneDice.Rune;

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
        FIGHT_OPPONENT,
        VICTORY_SCREEN
    }

    //CONFIG BEGIN
    int _maxPlayers = 2;

    int _maxRounds = 3;
    //CONFIG END

    LabelGraphic txtCurAction, txtStatistics;

    PictureGraphic background;

    GameState _state = GameState.AWAITING_READY;

    ArrayList<IController> _controllers = new ArrayList<>();

    Random _randomGenerator = new Random();

    double _waitTime;

    int _playersTurn = 0;

    boolean _playersTurnIncreased = true;

    ConcurrentLinkedQueue<IController> _attackQueue = new ConcurrentLinkedQueue<>();

    IController _curAttacker = null;

    ArrayList<AttackButtonComponent> _controllersAttack;

    ArrayList<Integer> _statControllerWins;

    int _curDice = 0;
    
    @Override
    public void init() {
        _statControllerWins = new ArrayList<>();

        background = RunGameFactory.createBackground("images/background_2.png", new Vector2(0, 0));
        txtCurAction = RunGameFactory.createText("<<ActionText>>", new Vector2(400, 350));

        GameObject playerCenter = new GameObject("PlayerRoot");
        playerCenter.getTransform().setPosition(new Vector2(450, 300));
        for(int i = 0; i < _maxPlayers; i++) {
            IController playerController = RunGameFactory.createPlayer(_maxPlayers);
            playerController.getGameObject().setParent(playerCenter);
            _controllers.add(playerController);
            Vector2 position = playerController.getGameObject().getTransform().getPosition();
            System.out.println("Position: "+position.x+" / "+position.y);

            _statControllerWins.add(0);
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

        txtStatistics = RunGameFactory.createText("Leaderboard:", new Vector2(0, 0));

        refreshStatistics();
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
            case FIGHT_OPPONENT:
                stateFightOpponent();
                break;
            case VICTORY_SCREEN:
                stateVictoryScreen();
                refreshStatistics();
                break;
        }
    }

    public int getBestPlayer() {
        int bestPlayer = 0;
        int lastWins = 0;
        int i = 0;
        for(int wins : this._statControllerWins) {
            if(wins > lastWins) {
                lastWins = wins;
                bestPlayer = i;
            }
            i++;
        }
        return bestPlayer + 1;
    }

    public void refreshStatistics() {
        int playedRounds = 0;
        for(int wins : this._statControllerWins) {
            playedRounds += wins;
        }

        String strLeaderBoard = "Runde: "+playedRounds+" von "+_maxRounds+"<br><br>Leaderboard:";
        int playerId = 1;
        for(int wins : this._statControllerWins) {
            strLeaderBoard += "<br>Spieler "+(playerId++)+": "+wins+" Siege";
        }
        txtStatistics.setLabelText(strLeaderBoard);
    }

    public GameState getGameState() {
        return this._state;
    }

    private void fight(RuneDiceBag attacker, RuneDiceBag defender) {
        boolean attackHappened = false;
        for(RuneDice attackerBow : attacker.getRuneDices(Rune.BOW_MAN)) {
            //attacker.markRuneDice(attackerBow, true, false);
            attackHappened = true;
            if(defender.getRuneDice(Rune.JOKER) != null) {
                txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Bogenschütze tötet Joker");
                defender.getRuneDice(Rune.JOKER).setTopFace(null);
            } else if(defender.getRuneDice(Rune.BOW_MAN) != null) {
                int height = defender.getRuneDices(Rune.STAIRSANDWALLS).size();
                if(defender.getRuneDice(Rune.STAIRS) != null) {
                    height += defender.getRuneDices(Rune.WALLS).size();
                }

                //Target weakest BowMan first.
                height -= -1 + defender.getRuneDices(Rune.BOW_MAN).size();

                if(_randomGenerator.nextInt(100) <= 100.0/height) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Bogenschütze tötet Bögenschütze");
                    defender.getRuneDices(Rune.BOW_MAN).get(0).setTopFace(null);
                } else {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Bogenschütze verfehlt Bögenschütze");
                }
            } else if(defender.getRuneDice(Rune.SHIELD_BEARER) != null) {
                if(_randomGenerator.nextInt(100) <= 25) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Bogenschütze tötet Lanzenträger");
                    defender.getRuneDice(Rune.SHIELD_BEARER).setTopFace(null);
                } else {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Bogenschütze verfehlt Lanzenträger");
                }
            }
            break;
        }

        if(!attackHappened) {
            for(RuneDice attackerShieldBearer : attacker.getRuneDices(Rune.SHIELD_BEARER)) {
                //attacker.markRuneDice(attackerShieldBearer, true, false);
                
                if(defender.getRuneDice(Rune.JOKER) != null) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Lanzenträger tötet Joker");
                    defender.getRuneDice(Rune.JOKER).setTopFace(null);
                } else if(defender.getRuneDice(Rune.SHIELD_BEARER) != null) {
                    if(_randomGenerator.nextInt(100) <= 50) {
                        txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Lanzenträger tötet Lanzenträger");
                        defender.getRuneDice(Rune.SHIELD_BEARER).setTopFace(null);
                    } else {
                        txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Lanzenträger verfehlt Lanzenträger");
                    }
                } else if(defender.getRuneDice(Rune.STAIRSANDWALLS) != null) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Lanzenträger klettert auf Mauerstiege");
                    defender.getRuneDice(Rune.STAIRSANDWALLS).setTopFace(null);
                } else if(defender.getRuneDice(Rune.STAIRS) != null && defender.getRuneDice(Rune.WALLS) != null) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Lanzenträger klettert einen Turm hoch");
                    defender.getRuneDice(Rune.WALLS).setTopFace(null);
                } else if(defender.getRuneDice(Rune.BOW_MAN) != null) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Lanzenträger tötet Bogenschützen");
                    defender.getRuneDice(Rune.BOW_MAN).setTopFace(null);
                }
                break;
            }
        }
    }

    private void stateVictoryScreen() {
        int playedRounds = 0;
        for(int wins : this._statControllerWins) {
            playedRounds += wins;
        }

        _waitTime -= 1.0 * RenderManager.getInstance().getDeltaTime();
        if(_waitTime <= 0.0) {
            if(playedRounds < _maxRounds) {
                for (IController controller : _controllers) {
                    for(ReadyButtonComponent readyButton : controller.getGameObject().getComponentsInChildren(ReadyButtonComponent.class)) {
                        readyButton.setReady(false);
                    }
    
                    RuneDiceBag runeBag = controller.getGameObject().getComponent(RuneDiceBag.class);
                    runeBag.resetReadyAndFace();
                }

                _state = GameState.AWAITING_READY;
            } else {
                
            }
            return;
        }

        for (IController controller : _controllers) {
            PlayerLabelGraphic playerText = controller.getGameObject().getComponent(PlayerLabelGraphic.class);
            if(playerText != null) {
                if(playedRounds < _maxRounds) {
                    playerText.setLabelText("Nächste Runde startet in "+Math.round(_waitTime)+" Sekunden ...");
                } else {
                    playerText.setLabelText("Spiel-Ende!");
                }
            }
        }

        if(playedRounds >= _maxRounds) {
            txtCurAction.setLabelText("Spieler "+this.getBestPlayer()+" ist Spiel-Sieger!");
        }
    }   

    private void stateFightOpponent() {
        _waitTime -= 1.0 * RenderManager.getInstance().getDeltaTime();
        if(_waitTime <= 0.0) {
            _waitTime = 3.0;

            if(_attackQueue.size() <= 1) {
                _curAttacker = _attackQueue.poll();
                _state = GameState.VICTORY_SCREEN;
                _waitTime = 10.0;
                txtCurAction.setLabelText("Sieg für Spieler "+_curAttacker.getPlayerNo());

                _statControllerWins.set(_curAttacker.getPlayerNo() - 1, _statControllerWins.get(_curAttacker.getPlayerNo() - 1) + 1);

                return;
            }
            _curAttacker = _attackQueue.poll();

            RuneDiceBag attackerBag = _curAttacker.getGameObject().getComponent(RuneDiceBag.class);

            if(attackerBag.getRuneDice(Rune.BOW_MAN) == null &&
            attackerBag.getRuneDice(Rune.SHIELD_BEARER) == null &&
            attackerBag.getRuneDice(Rune.JOKER) == null) {
                //Defeated!
                return;
            }

            AttackButtonComponent attackInfo = null;
            for(AttackButtonComponent attackComp : _controllersAttack) {
               ControllerView attackView = attackComp.getGameObject().getComponent(ControllerView.class);
               if(attackView.getController() == _curAttacker) {
                attackInfo = attackComp;
               }
            }

            if(attackInfo != null) {
                IController target = attackInfo.getTarget();
                if(target == null) {
                    target = _controllers.get(_randomGenerator.nextInt(_controllers.size()));
                }
                if(target != _curAttacker) {
                    txtCurAction.setLabelText("Spieler "+_curAttacker.getPlayerNo()+" greift Spieler "+target.getPlayerNo()+" an!");

                    RuneDiceBag defenderBag = target.getGameObject().getComponent(RuneDiceBag.class);
                    this.fight(attackerBag, defenderBag);
                } else {
                    txtCurAction.setLabelText("Spieler "+_curAttacker.getPlayerNo()+" macht nichts ...");
                }
            }

            _attackQueue.offer(_curAttacker);
        }
    }

    private void stateChooseOpponent() {
        _waitTime -= 1.0 * RenderManager.getInstance().getDeltaTime();
        txtCurAction.setLabelText("Wählt euren Gegner in "+Math.round(_waitTime)+" Sekunden!");

        if(_waitTime <= 0.0) {
            this._waitTime = 3.0;
            this._state = GameState.FIGHT_OPPONENT;
            txtCurAction.setLabelText("Der Kampf beginnt!");
            
            _controllersAttack = new ArrayList<>();
            for (IController controller : _controllers) {
                AttackButtonComponent attack = controller.getGameObject().getComponentInChildren(AttackButtonComponent.class);
                attack.getGameObject().setEnabled(false);
                _controllersAttack.add(attack);
            }

            _controllersAttack.sort(new Comparator<AttackButtonComponent>() {
                @Override
                public int compare(AttackButtonComponent o1, AttackButtonComponent o2) {
                    return (int)(o2.getAttackTime() - o1.getAttackTime());
                }
            });

            _attackQueue.clear();

            for(AttackButtonComponent attack : _controllersAttack) {
                IController controller = attack.getGameObject().getComponentInParent(AbstractController.class);
                _attackQueue.offer(controller);
            }

            _curAttacker = null;
            _curDice = 0;
        }
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
            
            final int maxRollCount = 4;
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
            
                AttackButtonComponent attack = controller.getGameObject().getComponentInChildren(AttackButtonComponent.class);
                attack.getGameObject().setEnabled(true);
                attack.reset();
            }

            _state = GameState.CHOOSE_OPPONENT;
            _waitTime = 10.0;
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
            txtCurAction.setLabelText(String.format("Es sind %d von %d Spieler bereit...<br>FPS: %f", 
                playerCountReady, 
                _maxPlayers,
                RenderManager.getInstance().getDeltaTime()*1000));
        }
    }
}
