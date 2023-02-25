import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TicTacToeBoard extends JPanel implements ActionListener {
    char[][] board2 = new char[3][3];
    char[] board = new char[9];
    Timer timer;
    int panelHeight = 225;
    int panelWidth = 225;
    BufferedImage emptyBoard;
    char turn;
    final char BOT_MOVE = 'x';
    final char HUMAN_MOVE = 'o';
    int movesLeft;

    TicTacToeBoard() throws IOException {
        this.setPreferredSize(new Dimension(panelHeight, panelWidth));
        this.setBackground(Color.white);
        this.setFocusable(true);
        timer = new Timer(600, this); // every 0.001 second, it'll look inside this GamePanel object for the //
        // actionPerformed method and call it
        timer.start(); // activating the timer

        // creating the board with empty values
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }

        // creating empty board picture
        emptyBoard = ImageIO
                .read(new File("/Users/vithulravivarma/Desktop/TicTacToe/src/tic tac toe empty board.png"));

        // initializing turn and amount of turns left
        turn = BOT_MOVE;
        movesLeft = 9;
    }

    public void paint(Graphics g) {
        super.paint(g);
        // game over game play
        if (gameOver()) {
            if (turn == BOT_MOVE) {
                System.out.println("you won!");
            } else {
                System.out.println("you lost to the AI!");
            }
        } else if (movesLeft == 0) {
            System.out.println("tied");
        } else {
            // regular gameplay
            if (turn == BOT_MOVE) {
//                randomPlacement();
                oneMoveAhead();
//                minMaxDepthTwo();
                movesLeft--;
                turn = HUMAN_MOVE;
            }
        }
        drawBoard(g);

    }

    private void minMax() {

    }

    private int minMaxHelper(int a) {
        return a;
    }

    private void minMaxDepthTwo() {
        System.out.println(turn);
        int[] bestMove = new int[2];
        bestMove[0] = -1;
        bestMove[1] = -1;

        int maxWinPotential = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board2[row][col] == ' ') {
                    turn = BOT_MOVE;
                    board2[row][col] = turn;
                    printBoardToConsole();
                    turn = HUMAN_MOVE;
                    movesLeft--;
                    int moveWinTotal = minMaxDepthTwoHelper(0);
                    //printBoardToConsole();
                    System.out.println("win total :" + moveWinTotal + " row " + row + " col " + col);
                    if (moveWinTotal > maxWinPotential) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        maxWinPotential = moveWinTotal;
                    }
                    movesLeft++;
                    board2[row][col] = ' ';
                }
            }
        }
//        if (board[0][0] == ' ') {
//            board[0][0] = BOT_MOVE;
//            movesLeft--;
//            int moveWinTotal = minMaxDepthTwoHelper(0);
//            if (moveWinTotal > maxWinPotential) {
//                bestMove[0] = 0;
//                bestMove[1] = 0;
//                maxWinPotential = moveWinTotal;
//            }
//            movesLeft++;
//            board[0][0] = ' ';
//        }
        if (bestMove[0] == -1) {
            System.out.println("in random placement: max win potential of " + maxWinPotential);
            randomPlacement();
        } else {
            board2[bestMove[0]][bestMove[1]] = BOT_MOVE;
            System.out.println("in actual placement: max win potential of " + maxWinPotential);
        }
    }

    private int minMaxDepthTwoHelper(int currentWins) {
        int[] bestMove = new int[2];
        if (movesLeft == 0) {
            return currentWins;
        }
        int maxWinPotential = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int currentWinPotential = 0;
                if (board2[row][col] == ' ') {
                    board2[row][col] = turn;
                    movesLeft--;
                    if (gameOver()) {
                        if (turn == BOT_MOVE) currentWinPotential++;
                        else currentWinPotential--;
                    }
                    if (turn == BOT_MOVE) turn = HUMAN_MOVE;
                    else turn = BOT_MOVE;
                    int winsToAdd = minMaxDepthTwoHelper(currentWinPotential);
                    currentWinPotential += winsToAdd;
                    if (turn == BOT_MOVE && currentWinPotential > maxWinPotential) {
                        maxWinPotential = currentWinPotential;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                    if (turn == HUMAN_MOVE && currentWinPotential < maxWinPotential) {
                        maxWinPotential = currentWinPotential;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }


                    movesLeft++;
                    board2[row][col] = ' ';
                }
            }
        }

        //System.out.println("max win potential being returned on " + turn + " of " + maxWinPotential);
        return maxWinPotential;
    }

    private void printBoardToConsole() {
        System.out.println(Arrays.toString(board));
    }

    private void randomPlacement() {
        while (true) {
            int pos = (int) (Math.random() * 9);
            if (board[pos] == ' ') {
                board[pos] = BOT_MOVE;
                turn = HUMAN_MOVE;
                break;
            }
        }
    }

    private void oneMoveAhead() {
        if (movesLeft > 5) {
            // cannot have a game winning move before at least four spots are filled
            randomPlacement();
            return;
        }

        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = BOT_MOVE;
                if (gameOver()) return;
                board[i] = ' ';
            }
        }
        // only reaches here if there was no game winning move to be made
        randomPlacement();
    }

    private void drawBoard(Graphics g) {
        g.drawImage(emptyBoard, 0, 0, this);
        int xPos;
        int yPos = panelHeight / 6;
        g.setColor(Color.BLACK);
        for (int row = 0; row < 3; row++) {
            xPos = panelWidth / 6;
            for (int col = 0; col < 3; col++) {
                if (board[(row * 3) + col] == 'x') {
                    g.drawLine(xPos - 10, yPos - 10, xPos + 10, yPos + 10);
                    g.drawLine(xPos + 10, yPos - 10, xPos - 10, yPos + 10);
                } else if (board[(row * 3) + col] == 'o') {
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

    public int pointAssignment() {
        int points = 0;
        if (gameOver()) {
            if (turn == BOT_MOVE) points -= 10;
            else if (turn == HUMAN_MOVE) points += 10;
        }
        return points;
    }

    public boolean gameOver() {
        if (rowCheck()) {
            return true;
        }
        if (columnCheck()) {
            return true;
        }
        if (diagonalCheck()) {
            return true;
        }
        return false;
    }

    public boolean rowCheck() {
        if (board[0] != ' ' && board[0] == board[1] && board[1] == board[2]) return true;
        if (board[3] != ' ' && board[3] == board[4] && board[4] == board[5]) return true;
        if (board[6] != ' ' && board[6] == board[7] && board[7] == board[8]) return true;
        return false;
    }

    public boolean columnCheck() {
        if (board[0] != ' ' && board[0] == board[3] && board[3] == board[6]) return true;
        if (board[1] != ' ' && board[1] == board[4] && board[4] == board[7]) return true;
        if (board[2] != ' ' && board[2] == board[5] && board[5] == board[8]) return true;
        return false;
    }

    public boolean diagonalCheck() {
        if (board[4] == ' ') return false;
        if (board[0] == board[4] && board[4] == board[8]) return true;
        if (board[2] == board[4] && board[4] == board[6]) return true;
        return false;
    }
}