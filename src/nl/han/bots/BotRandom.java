package nl.han.bots;

import nl.han.tictactoe.TicTacToe;

import java.util.Random;

/**
 * Bot for Tic Tac Toe. This bot is random.
 *
 * @author Thom Griffioen
 */
public class BotRandom implements Bot {
    private int _roundsPlayed = 0;
    private int _roundsWon = 0;
    
    @Override
    public void doMove(TicTacToe game) {
        int row;
        int col;

        do {
            row = new Random().nextInt(game.BOARD_DIMS);
            col = new Random().nextInt(game.BOARD_DIMS);
        } while(!game.place(row, col));
    }

    @Override
    public void roundEnd(int won) {
        ++_roundsPlayed;
        if(won > 0)
            ++_roundsWon;
    }

    @Override
    public float getWinRate() {
        return (float)_roundsWon / (float)_roundsPlayed;
    }

    @Override
    public String getName() {
        return "Bot random";
    }
}
