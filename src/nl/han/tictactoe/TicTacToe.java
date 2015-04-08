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
    public enum State {
        /** Empty */  // 0b00
        BLANK,
        /** Cross */  // 0b01
        X,
        /** Circle */ // 0b10
        O,
        /** Draw */
        DRAW
    }

    /** Dimensions of the board. */
    public final byte BOARD_DIMS = 3;

    /** The current player. */
    private State _currentPlayer = State.X;
    /** The winner of this round or {@code State.BLANK} if no winner has been decided yet. */
    private State _winner = State.BLANK;
    /** Move counter for this round. */
    private short _moves = 0;
    /** The unique board state value. */
    private long _board = 0;

    /** The 2 bit representation of a blank cell. */
    private final int _blank = 0b00;
    /** The 2 bit representation of the token X. */
    private final int _x = 0b01;
    /** The 2 bit representation of the token O. */
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
        if(row < 0 || row >= BOARD_DIMS)
            return false;
        if(column < 0 || column >= BOARD_DIMS)
            return false;
        
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

    /**
     * Derives the token to place from the given
     * value of the new board.
     * 
     * @param newBoard The board value to create.
     * @return true if the action was successful, otherwise false.
     */
    public boolean place(long newBoard) {
        // No change.
        if(newBoard == _board)
            return false;
        
        boolean singleChange = false;
        
        int row = -1;
        int col = -1;
        
        for(int pos = 0; pos < Math.pow(BOARD_DIMS, 2)*2; pos+=2) {
            long mask = 0b11 << pos;
            int curVal = (int) ((newBoard & mask) >> pos);
            if((int) ((_board & mask) >> pos) != curVal) {
                // Checks if this is the only required board change.
                if(singleChange)
                    return false;
                
                // Checks if the found player equals the current player.
                if(curVal != getCurrentPlayerAsInt())
                    return false;
                
                // Derives the position.
                row = (pos/2) % BOARD_DIMS;
                col = (pos/2) / BOARD_DIMS;
                singleChange = true;
            }
        }
        
        return place(row, col);
    }

    /**
     * Sets the given cell to the given value. Note
     * that the given value shouldn't exceed 2 bits in length.
     * (i.e. the values 0, 1, 2 and 3).
     * 
     * @param row The cell row.
     * @param column The cell column.
     * @param value The value to set the cell to.
     */
    private void setPosition(int row, int column, int value) {
        int pos = (row + (column * BOARD_DIMS)) * 2;
        long mask = 0b11 << pos;
        mask = ~mask;

        _board = (_board & mask) | (value << pos);
    }

    /**
     * Gets the {@code int} representation of the cell
     * state at the requested location.
     * 
     * @param row The cell row.
     * @param column The cell column.
     * @return The cell state value.
     */
    private int getPosition(int row, int column) {
        int pos = (row + (column * BOARD_DIMS)) * 2;
        long mask = 0b11 << pos;
        return (int) ((_board & mask) >> pos);
    }

    /**
     * Checks if the cell at the given location is empty.
     * 
     * @param row The cell row.
     * @param column The cell column.
     * @return True if the cell is empty, otherwise false.
     */
    private boolean positionEmpty(int row, int column) {
        return getPosition(row, column) == _blank;
    }

    /**
     * Gets the token at the requested position.
     *
     * @param row The cell row.
     * @param column The cell column.
     * @return The token at the requested position.
     */
    public State getToken(int row, int column) {
        int pos = getPosition(row, column);

        if(pos == _x)
            return State.X;
        if(pos == _o)
            return State.O;
        return State.BLANK;
    }
    
    public String getStringToken(int row, int column) {
        int pos = getPosition(row, column);

        if(pos == _x)
            return "X";
        if(pos == _o)
            return "O";
        return " ";
    }

    /**
     * Checks for a win situation on the last move.
     * 
     * @param row The row of the last move.
     * @param column The column of the last move.
     */
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

        if(row == column || row == BOARD_DIMS-1-column) {
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

    /** Toggles the current player. */
    private void togglePlayer() {
        if(_currentPlayer == State.X)
            _currentPlayer = State.O;
        else
            _currentPlayer = State.X;
        ++_moves;
    }

    /** @return The board state. Equals {@code 0L} at the start. */
    public long getBoard() {
        return _board;
    }

    /**
     * Sets the board to the given state value.
     * It will reset all stats and set
     * the win state to {@code State.DRAW}.
     * 
     * @param board The board state value.
     */
    public void setBoard(long board) {
        resetBoard();
        _board = board;
        _winner = State.DRAW;
    }

    /** @return The current player. */
    public State getCurrentPlayer() {
        return _currentPlayer;
    }

    /** @return The integer representation of the current player. */
    private int getCurrentPlayerAsInt() {
        switch(_currentPlayer) {
            case O:
                return _o;
            case X:
                return _x;
            default:
                return _blank;
        }
    }

    /** @return The move count. */
    public short getMoves() {
        return _moves;
    }

    /** @return The winner. */
    public State getWinner() {
        return _winner;
    }
    
    /** Draws the board in de stdout. */
    public void drawBoard() {
        for(int i = 0; i < BOARD_DIMS; i++) {
            System.out.print("+---+---+---+\n|");
            for(int j = 0; j < BOARD_DIMS; j++) {
                System.out.print(String.format(" %s |", getStringToken(j, i)));
            }
            System.out.println();
        }
        System.out.println("+---+---+---+");
    }

    @Override
    public String toString() {
        return String.format("TicTacToe {Dims=%d; Winner=%s; Turn=%s; Moves=%d; Board=%d}",
                BOARD_DIMS,
                _winner,
                _currentPlayer,
                _moves,
                _board);
    }
}