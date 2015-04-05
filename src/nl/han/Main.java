package nl.han;

import java.awt.*;

/**
 * TicTacToe:nl.han
 *
 * @author Thom Griffioen
 */
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameForm form = new GameForm();
                form.setVisible(true);
            }
        });
    }
}
