package nl.han;

import nl.han.bots.Bot;
import nl.han.bots.BotLearning;
import nl.han.tictactoe.TicTacToe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton trainBotButton;
    private JButton resetBotButton;
    private JButton resetBoardButton;

    private TicTacToe ttt;
    private Bot playerO;

    public GameForm() {
        initializeComponents();

        ttt = new TicTacToe();
        playerO = new BotLearning();
        drawGame();
    }

    private void initializeComponents() {
        setTitle("Tic Tac Toe");
        setContentPane(pnlRoot);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 300, 300);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        button1.addActionListener(listener);
        button2.addActionListener(listener);
        button3.addActionListener(listener);
        button4.addActionListener(listener);
        button5.addActionListener(listener);
        button6.addActionListener(listener);
        button7.addActionListener(listener);
        button8.addActionListener(listener);
        button9.addActionListener(listener);

        trainBotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        resetBotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        resetBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ttt.resetBoard();
                drawGame();
            }
        });
    }

    private void drawGame() {
        button1.setText(ttt.getStringToken(0, 0));
        button2.setText(ttt.getStringToken(0, 1));
        button3.setText(ttt.getStringToken(0, 2));
        button4.setText(ttt.getStringToken(1, 0));
        button5.setText(ttt.getStringToken(1, 1));
        button6.setText(ttt.getStringToken(1, 2));
        button7.setText(ttt.getStringToken(2, 0));
        button8.setText(ttt.getStringToken(2, 1));
        button9.setText(ttt.getStringToken(2, 2));
    }
}
