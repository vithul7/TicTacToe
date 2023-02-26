import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class TicTacToeBoard extends JPanel implements ActionListener {
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
                minMax();
                turn = HUMAN_MOVE;
            }
        }
        drawBoard(g);

    }

    private void minMax() {
        HashMap<Integer, Integer> availableMoves = getAvailableMoves();
        // iterating through available moves, using backtracking to revert board
        // after move is evaluated through minMax
        for (int position: availableMoves.keySet()) {
            board[position] = BOT_MOVE;
            availableMoves.put(position, availableMoves.get(position) + minMaxHelperHuman(1));
            board[position] = ' ';
        }

        // iterating through HashMap to find best move
        int maxPointTotal = -1000000;
        int bestPosition = -1;
        for (int position: availableMoves.keySet()) {
            if (availableMoves.get(position) > maxPointTotal) {
                maxPointTotal = availableMoves.get(position);
                bestPosition = position;
            }
        }
        // finally implementing the move
        board[bestPosition] = BOT_MOVE;
        movesLeft--;

    }

    private int minMaxHelperHuman(int depth) {
        // if game over, return appropriate point total
        if (gameOver()) return 10 - depth;
        // if there are no moves left, no point total to be found
        HashMap<Integer, Integer> availableMoves = getAvailableMoves();
        if (availableMoves.isEmpty()) {
            return 0;
        }
        // iterating through available moves, using backtracking to revert board
        // after move is evaluated through minMax
        for (int position: availableMoves.keySet()) {
            board[position] = HUMAN_MOVE;
            depth += 1;
            availableMoves.put(position, availableMoves.get(position) + minMaxHelperBot(depth));
            depth -= 1;
            board[position] = ' ';
        }

        // iterating through HashMap to find best move
        int minPointTotal = 1000000;
        for (int position: availableMoves.keySet()) {
            if (availableMoves.get(position) < minPointTotal) {
                minPointTotal = availableMoves.get(position);
            }
        }
        return minPointTotal;
    }

    private int minMaxHelperBot(int depth) {
        // if game over, return appropriate point total
        if (gameOver()) return (depth + 1) - 10;
        // if there are no moves left, no point total to be found
        HashMap<Integer, Integer> availableMoves = getAvailableMoves();
        if (availableMoves.isEmpty()) {
            return 0;
        }
        // iterating through available moves, using backtracking to revert board
        // after move is evaluated through minMax
        for (int position: availableMoves.keySet()) {
            board[position] = BOT_MOVE;
            depth += 1;
            availableMoves.put(position, availableMoves.get(position) + minMaxHelperHuman(depth));
            depth -= 1;
            board[position] = ' ';
        }
        // iterating through HashMap to find best move
        int maxPointTotal = -1000000;
        for (int position: availableMoves.keySet()) {
            if (availableMoves.get(position) > maxPointTotal) {
                maxPointTotal = availableMoves.get(position);
            }
        }
        return maxPointTotal;
    }

    private HashMap<Integer, Integer> getAvailableMoves() {
        HashMap<Integer, Integer> availableMoves = new HashMap<>();
        for (int position = 0; position < 9; position++) {
            if (board[position] == ' ') {
                availableMoves.put(position, 0);
            }
        }
        return availableMoves;
    }

    private void printBoardToConsole() {
        System.out.println(board[0] + " " + board[1] + " " + board[2]);
        System.out.println(board[3] + " " + board[4] + " " + board[5]);
        System.out.println(board[6] + " " + board[7] + " " + board[8]);
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

    public void boardScenarioOne() {
        board[0] = 'o';
        board[1] = 'x';
        board[2] = 'o';
        board[3] = 'x';
        board[4] = 'o';
        board[5] = 'o';
        board[6] = ' ';
        board[7] = 'x';
        board[8] = 'x';
        movesLeft = 1;
        // o x o
        // x o o
        //   x x
    }

    public void boardScenarioTwo() {
        board[0] = 'o';
        board[1] = 'x';
        board[2] = 'o';
        board[3] = 'x';
        board[4] = ' ';
        board[5] = ' ';
        board[6] = 'x';
        board[7] = 'o';
        board[8] = ' ';
        movesLeft = 3;
        // o x o
        // x
        // x o
    }

    public void boardScenarioThree() {
        board[0] = ' ';
        board[1] = 'o';
        board[2] = ' ';
        board[3] = ' ';
        board[4] = ' ';
        board[5] = 'o';
        board[6] = 'x';
        board[7] = 'x';
        board[8] = 'o';
        movesLeft = 4;
        //   x
        //     x
        // o o x
    }

    public void boardScenarioFour() {
        board[0] = 'x';
        board[1] = 'o';
        board[2] = ' ';
        board[3] = ' ';
        board[4] = 'x';
        board[5] = ' ';
        board[6] = ' ';
        board[7] = ' ';
        board[8] = 'o';
        turn = BOT_MOVE;
        movesLeft = 5;
        // x o
        //   x
        //     o
    }
}