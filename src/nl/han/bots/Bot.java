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
     * Gets the name of the bot.
     * @return The name
     */
    public String getName();
}
