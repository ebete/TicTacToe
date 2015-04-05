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
