import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class GameFrame extends JFrame implements MouseListener{
    TicTacToeBoard newGP;
    GameFrame() throws IOException {
        newGP = new TicTacToeBoard();
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
        if (newGP.turn == newGP.HUMAN_MOVE) {
            if (validMove(newGP.HUMAN_MOVE, e.getX(), e.getY())) {
                newGP.turn = newGP.BOT_MOVE;
                newGP.movesLeft--;
            }
        }

    }

    private boolean validMove(char playerMove, int mouseX, int mouseY) {
        if (mouseX < 80) {
            if (mouseY < 110) {
                if (insertChar(0, 0, playerMove)) return true;
            } else if (mouseY < 175) {
                if (insertChar(1, 0, playerMove)) return true;
            } else {
                if (insertChar(2, 0, playerMove)) return true;
            }
        } else if (mouseX < 147) {
            if (mouseY < 110) {
                if (insertChar(0, 1, playerMove)) return true;
            } else if (mouseY < 175) {
                if (insertChar(1, 1, playerMove)) return true;
            } else {
                if (insertChar(2, 1, playerMove)) return true;
            }
        } else {
            if (mouseY < 110) {
                if (insertChar(0, 2, playerMove)) return true;
            } else if (mouseY < 175) {
                if (insertChar(1, 2, playerMove)) return true;
            } else {
                if (insertChar(2, 2, playerMove)) return true;
            }
        }
        return false;
    }



    private boolean insertChar(int row, int column, char toInsert) {
        int position = (row * 3) + column;
        if (newGP.board[position] == ' ') {
            newGP.board[position] = toInsert;
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