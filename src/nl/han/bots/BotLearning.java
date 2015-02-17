package nl.han.bots;

import nl.han.tictactoe.TicTacToe;

import java.util.Random;

/**
 * Bot for Tic Tac Toe. This bot learns from played rounds.
 *
 * @author Thom Griffioen
 */
public class BotLearning implements Bot {
    @Override
    public void doMove(TicTacToe game) {
        if(!calculateMove(game))
            randomMove(game);
    }
    
    private void randomMove(TicTacToe game) {
        int row;
        int col;

        do {
            row = new Random().nextInt(game.BOARD_DIMS);
            col = new Random().nextInt(game.BOARD_DIMS);
        } while(!game.place(row, col));
        
    }
    
    private boolean calculateMove(TicTacToe game) {
        return false;
    }

    @Override
    public String getName() { return "Bot learning"; }
}
