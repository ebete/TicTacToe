package nl.han.bots;

import nl.han.tictactoe.TicTacToe;

/**
 * Interface used by all bots.
 *
 * @author Thom Griffioen
 */
public interface Bot {
    /**
     * Called every time the bot has to execute
     * a move.
     *
     * @param game The current game being played.
     */
    public void doMove(TicTacToe game);

    /**
     * Called at the end of each round.
     *
     * @param won True if the bot won that round, otherwise false.
     */
    public void roundEnd(boolean won);

    /**
     * Returns the win rate of the bot.
     *
     * @return The win rate.
     */
    public float getWinRate();

    /**
     * Gets the name of the bot.
     * @return The name
     */
    public String getName();
}
