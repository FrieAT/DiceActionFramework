import DAF.Components.AbstractComponent;
import DAF.Controller.Components.ControllerView;
import DAF.Controller.Components.IController;
import DAF.Controller.Components.PlayerController;
import DAF.Dice.Components.ADice;
import DAF.Dice.Components.ADiceBag;
import DAF.GameObject;
import DAF.Renderer.Components.LabelGraphic;

import java.util.ArrayList;
import java.util.Objects;

public abstract class ARound extends AbstractComponent {

    private ArrayList<RoundPlayer> players;
    private int currentPlayer;
    private int turns;
    private int currentRound;
    private int rounds;

    private java.util.Timer _timer;

    /**
     * Default round number = 3;
     */
    public ARound() {
        this.players = new ArrayList<>();
        currentPlayer = -1;
        turns = 0;
        currentRound = 1;
        rounds = 3;
    }

    public void setRounds(int roundNumber) {
        this.currentRound = 1;
        this.rounds = roundNumber;
    }

    public void addPlayer(int playerId) {
        if (players.isEmpty())
            currentPlayer = playerId;
        players.add(new RoundPlayer(playerId, false));
        turns++;
    }

    public void addPlayer(RoundPlayer player) {
        if (players.isEmpty())
            currentPlayer = player.getId();
        players.add(player);
        turns++;
    }

    public RoundPlayer removePlayer(int playerId) {
        turns--;
        RoundPlayer help;
        for (RoundPlayer player : players) {
            if (player.getId() == playerId) {
                help = player;
                players.remove(player);
                return help;
            }
        }
        return null;
    }

    public void play(int id) {
        if (currentPlayer == id) {
            int nextIdx = players.indexOf(findPlayer(id)) + 1;
            if (nextIdx >= players.size()) {
                currentRound++;
                turns--;
                if (turns <= 0 && rounds - currentRound >= 0) {
                    GameObject go_label = GameObject.find("Label_roundCount");
                    LabelGraphic label = go_label.getComponent(LabelGraphic.class);
                    label.setLabelText("Round " + currentRound + " of " + rounds);
                    go_label.setEnabled(true);
                    changeControllerIndexPublic();

                    restart();
                }
                if (gameHasEnded())
                    stop();
                return;
            }
            GameObject go_label = GameObject.find("Label_playerTurn");
            LabelGraphic label = go_label.getComponent(LabelGraphic.class);
            label.setLabelText("Player " + currentPlayer + "'s turn");

            if (this._timer != null)
                this._timer.cancel();
            this._timer = new java.util.Timer();
            this._timer.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            go_label.setEnabled(false);
                        }
                    }, 1000*5, 1000*5);

            go_label.setEnabled(true);
            currentPlayer = players.get(nextIdx).getId();
            turns--;
            //currentPlayer = players.get(nextIdx).getId();
            //turns--;
        }
    }

    public void changeControllerIndexPublic() {
        for (RoundPlayer p: players) {
            p.getDice().getGameObject().getComponent(ControllerView.class).setController(0);

        }
    }



    public boolean isPlayersTurn(int playerId) {
        return playerId == currentPlayer;
    }

    public RoundPlayer findPlayer(int playerId) {

        for (RoundPlayer player : players) {
            if (player.getId() == playerId) {
                return player;
            }
        }

        return null;
    }

    @Override
    public void start() {
        GameObject go_label = GameObject.find("Label_roundCount");
        LabelGraphic label = go_label.getComponent(LabelGraphic.class);
        label.setLabelText("Round " + currentRound + " of " + rounds);
        go_label.setEnabled(true);

        go_label = GameObject.find("Label_playerTurn");
        label = go_label.getComponent(LabelGraphic.class);
        label.setLabelText("Player " + currentPlayer + "'s turn");
        go_label.setEnabled(true);

        RoundManager.getInstance().add(this);
    }

    @Override
    public void update() {
        if (roundHasEnded())
            System.out.println("Round " + rounds + " has ended.");
        if (gameHasEnded())
            System.out.println("Game has ended.");

    }

    public boolean roundHasEnded() {
        return turns == 0;
    }

    public boolean gameHasEnded() {
        return currentRound == rounds && turns == 0;
    }

    public void restart() {
        for (RoundPlayer player : players)
            player.setHasPlayed(false);

        turns = players.size();
        assert players.size() >= 2;
        currentPlayer = players.get(1).getId();
        RoundPlayer lastPlayer = players.remove(0);
        players.add(lastPlayer);
    }

    public void stop() {
        currentPlayer = -1;
    }

}
