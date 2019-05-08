import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MineSweeper extends JFrame implements KeyListener, ActionListener {
    private final int ROWS = 8, COLS = 8;
    //INFORMATION PANEL
    JPanel information = new JPanel(new GridLayout(1,3));
    JLabel lblMines = new JLabel(Assets.infoLeft);
    JButton btnReset = new JButton(Assets.smiley);
    JLabel lblMoves = new JLabel();
    Font infoFont = new Font("Arial", Font.BOLD, 24);

    //BOARD GRID
    JPanel boardGrid = new JPanel(new GridLayout(ROWS, COLS));
    //ICONS GRID
    JButton[][] iconsGrid = new JButton[ROWS][COLS];
    //LOGIC GRID
    Symbol[][] symbolGrid = new Symbol[ROWS][COLS];
    //REVEAL GRID
    boolean[][] revealGrid = new boolean[ROWS][COLS];

    boolean gameOver = false;

    int mineCounter, infoMineCounter, revealedSpots, correctFlagCounter, moveCounter;

    int playerX, playerY, prevX, prevY;
    Icon prevIcon = Assets.blank;

    Container contentPane = getContentPane();

    public MineSweeper() {
        super("MineSweeper");

        //INFORMATION PANEL
        btnReset.setBorder(null);
        btnReset.setFont(infoFont);
        btnReset.addActionListener(this);

        lblMoves.setFont(infoFont);
        lblMoves.setHorizontalTextPosition(JLabel.CENTER);
        lblMoves.setForeground(Color.red);
        lblMoves.setIcon(Assets.infoRight);

        lblMines.setFont(infoFont);
        lblMines.setHorizontalTextPosition(JLabel.CENTER);
        lblMines.setForeground(Color.red);
        lblMines.setIcon(Assets.infoLeft);


        information.add(lblMines);
        information.add(btnReset);
        information.add(lblMoves);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                iconsGrid[row][col] = new JButton();
                iconsGrid[row][col].setBorder(null);
                iconsGrid[row][col].addActionListener(this);
                revealGrid[row][col] = false;

                boardGrid.add(iconsGrid[row][col]);

            }
        }
        reset();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentPane.add(information);
        contentPane.add(boardGrid);

        contentPane.addKeyListener(this);
        contentPane.setFocusable(true);
    }

    private void reset() {
        mineCounter = 0;
        infoMineCounter = 0;
        revealedSpots = 0;
        correctFlagCounter = 0;
        moveCounter = 0;
        playerX = 0;
        playerY = 0;
        prevX = 0;
        prevY = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                revealGrid[row][col] = false;
                symbolGrid[row][col] = Symbol.BUTTON;
                iconsGrid[row][col].setIcon(Assets.blank);
            }
        }

        contentPane.requestFocus();
        prevIcon = Assets.blank;
        iconsGrid[playerY][playerX].setIcon(Assets.playerButton);
        btnReset.setIcon(Assets.smiley);
        gameOver = false;
        updateInfo();
    }

    private void updateInfo() {
        lblMines.setText(formatDigits(infoMineCounter));
        lblMoves.setText(formatDigits(moveCounter));
    }

    private String formatDigits(int number) {
        int absNumber = Math.abs(number);
        boolean oneDigit = absNumber < 10,
                twoDigit = absNumber < 100;
        if (oneDigit) {
            if (number < 0) {  // negative
                return "-0" + absNumber;
            } else {                    // positive
                return"00" + absNumber;
            }
        } else if (twoDigit) {
            if (number < 0) {
                return "-" + absNumber;
            } else {
                return "0" + absNumber;
            }
        } else {  // 3 or more digits
            return number + "";
        }
    }

    private void move(int key) {
        prevX = playerX;
        prevY = playerY;
        switch (key) {
            case KeyEvent.VK_W:
                playerY -= 1;
                break;
            case KeyEvent.VK_A:
                playerX -= 1;
                break;
            case KeyEvent.VK_S:
                playerY += 1;
                break;
            case KeyEvent.VK_D:
                playerX += 1;
                break;
        }
        iconsGrid[prevY][prevX].setIcon(prevIcon);
        prevIcon = iconsGrid[playerY][playerX].getIcon();
        if (revealGrid[playerY][playerX]) {
            iconsGrid[playerY][playerX].setIcon(Assets.player);
        } else {
            iconsGrid[playerY][playerX].setIcon(Assets.playerButton);
        }
    }

    private void draw(int row, int col) {
        if (!revealGrid[row][col]) {
            switch (symbolGrid[row][col]) {
                case INCORRECT_FLAG:
                case CORRECT_FLAG:
                    iconsGrid[row][col].setIcon(Assets.flag);
                    break;
                case INCORRECT_QUESTION_MARK:
                case CORRECT_QUESTION_MARK:
                    iconsGrid[row][col].setIcon(Assets.questionMark);
                    break;
                case BUTTON:
                case HIDDEN_MINE:
                    iconsGrid[row][col].setIcon(Assets.blank);
                    break;
            }
            prevIcon = iconsGrid[playerY][playerX].getIcon();
        } else {
            switch (symbolGrid[row][col]) {
                case REVEALED_INCORRECT_FLAG:
                    iconsGrid[row][col].setIcon(Assets.wrongFlag);
                    break;
                case MINE:
                    iconsGrid[row][col].setIcon(Assets.bomb);
                    break;
                case MINE_RED:
                    iconsGrid[row][col].setIcon(Assets.bombRed);
                    break;
                case ZERO:
                    iconsGrid[row][col].setIcon(Assets.revealed);
                    break;
                case ONE:
                    iconsGrid[row][col].setIcon(Assets.one);
                    break;
                case TWO:
                    iconsGrid[row][col].setIcon(Assets.two);
                    break;
                case THREE:
                    iconsGrid[row][col].setIcon(Assets.three);
                    break;
                case FOUR:
                    iconsGrid[row][col].setIcon(Assets.four);
                    break;
                case FIVE:
                    iconsGrid[row][col].setIcon(Assets.five);
                    break;
                case SIX:
                    iconsGrid[row][col].setIcon(Assets.six);
                    break;
                case SEVEN:
                    iconsGrid[row][col].setIcon(Assets.seven);
                    break;
                case EIGHT:
                    iconsGrid[row][col].setIcon(Assets.eight);
                    break;
            }
        }
        prevIcon = iconsGrid[playerY][playerX].getIcon();
    }

    private boolean[][] withinBounds(int row, int col) {
        boolean[][] grid = new boolean[3][3];
        grid[0][0] = row - 1 > -1 && col - 1 > -1;
        grid[0][1] = row - 1 > -1;
        grid[0][2] = row - 1 > -1 && col + 1 < COLS;
        grid[1][0] = col - 1 > -1;
        grid[1][1] = true;
        grid[1][2] = col + 1 < COLS;
        grid[2][0] = row + 1 < ROWS && col - 1 > -1;
        grid[2][1] = row + 1 < ROWS;
        grid[2][2] = row + 1 < ROWS && col + 1 < COLS;
        return grid;
    }

    private boolean[][] checkAdjacent(int row, int col, Symbol symbol, Symbol[][] grid) {
        boolean[][] boundGrid = withinBounds(row, col);
        boolean[][] validGrid = new boolean[ROWS][COLS];
        int offset = 1;
        if (boundGrid[0][0] && grid[row - 1][col - 1] == symbol) {
            validGrid[-1 + offset][-1 + offset] = true;
        }
        if (boundGrid[0][1] && grid[row - 1][col] == symbol) {
            validGrid[-1 + offset][0 + offset] = true;
        }
        if (boundGrid[0][2] && grid[row - 1][col + 1] == symbol) {
            validGrid[-1 + offset][1 + offset] = true;
        }
        if (boundGrid[1][0] && grid[row][col - 1] == symbol) {
            validGrid[0 + offset][-1 + offset] = true;
        }
        if (boundGrid[1][1] && grid[row][col] == symbol) {
            validGrid[0 + offset][0 + offset] = true;
        }
        if (boundGrid[1][2] && grid[row][col + 1] == symbol) {
            validGrid[0 + offset][1 + offset] = true;
        }
        if (boundGrid[2][0] && grid[row + 1][col - 1] == symbol) {
            validGrid[1 + offset][-1 + offset] = true;
        }
        if (boundGrid[2][1]&& grid[row + 1][col] == symbol) {
            validGrid[1 + offset][0 + offset] = true;
        }
        if (boundGrid[2][2] && grid[row + 1][col + 1] == symbol) {
            validGrid[1 + offset][1 + offset] = true;
        } else {
        }

        return validGrid;
    }

    private int getRevealedSpots() {
        int revealedSpots = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                revealedSpots++;
            }
        }
        return revealedSpots;
    }

    private boolean notFlagOrQuestionMark(Symbol symbol) {
        return symbol != Symbol.CORRECT_FLAG && symbol != Symbol.INCORRECT_FLAG && symbol != Symbol.CORRECT_QUESTION_MARK && symbol != Symbol.INCORRECT_QUESTION_MARK;
    }

    private void revealZero(int row, int col) {
        prevIcon = iconsGrid[playerY][playerX].getIcon();
        boolean validGrid[][] = checkAdjacent(row, col, Symbol.ZERO, symbolGrid);
        for (int temp_row = -1; temp_row <= 1; temp_row++) {
            for (int temp_col = -1; temp_col <= 1; temp_col++) {
                    if (validGrid[temp_row + 1][temp_col + 1] == true && revealGrid[row + temp_row][col + temp_col] == false) {
                        if (notFlagOrQuestionMark(symbolGrid[row + temp_row][col + temp_col])) {

                            revealGrid[row + temp_row][col + temp_col] = true;
                        revealZero(row + temp_row, col + temp_col);
                    }
                }
            }
        }
        boolean boundsGrid[][] = withinBounds(row, col);
        for (int temp_row = -1; temp_row <= 1; temp_row++) {
            for (int temp_col = -1; temp_col <= 1; temp_col++) {
                    if (boundsGrid[temp_row + 1][temp_col + 1]) {
                        if (notFlagOrQuestionMark(symbolGrid[row + temp_row][col + temp_col])) {

                            revealGrid[row + temp_row][col + temp_col] = true;
                    }
                }
            }
        }
    }

    private void updateBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                draw(row, col);
            }
        }
    }

    private void lose() {
        symbolGrid[playerY][playerX] = Symbol.MINE_RED;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (symbolGrid[row][col] == Symbol.HIDDEN_MINE || symbolGrid[row][col] == Symbol.CORRECT_QUESTION_MARK) {
                    revealGrid[row][col] = true;
                    symbolGrid[row][col] = Symbol.MINE;
                    draw(row, col);
                }
                if (symbolGrid[row][col] == Symbol.INCORRECT_FLAG) {
                    symbolGrid[row][col] = Symbol.REVEALED_INCORRECT_FLAG;
                    revealGrid[row][col] = true;
                    draw(row,col);
                }
            }
        }
        gameOver = true;
        btnReset.setIcon(Assets.sad);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!gameOver) {
            if (key == KeyEvent.VK_W && playerY > 0) {
                move(key);
            } else if (key == KeyEvent.VK_A && playerX > 0) {
                move(key);
            } else if (key == KeyEvent.VK_S && playerY < ROWS - 1) {
                move(key);
            } else if (key == KeyEvent.VK_D && playerX < COLS - 1) {
                move(key);
            }
            Symbol currentPosition;
            if (key == KeyEvent.VK_SPACE) {
                if (!revealGrid[playerY][playerX]) {
                    btnReset.setIcon(Assets.omouth);
                    if (moveCounter++ == 0) {
                        generate();
                    }
                    currentPosition = symbolGrid[playerY][playerX];
                    if (currentPosition == Symbol.ZERO) {
                        revealZero(playerY, playerX);
                        updateBoard();
                        moveCounter++;
                    }
                    if (currentPosition == Symbol.HIDDEN_MINE) {
                        lose();
                    }
                    if (currentPosition != Symbol.INCORRECT_FLAG && currentPosition != Symbol.INCORRECT_QUESTION_MARK &&
                    currentPosition != Symbol.CORRECT_FLAG && currentPosition != Symbol.CORRECT_QUESTION_MARK) {
                        revealGrid[playerY][playerX] = true;
                        moveCounter++;
                        draw(playerY, playerX);
                    }
                    updateInfo();
                    if (checkWin()) {
                        win();
                    }
                }
            }
            if (key == KeyEvent.VK_SHIFT) {
                if (!revealGrid[playerY][playerX]) {
                    if (moveCounter++ == 0) {
                        generate();
                    }
                    currentPosition = symbolGrid[playerY][playerX];
                    switch (currentPosition) {
                        case CORRECT_QUESTION_MARK:
                            flag(playerY, playerX, Symbol.HIDDEN_MINE, symbolGrid, 0, 0, 0);
                            break;
                        case CORRECT_FLAG:
                            flag(playerY, playerX, Symbol.CORRECT_QUESTION_MARK, symbolGrid, -1, 1, 0);
                            break;
                        case HIDDEN_MINE:
                            flag(playerY, playerX, Symbol.CORRECT_FLAG, symbolGrid, 1, -1, 1);
                            break;
                        case INCORRECT_QUESTION_MARK:
                            flag(playerY, playerX, Symbol.BUTTON, symbolGrid, 0, 0, 0);
                            setAdjacentMines(playerY, playerX, symbolGrid);
                            break;
                        case INCORRECT_FLAG:
                            flag(playerY, playerX, Symbol.INCORRECT_QUESTION_MARK, symbolGrid, 1, 1, 0);
                            break;
                        case BUTTON:
                        default:
                            flag(playerY, playerX, Symbol.INCORRECT_FLAG, symbolGrid, -1, -1, 1);
                    }
                    System.out.println(currentPosition);
                    updateInfo();
                    if (checkWin()) {
                        win();
                    }
                }
            }
        }
    }

    private void flag(int row, int col, Symbol symbol, Symbol[][] grid, int correctFlagCounter, int infoMineCounter, int moveCounter) {
        grid[row][col] = symbol;
        this.correctFlagCounter += correctFlagCounter;
        this.infoMineCounter += infoMineCounter;
        this.moveCounter += moveCounter;
        draw(playerY, playerX);
    }

    private void win() {
        gameOver = true;
        btnReset.setIcon(Assets.glasses);
    }

    private boolean checkWin() {
        if (correctFlagCounter == mineCounter) {
            return true;
        } else {
            if (getRevealedSpots() + mineCounter == ROWS * COLS) {
                return true;
            }
            return false;
        }
    }

    private void generate() {
        boolean createMine;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                createMine = Math.random() <= .1; //controls difficulty
                if (createMine) {
                    symbolGrid[row][col] = Symbol.HIDDEN_MINE;
                    mineCounter++;
                }
            }
        }
        if (symbolGrid[playerY][playerX] == Symbol.HIDDEN_MINE) {
            symbolGrid[playerY][playerX] = Symbol.BUTTON;
            mineCounter--;
        }
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (symbolGrid[row][col] == Symbol.HIDDEN_MINE) {
                    continue;
                } else {
                    setAdjacentMines(row, col, symbolGrid);
                }
            }
        }
        infoMineCounter = mineCounter;
    }

    private void setAdjacentMines(int row, int col, Symbol[][] grid) {
        int adjacentMineCount = 0;
        boolean[][] validGrid = checkAdjacent(row, col, Symbol.HIDDEN_MINE, symbolGrid);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (validGrid[i + 1][j + 1] == true) {
                    adjacentMineCount++;
                }
            }
        }
        boolean[][] validGrid1 = checkAdjacent(row, col, Symbol.CORRECT_FLAG, symbolGrid);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (validGrid1[i + 1][j + 1] == true) {
                    adjacentMineCount++;
                }
            }
        }
        boolean[][] validGrid2 = checkAdjacent(row, col, Symbol.CORRECT_QUESTION_MARK, symbolGrid);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (validGrid2[i + 1][j + 1] == true) {
                    adjacentMineCount++;
                }
            }
        }
        switch (adjacentMineCount) {
            case 0:
                grid[row][col] = Symbol.ZERO;
                break;
            case 1:
                grid[row][col] = Symbol.ONE;
                break;
            case 2:
                grid[row][col] = Symbol.TWO;
                break;
            case 3:
                grid[row][col] = Symbol.THREE;
                break;
            case 4:
                grid[row][col] = Symbol.FOUR;
                break;
            case 5:
                grid[row][col] = Symbol.FIVE;
                break;
            case 6:
                grid[row][col] = Symbol.SIX;
                break;
            case 7:
                grid[row][col] = Symbol.SEVEN;
                break;
            case 8:
                grid[row][col] = Symbol.EIGHT;
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                btnReset.setIcon(Assets.smiley);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnReset) {
            reset();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
