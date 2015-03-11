package nl.han.bots;

import nl.han.tictactoe.TicTacToe;

import java.util.Random;

/**
 * Bot for Tic Tac Toe. This bot is random.
 *
 * @author Thom Griffioen
 */
public class BotRandom extends Bot {
    
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
    public String getName() {
        return "Bot random";
    }
}
