public class RoundPlayer {
    private int id;
    private boolean hasPlayed;

    public RoundPlayer (int id, boolean hasPlayed) {
        this.id = id;
        this.hasPlayed = hasPlayed;
    }

    public boolean hasPlayed() {
        return hasPlayed;
    }

    public int getId() {
        return id;
    }

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    public void setId(int id) {
        this.id = id;
    }
}
