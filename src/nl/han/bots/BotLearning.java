package nl.han.bots;

import nl.han.tictactoe.TicTacToe;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;

/**
 * Bot for Tic Tac Toe. This bot learns from played rounds.
 *
 * @author Thom Griffioen
 */
public class BotLearning implements Bot {
    private int _roundsPlayed = 0;
    private int _roundsWon = 0;
    
    private LearningGraphNode graph = new LearningGraphNode(null, 0L);

    private void randomMove(TicTacToe game) {
        int row;
        int col;

        do {
            row = new Random().nextInt(game.BOARD_DIMS);
            col = new Random().nextInt(game.BOARD_DIMS);
        } while(!game.place(row, col));
    }

    @Override
    public void doMove(TicTacToe game) {
        //TODO: Finalize implementation for move calculation.
        if(game.getBoard() != 0L)
            graph = graph.moveToNode(game.getBoard());

        if(graph.calculateBestMove() == null) {
            randomMove(game);
            graph = graph.moveToNode(game.getBoard());
        } else {
            graph = graph.calculateBestMove();
        }
    }

    /**
     * Export the tree to a file.
     *
     * @return True on success, otherwise false.
     */
    private boolean exportTree() {
        // TODO: Add the tree export functionality.
        throw new NotImplementedException();
    }

    /**
     * Import the tree from a file.
     *
     * @return True on success, otherwise false.
     */
    private boolean importTree() {
        // TODO: Add the tree import functionality.
        throw new NotImplementedException();
    }

    @Override
    public void roundEnd(int won) {
        ++_roundsPlayed;
        if(won > 0) {
            graph.winSituation();
            ++_roundsWon;
        } else if(won < 0) {
            graph.loseSituation();
        }
        
        // Reset the graph for the next round.
        while(graph.getParentNode() != null)
            graph = graph.getParentNode();
    }

    @Override
    public float getWinRate() {
        return (float)_roundsWon / (float)_roundsPlayed;
    }

    @Override
    public String getName() { return "Bot learning"; }
}
