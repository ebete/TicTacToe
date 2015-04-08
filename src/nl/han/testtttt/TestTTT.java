package nl.han.testtttt;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import nl.han.tictactoe.*;
import nl.han.tictactoe.TicTacToe.State;

import nl.han.bots.*;

import java.io.IOException;

@SuppressWarnings("javadoc")
public class TestTTT {
    private TicTacToe ttt;

    @Before
    public void init() {
        ttt = new TicTacToe();
    }

    @Test
    public void startBoard() {
        assertEquals(ttt.getBoard(), 0L);
    }

    @Test
    public void placeCenter() {
        assertTrue(ttt.place(1, 1));
    }

    @Test
    public void checkBoardValue() {
        ttt.place(1, 1);
        assertEquals(ttt.getBoard(), 256L); // Checks if the only token is a X in the center.
    }

    @Test
    public void checkPlayers() {
        assertEquals(ttt.getCurrentPlayer(), State.X);
        ttt.place(1, 1);
        assertEquals(ttt.getCurrentPlayer(), State.O);
    }

    @Test
    public void clearBoard() {
        ttt.place(1, 1);
        ttt.resetBoard();
        assertEquals(ttt.getBoard(), 0L);
    }

    @Test
    public void checkOverlap() {
        assertTrue(ttt.place(1, 1));
        assertFalse(ttt.place(1, 1));
    }

    @Test
    public void winGameHorizontal() {
        // X X X
        // O _ _
        // O _ _
        ttt.place(0, 0);
        ttt.place(1, 0);
        ttt.place(0, 1);
        ttt.place(2, 0);
        ttt.place(0, 2);

        assertNotEquals(ttt.getWinner(), State.BLANK);
    }

    @Test
    public void winGameVertical() {
        // X O O
        // X _ _
        // X _ _
        ttt.place(0, 0);
        ttt.place(0, 1);
        ttt.place(1, 0);
        ttt.place(0, 2);
        ttt.place(2, 0);

        assertNotEquals(ttt.getWinner(), State.BLANK);
    }

    @Test
    public void winGameDiagonal() {
        // X _ _
        // O X _
        // O _ X
        ttt.place(0, 0);
        ttt.place(1, 0);
        ttt.place(1, 1);
        ttt.place(2, 0);
        ttt.place(2, 2);

        assertNotEquals(ttt.getWinner(), State.BLANK);
    }

    @Test
    public void winGameCross() {
        // X _ _
        // O X _
        // O _ X
        ttt.place(0, 0);
        ttt.place(1, 0);
        ttt.place(1, 1);
        ttt.place(2, 0);
        ttt.place(2, 2);

        assertEquals(ttt.getWinner(), State.X);
    }

    @Test
    public void winGameCircle() {
        // X X _
        // O O O
        // X _ _
        ttt.place(0, 0);
        ttt.place(1, 0);
        ttt.place(2, 0);
        ttt.place(1, 1);
        ttt.place(0, 1);
        ttt.place(1, 2);

        assertEquals(ttt.getWinner(), State.O);
    }

    @Test
    public void drawGame() {
        // X O X
        // O X X
        // O X O
        ttt.place(0, 0);
        ttt.place(0, 1);
        ttt.place(0, 2);
        ttt.place(1, 0);
        ttt.place(1, 1);
        ttt.place(2, 0);
        ttt.place(1, 2);
        ttt.place(2, 2);
        ttt.place(2, 1);

        assertEquals(ttt.getWinner(), State.DRAW);
    }

    @Test
    public void playRandomBot() {
        Bot bot = new BotRandom();

        bot.doMove(ttt);

        assertNotEquals(ttt.getBoard(), 0L);
    }

    @Test(timeout = 50)
    public void playRandomBotGame() {
        Bot playerX = new BotRandom();
        Bot playerO = new BotRandom();

        while(ttt.getWinner() == State.BLANK) {
            if(ttt.getCurrentPlayer() == State.X)
                playerX.doMove(ttt);
            else
                playerO.doMove(ttt);
        }
    }

    @Test(timeout = 50)
    public void playRandomLearningBotGame() {
        Bot playerX = new BotRandom();
        Bot playerO = new BotLearning();

        while(ttt.getWinner() == State.BLANK) {
            if(ttt.getCurrentPlayer() == State.X)
                playerX.doMove(ttt);
            else
                playerO.doMove(ttt);
        }
    }

    @Test
    public void writeBoardAfterManualSet() {
        ttt.setBoard(16417L);
        ttt.drawBoard();
        assertEquals(ttt.getBoard(), 16417L);
    }

    @Test(timeout = 30000)
    public void playRandomVersusLearningGameAndExport() {
        int rounds = 1000000;
        Bot playerX = new BotRandom();
        Bot playerO = new BotLearning();

        for (int i = 0; i < rounds; i++) {
            while(ttt.getWinner() == State.BLANK) {
                if(ttt.getCurrentPlayer() == State.X)
                    playerX.doMove(ttt);
                else
                    playerO.doMove(ttt);
            }
            
            switch(ttt.getWinner()) {
                case X:
                    playerX.roundEnd(1);
                    playerO.roundEnd(-1);
                    break;

                case O:
                    playerX.roundEnd(-1);
                    playerO.roundEnd(1);
                    break;

                case DRAW:
                    playerX.roundEnd(0);
                    playerO.roundEnd(0);
                    break;
            }

            ttt.resetBoard();
        }

        try {
            ((BotLearning) playerO).exportTree();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        System.out.println(String.format("%s vs %s game x%d:", playerX.getName(), playerO.getName(), rounds));
        System.out.println(String.format("Bot X: %d-%d-%d. Rate: %.1f%%",
                playerX.getRoundsWon(),
                playerX.getRoundsLost(),
                playerX.getRoundsDraw(),
                playerX.getWinRate() * 100));
        System.out.println(String.format("Bot O: %d-%d-%d. Rate: %.1f%%",
                playerO.getRoundsWon(),
                playerO.getRoundsLost(),
                playerO.getRoundsDraw(),
                playerO.getWinRate() * 100));
    }

    @Test(timeout = 30000)
    public void playLearningVersusLearningGame() {
        int rounds = 1000000;
        Bot playerX = new BotLearning();
        Bot playerO = new BotLearning();

        for (int i = 0; i < rounds; i++) {
            while(ttt.getWinner() == State.BLANK) {
                if(ttt.getCurrentPlayer() == State.X)
                    playerX.doMove(ttt);
                else
                    playerO.doMove(ttt);
            }

            switch(ttt.getWinner()) {
                case X:
                    playerX.roundEnd(1);
                    playerO.roundEnd(-1);
                    break;

                case O:
                    playerX.roundEnd(-1);
                    playerO.roundEnd(1);
                    break;

                case DRAW:
                    playerX.roundEnd(0);
                    playerO.roundEnd(0);
                    break;
            }

            ttt.resetBoard();
        }

        System.out.println(String.format("%s vs %s game x%d:", playerX.getName(), playerO.getName(), rounds));
        System.out.println(String.format("Bot X: %d-%d-%d. Rate: %.1f%%",
                playerX.getRoundsWon(),
                playerX.getRoundsLost(),
                playerX.getRoundsDraw(),
                playerX.getWinRate() * 100));
        System.out.println(String.format("Bot O: %d-%d-%d. Rate: %.1f%%",
                playerO.getRoundsWon(),
                playerO.getRoundsLost(),
                playerO.getRoundsDraw(),
                playerO.getWinRate() * 100));
    }
}
