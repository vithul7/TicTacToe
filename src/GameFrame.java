import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class GameFrame extends JFrame implements MouseListener{
    GamePanel newGP;
    GameFrame() throws IOException {
        newGP = new GamePanel();
        this.add(newGP);
        this.setTitle("Tic Tac Toe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.addMouseListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {


    }
    @Override
    public void mousePressed(MouseEvent e) {
        char firstPlayer = newGP.PLAYER_ONE;
        char secondPlayer = newGP.PLAYER_TWO;
        if (newGP.turn == secondPlayer) {
            if (e.getX() < 80) {
                if (e.getY() < 110) {
                    if (insertChar(0, 0, secondPlayer)) newGP.turn = firstPlayer;
                } else if (e.getY() < 175) {
                    if (insertChar(1, 0, secondPlayer)) newGP.turn = firstPlayer;
                } else {
                    if (insertChar(2, 0, secondPlayer)) newGP.turn = firstPlayer;
                }
            } else if (e.getX() < 147) {
                if (e.getY() < 110) {
                    if (insertChar(0, 1, secondPlayer)) newGP.turn = firstPlayer;
                } else if (e.getY() < 175) {
                    if (insertChar(1, 1, secondPlayer)) newGP.turn = firstPlayer;
                } else {
                    if (insertChar(2, 1, secondPlayer)) newGP.turn = firstPlayer;
                }
            } else {
                if (e.getY() < 110) {
                    if (insertChar(0, 2, secondPlayer)) newGP.turn = firstPlayer;
                } else if (e.getY() < 175) {
                    if (insertChar(1, 2, secondPlayer)) newGP.turn = firstPlayer;
                } else {
                    if (insertChar(2, 2, secondPlayer)) newGP.turn = firstPlayer;
                }
            }
        }



    }

    private boolean insertChar(int row, int column, char toInsert) {
        if (newGP.board[row][column] == ' ') {
            newGP.board[row][column] = toInsert;
            return true;
        }
        return false;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub     
    }
}