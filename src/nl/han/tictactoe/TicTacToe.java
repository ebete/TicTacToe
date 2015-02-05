package nl.han.tictactoe;

/**
 * An object for playing a game of Tic Tac Toe.
 *
 * @author Thom Griffioen
 */
public class TicTacToe {
    /**
     * Represents the state of the winner or the cell's
     * contents.
     *
     * @author Thom Griffioen
     */
    public enum State {/** Empty */  BLANK, // 0b00
                       /** Cross */  X,     // 0b01
                       /** Circle */ O,     // 0b10
                       /** Draw */   DRAW};
    /** Dimensions of the board. */
    public final byte BOARD_DIMS = 3;

    private State _currentPlayer = State.X;
    private State _winner = State.BLANK;
    private short _moves = 0;
    private long _board = 0;

    private final int _blank = 0b00;
    private final int _x = 0b01;
    private final int _o = 0b10;

    /**
     * Initializes the gameboard with the size of {@code BOARD_DIMS}.
     * {@code State.X} is the starting player.
     */
    public TicTacToe() {
        resetBoard();
    }

    /**
     * Re-initializes the board, resetting all cells and counters.
     *
     * @return The state of the board. Should be {@code 0L}.
     */
    public long resetBoard() {
        _moves = 0;
        _board = 0L;
        _winner = State.BLANK;
        _currentPlayer = State.X;

        for(int i = 0; i < Math.pow(BOARD_DIMS, 2); i++) {
            _board <<= 2;
            _board |= _blank;
        }

        return _board;
    }

    /**
     * Tries to place a token in the given cell at
     * the given {@code row} and {@code column}.
     * Returns true on success.
     *
     * @param row The row.
     * @param column The column.
     * @return true if the action was successful, otherwise false.
     */
    public boolean place(int row, int column) {
        if(!positionEmpty(row, column))
            return false;

        if(_currentPlayer == State.X)
            setPosition(row, column, _x);
        else
            setPosition(row, column, _o);

        checkWin(row, column);
        togglePlayer();
        return true;
    }

    private void setPosition(int row, int column, int value) {
        int pos = (row + (column * BOARD_DIMS)) * 2;
        long mask = 0b11 << pos;
        mask = ~mask;

        _board = (_board & mask) | (value << pos);
    }

    private int getPosition(int row, int column) {
        int pos = (row + (column * BOARD_DIMS)) * 2;
        long mask = 0b11 << pos;
        return (int) ((_board & mask) >> pos);
    }

    private boolean positionEmpty(int row, int column) {
        return getPosition(row, column) == _blank;
    }

    private State getToken(int row, int column) {
        int pos = getPosition(row, column);

        if(pos == _x)
            return State.X;
        if(pos == _o)
            return State.O;
        return State.BLANK;
    }

    private void checkWin(int row, int column) {
        // Check horizontal win.
        for(int i = 0; i < BOARD_DIMS; i++) {
            if(getToken(i, column) != _currentPlayer)
                break;
            if(i == BOARD_DIMS-1) {
                _winner = _currentPlayer;
                return;
            }
        }

        // Check vertical win.
        for(int i = 0; i < BOARD_DIMS; i++) {
            if(getToken(row, i) != _currentPlayer)
                break;
            if(i == BOARD_DIMS-1) {
                _winner = _currentPlayer;
                return;
            }
        }

        if(row == column) {
            // Check diagonal win.
            for(int i = 0; i < BOARD_DIMS; i++) {
                if(getToken(i, i) != _currentPlayer)
                    break;
                if(i == BOARD_DIMS-1) {
                    _winner = _currentPlayer;
                    return;
                }
            }
            // Check anti-diagonal win.
            for(int i = 0; i < BOARD_DIMS; i++) {
                if(getToken(i, (BOARD_DIMS-1)-i) != _currentPlayer)
                    break;
                if(i == BOARD_DIMS-1) {
                    _winner = _currentPlayer;
                    return;
                }
            }
        }

        // Check for draw.
        if(_moves == Math.pow(BOARD_DIMS, 2)-1)
            _winner = State.DRAW;
    }

    private void togglePlayer() {
        if(_currentPlayer == State.X)
            _currentPlayer = State.O;
        else
            _currentPlayer = State.X;
        ++_moves;
    }

    /** @return The board state. */
    public long getBoard() {
        return _board;
    }

    /** @return The current player. */
    public State getCurrentPlayer() {
        return _currentPlayer;
    }

    /** @return The move count. */
    public short getMoves() {
        return _moves;
    }

    /** @return The winner. */
    public State getWinner() {
        return _winner;
    }
}