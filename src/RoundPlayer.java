import DAF.Dice.Components.ADice;

public class RoundPlayer {
    private int _id;
    private boolean _hasPlayed;

    ADice _dice;

    public RoundPlayer (int id, boolean hasPlayed) {
        this._id = id;
        this._hasPlayed = hasPlayed;
    }

    public RoundPlayer (int id, ADice dice) {
        this._id = id;
        this._dice = dice;
        this._hasPlayed = false;
    }

    public boolean hasPlayed() {
        return _hasPlayed;
    }

    public int getId() {
        return _id;
    }

    public void setHasPlayed(boolean hasPlayed) {
        this._hasPlayed = hasPlayed;
    }

    public void setId(int id) {
        this._id = id;
    }

    public void setDice(ADice dice) {
        this._dice = dice;
    }

    public ADice getDice() {
        return this._dice;
    }
}
