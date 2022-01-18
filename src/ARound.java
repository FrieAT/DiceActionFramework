import DAF.Components.AbstractComponent;

import java.util.ArrayList;

public abstract class ARound extends AbstractComponent {

    private ArrayList<RoundPlayer> players;
    private int currentPlayer;
    private int turns;
    private int rounds;

    /**
     * Default round number = 3;
     */
    public ARound() {
        this.players = new ArrayList<>();
        currentPlayer = -1;
        turns = 0;
        rounds = 3;
    }

    public void setRounds(int roundNumber) {
        this.rounds = roundNumber;
    }

    public void addPlayer(int playerId) {
        if (players.isEmpty())
            currentPlayer = playerId;
        players.add(new RoundPlayer(playerId, false));
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
                System.out.println(currentPlayer);
                rounds--;
                turns--;
                if (turns <= 0 && rounds > 0)
                    restart();
                if (gameHasEnded())
                    stop();
                return;
            }
            currentPlayer = players.get(nextIdx).getId();
            turns--;
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
        RoundManager.getInstance().add(this);
    }

    @Override
    public void update() {
        if (roundHasEnded())
            System.out.println("Round" + rounds + " has ended.");
        if (gameHasEnded())
            System.out.println("Game has ended.");

    }

    public boolean roundHasEnded() {
        return turns == 0;
    }

    public boolean gameHasEnded() {
        return rounds == 0 && turns == 0;
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
