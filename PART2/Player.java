//Player class to store player number and respective scores

public class Player {
    private int playerNumber;
    private int score;

    public Player(int playerNumber, int score) {
        this.playerNumber = playerNumber;
        this.score = score;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}