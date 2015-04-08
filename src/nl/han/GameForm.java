package nl.han;

import nl.han.bots.*;
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
    private static Bot trainingBot = new BotRandom();

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
                setTitle("Tic Tac Toe - Bot learning factor: " + playerO.getRoundsPlayed());
                boolean validmove = false;

                if(e.getSource() == button1)
                    validmove = ttt.place(0, 0);
                else if(e.getSource() == button2)
                    validmove = ttt.place(0, 1);
                else if(e.getSource() == button3)
                    validmove = ttt.place(0, 2);
                else if(e.getSource() == button4)
                    validmove = ttt.place(1, 0);
                else if(e.getSource() == button5)
                    validmove = ttt.place(1, 1);
                else if(e.getSource() == button6)
                    validmove = ttt.place(1, 2);
                else if(e.getSource() == button7)
                    validmove = ttt.place(2, 0);
                else if(e.getSource() == button8)
                    validmove = ttt.place(2, 1);
                else if(e.getSource() == button9)
                    validmove = ttt.place(2, 2);

                if(!validmove)
                    return;

                drawGame();
                if(ttt.getWinner() != TicTacToe.State.BLANK) {
                    JOptionPane.showMessageDialog(pnlRoot, "The winner is: "+ttt.getWinner(), "Round end!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                playerO.doMove(ttt);
                drawGame();
                if(ttt.getWinner() != TicTacToe.State.BLANK) {
                    JOptionPane.showMessageDialog(pnlRoot, "The winner is: "+ttt.getWinner(), "Round end!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
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
                trainBotButton.setText("Training...");
                int rounds = 100000;

                for (int i = 0; i < rounds; i++) {
                    while(ttt.getWinner() == TicTacToe.State.BLANK) {
                        if(ttt.getCurrentPlayer() == TicTacToe.State.X)
                            trainingBot.doMove(ttt);
                        else
                            playerO.doMove(ttt);
                    }

                    switch(ttt.getWinner()) {
                        case X:
                            trainingBot.roundEnd(1);
                            playerO.roundEnd(-1);
                            break;

                        case O:
                            trainingBot.roundEnd(-1);
                            playerO.roundEnd(1);
                            break;

                        case DRAW:
                            trainingBot.roundEnd(0);
                            playerO.roundEnd(0);
                            break;
                    }

                    ttt.resetBoard();
                }
                trainBotButton.setText("Train bot");
            }
        });

        resetBotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerO = new BotLearning();
            }
        });

        resetBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(ttt.getWinner()) {
                    case X:
                        playerO.roundEnd(-1);
                        break;

                    case O:
                        playerO.roundEnd(1);
                        break;

                    case DRAW:
                        playerO.roundEnd(0);
                        break;
                }

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
