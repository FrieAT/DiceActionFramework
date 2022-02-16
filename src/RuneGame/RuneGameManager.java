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
    int _maxPlayers = 3;

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

        background = RunGameFactory.createBackground("images/background-treasure.png", new Vector2(0, 0));
        txtCurAction = RunGameFactory.createText("<<ActionText>>", new Vector2(420, 350));

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
                IController otherController = other.getComponent(AbstractController.class);
                GameObject attackPlayer = new GameObject("Attack_"+controller.getPlayerNo()+"_"+otherController.getPlayerNo(), other);
                attackPlayer.addComponent(ControllerView.class).setController(controller);
                ButtonGraphic label = attackPlayer.addComponent(ButtonGraphic.class);
                label.setLabelText("Attack <br>player "+otherController.getPlayerNo());
                label.setFontSize(30);
                attackPlayer.addComponent(AttackButtonComponent.class);

                attackPlayer.setEnabled(false);
            }
        }

        txtStatistics = RunGameFactory.createText("Leaderboard", new Vector2(0, 0));

        txtStatistics.setFontSize(20);
        txtStatistics.setWebBgColor("rgb(230, 217, 202, 0.5)");
        txtCurAction.setFontSize(20);
        txtCurAction.setWebBgColor("rgb(230, 217, 202, 0.7)");


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

        String strLeaderBoard = "Round "+playedRounds+" / "+_maxRounds+"<br><br>Leaderboard";
        int playerId = 1;
        strLeaderBoard += "<table>";
        for(int wins : this._statControllerWins) {
            strLeaderBoard += "<tr>";
            strLeaderBoard += "<th>Player " + (playerId++) + ": </th>";
            strLeaderBoard += "<td>" + wins + "<td>";
            strLeaderBoard += "</tr>";
            //strLeaderBoard += "<br>Player "+(playerId++)+": "+wins+" wins";
        }
        strLeaderBoard += "</table>";
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
                txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Archer kills Joker");
                defender.getRuneDice(Rune.JOKER).setTopFace(null);
            } else if(defender.getRuneDice(Rune.BOW_MAN) != null) {
                int height = defender.getRuneDices(Rune.STAIRSANDWALLS).size();
                if(defender.getRuneDice(Rune.STAIRS) != null) {
                    height += defender.getRuneDices(Rune.WALLS).size();
                }

                //Target weakest BowMan first.
                height -= -1 + defender.getRuneDices(Rune.BOW_MAN).size();

                if(_randomGenerator.nextInt(100) <= 100.0/height) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Archer kills Archer");
                    defender.getRuneDices(Rune.BOW_MAN).get(0).setTopFace(null);
                } else {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Archer misses Archer");
                }
            } else if(defender.getRuneDice(Rune.SHIELD_BEARER) != null) {
                if(_randomGenerator.nextInt(100) <= 25) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Archer kills Shieldbearer");
                    defender.getRuneDice(Rune.SHIELD_BEARER).setTopFace(null);
                } else {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Archer misses Shieldbearer");
                }
            }
            break;
        }

        if(!attackHappened) {
            for(RuneDice attackerShieldBearer : attacker.getRuneDices(Rune.SHIELD_BEARER)) {
                //attacker.markRuneDice(attackerShieldBearer, true, false);
                
                if(defender.getRuneDice(Rune.JOKER) != null) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Shieldbearer kills Joker");
                    defender.getRuneDice(Rune.JOKER).setTopFace(null);
                } else if(defender.getRuneDice(Rune.SHIELD_BEARER) != null) {
                    if(_randomGenerator.nextInt(100) <= 50) {
                        txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Shieldbearer kills Shieldbearer");
                        defender.getRuneDice(Rune.SHIELD_BEARER).setTopFace(null);
                    } else {
                        txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Shieldbearer misses Shieldbearer");
                    }
                } else if(defender.getRuneDice(Rune.STAIRSANDWALLS) != null) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Shieldbearer climbs on towers stairs");
                    defender.getRuneDice(Rune.STAIRSANDWALLS).setTopFace(null);
                } else if(defender.getRuneDice(Rune.STAIRS) != null && defender.getRuneDice(Rune.WALLS) != null) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Shieldbearer climbs up tower");
                    defender.getRuneDice(Rune.WALLS).setTopFace(null);
                } else if(defender.getRuneDice(Rune.BOW_MAN) != null) {
                    txtCurAction.setLabelText(txtCurAction.getLabelText()+"<br>Shieldbearer kills Archer");
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
                    playerText.setLabelText("Preparing next round. "+Math.round(_waitTime)+" s");
                } else {
                    playerText.setLabelText("Game ended.");
                }
            }
        }

        if(playedRounds >= _maxRounds) {
            txtCurAction.setLabelText("Player "+this.getBestPlayer()+" wins the war!");
        }
    }   

    private void stateFightOpponent() {
        _waitTime -= 1.0 * RenderManager.getInstance().getDeltaTime();
        if(_waitTime <= 0.0) {
            _waitTime = 5.0;

            if(_attackQueue.size() <= 1) {
                _curAttacker = _attackQueue.poll();
                _state = GameState.VICTORY_SCREEN;
                _waitTime = 15.0;
                txtCurAction.setLabelText("Player "+_curAttacker.getPlayerNo()+" won the battle<br>...but not the war.");

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
            IController target = null;
            if(attackInfo != null) {
                target = attackInfo.getTarget();
            }
            if(target == null) {
                RuneDiceBag bag = null;
                int index = _randomGenerator.nextInt(_controllers.size());
                do {
                    index = index % _controllers.size();
                    target = _controllers.get(index++);
                    bag = target.getGameObject().getComponent(RuneDiceBag.class);
                } while(target == _curAttacker || bag.getRuneDices(Rune.BOW_MAN).size() == 0 && bag.getRuneDices(Rune.SHIELD_BEARER).size() == 0 && bag.getRuneDices(Rune.JOKER).size() == 0);
            }
            if(target != _curAttacker) {
                txtCurAction.setLabelText("Player "+_curAttacker.getPlayerNo()+" attacks Player "+target.getPlayerNo()+"!");

                RuneDiceBag defenderBag = target.getGameObject().getComponent(RuneDiceBag.class);
                this.fight(attackerBag, defenderBag);
            } else {
                txtCurAction.setLabelText("Player "+_curAttacker.getPlayerNo()+" refuses to fight...");
            }


            _attackQueue.offer(_curAttacker);
        }
    }

    private void stateChooseOpponent() {
        _waitTime -= 1.0 * RenderManager.getInstance().getDeltaTime();
        txtCurAction.setLabelText("Choose opponent in "+Math.round(_waitTime)+" seconds!");

        if(_waitTime <= 0.0) {
            this._waitTime = 3.0;
            this._state = GameState.FIGHT_OPPONENT;
            txtCurAction.setLabelText("May the battle begin!");
            
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
                playerText.setLabelText("Waiting for other players...");
            } else if(rollCount == 0) {
                playerText.setLabelText("Choose characters to keep.");
            }else {
                playerText.setLabelText("Last decision, before the battle begins!");
            }
            
            for(RuneDice d : dices) {
                if(controllerDone && !d.isReady()) {
                    d.setReady(true);
                }
            }
        }

        txtCurAction.setLabelText("Make your decision! "+Math.round(_waitTime)+" s");

        if(_waitTime <= 0.0 || playersAreDone == ControllerManager.getInstance().GetPlayerCount()) {
            for (IController controller : _controllers) {
                PlayerLabelGraphic playerText = controller.getGameObject().getComponent(PlayerLabelGraphic.class);
                playerText.setLabelText("Which player do you wish to attack?");
            
                AttackButtonComponent attack = controller.getGameObject().getComponentInChildren(AttackButtonComponent.class);
                attack.getGameObject().setEnabled(true);
                attack.reset();
            }

            _state = GameState.CHOOSE_OPPONENT;
            _waitTime = 20.0;
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
                
                txtCurAction.setLabelText("Make your decisions!");
                _state = GameState.MAKE_DECISION;
                _waitTime = 30.0;
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
                    playerText.setLabelText("Waiting for other players...");
                } else {
                    playerText.setLabelText("Ready Check");
                }
            }
        }

        if(playerCountReady == _maxPlayers) {
            txtCurAction.setLabelText("All players ready.<br>The battle is about to begin.");
            _waitTime = 3;
            _state = GameState.THROW_DICES;
            return;
        }
        else {
            txtCurAction.setLabelText(String.format("%d / %d players ready...<br>FPS: %f",
                playerCountReady, 
                _maxPlayers,
                RenderManager.getInstance().getDeltaTime()*1000));
        }
    }
}
