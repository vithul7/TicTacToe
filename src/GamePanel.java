import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements ActionListener {
    char[][] board = new char[3][3];
    Timer timer;
    int panelHeight = 225;
    int panelWidth = 225;
    BufferedImage emptyBoard;
    char turn;
    final char PLAYER_ONE = 'x';
    final char PLAYER_TWO = 'o';

    GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(panelHeight, panelWidth));
        this.setBackground(Color.white);
        this.setFocusable(true);
        timer = new Timer(1000, this); // every 0.001 second, it'll look inside this GamePanel object for the //
        // actionPerformed method and call it
        timer.start(); // activating the timer

        // creating the board with empty values
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }

        // creating empty board picture
        emptyBoard = ImageIO
                .read(new File("/Users/vithulravivarma/Desktop/TicTacToe/src/tic tac toe empty board.png"));

        // x starts first
        turn = PLAYER_ONE;
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (gameOver()) {
            if (turn == PLAYER_ONE) {
                System.out.println("you won!");
            } else {
                System.out.println("you lost to the AI!");
            }
        } else {
            if (turn == PLAYER_ONE) {
                randomPlacement();
            }
        }
        drawBoard(g);

    }

    private void randomPlacement() {
        while (true) {
            int xPos = (int) (Math.random() * 3);
            int yPos = (int) (Math.random() * 3);
            if (board[xPos][yPos] == ' ') {
                board[xPos][yPos] = PLAYER_ONE;
                turn = PLAYER_TWO;
                break;
            }
        }
    }

    

    private void drawBoard(Graphics g) {
        g.drawImage(emptyBoard, 0, 0, this);
        int xPos;
        int yPos = panelHeight / 6;
        g.setColor(Color.BLACK);
        for (int i = 0; i < 3; i++) {
            xPos = panelWidth / 6;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'x') {
                    g.drawLine(xPos - 10, yPos - 10, xPos + 10, yPos + 10);
                    g.drawLine(xPos + 10, yPos - 10, xPos - 10, yPos + 10);
                } else if (board[i][j] == 'o') {
                    g.drawOval(xPos - 10, yPos - 10, 20, 20);
                }
                xPos += panelWidth / 3;

            }
            yPos += panelHeight / 3;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); // calls the paint method again


    }

    public boolean gameOver() {
        if (rowCheck()) return true;
        if (columnCheck()) return true;
        if (diagonalCheck()) return true;
        return false;
    }

    public boolean rowCheck() {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] != ' ' && board[row][0] == board[row][1]
                    && board[row][1] == board[row][2]) {
                return true;
            }
        }
        return false;
    }

    public boolean columnCheck() {
        for (int column = 0; column < 3; column++) {
            if (board[0][column] != ' ' && board[0][column] == board[1][column]
                    && board[1][column] == board[2][column]) {
                return true;
            }
        }
        return false;
    }

    public boolean diagonalCheck() {
        if (board[1][1] == ' ') return false;
        if (board[0][0] == board[1][1] && board [1][1] == board[2][2]) {
            return true;
        }
        if (board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
            return true;
        }
        return false;
    }
}