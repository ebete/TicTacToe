package nl.han;

import javax.swing.*;
import java.awt.*;

/**
 * TicTacToe:nl.han
 *
 * @author Thom
 */
public class GameForm extends JFrame {
    private JPanel pnlRoot;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;

    public GameForm() {
        setTitle("Tic Tac Toe");
        setContentPane(pnlRoot);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 300, 300);
    }
}
