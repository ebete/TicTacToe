package nl.han.bots;

import nl.han.tictactoe.TicTacToe;

/**
 * Base class for all bots.
 *
 * @author Thom Griffioen
 */
abstract public class Bot {
    protected int roundsPlayed = 0;
    protected int roundsWon = 0;
    protected int roundsLost = 0;

    /**
     * Called every time the bot has to execute
     * a move.
     *
     * @param game The current game being played.
     */
    abstract public void doMove(TicTacToe game);

    /**
     * Called at the end of each round.
     *
     * @param won 1 for a win, 0 for draw and -1 for lose.
     */
    public void roundEnd(int won) {
        ++this.roundsPlayed;
        if(won > 0)
            ++this.roundsWon;
        else if(won < 1)
            ++this.roundsLost;
    }

    /**
     * Gets the number of rounds the bot has finished.
     *
     * @return Number of finished rounds.
     */
    public int getRoundsPlayed() {
        return this.roundsPlayed;
    }

    /**
     * Gets the number of rounds the bot won.
     *
     * @return Number of won rounds.
     */
    public int getRoundsWon() {
        return this.roundsWon;
    }

    /**
     * Gets the number of rounds the bot lost.
     *
     * @return Number of won lost.
     */
    public int getRoundsLost() {
        return this.roundsLost;
    }

    /**
     * Gets the number of rounds with no winner.
     *
     * @return Number of draw rounds.
     */
    public int getRoundsDraw() {
        return this.roundsPlayed - (this.roundsWon + this.roundsLost);
    }

    /**
     * Returns the win rate of the bot.
     *
     * @return The win rate.
     */
    public float getWinRate(){
        return (float)this.roundsWon / (float)this.roundsPlayed;
    }

    /**
     * Gets the name of the bot.
     * @return The name.
     */
    abstract public String getName();
}
