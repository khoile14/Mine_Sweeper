// TO DO: add your implementation and JavaDocs.

import java.util.Random;

/**
 * This class represents a MineSweeper game.
 */
public class MineSweeper {


    //******************************************************
    //*******    BELOW THIS LINE IS PROVIDED code    *******
    //*******            Do NOT edit code!           *******
    //*******		  Remember to add JavaDoc		 *******
    //******************************************************

    //supported game levels

    /**
     * The enum Level.
     */
    public enum Level {
        /**
         * Tiny level.
         */
        TINY,
        /**
         * Easy level.
         */
        EASY,
        /**
         * Medium level.
         */
        MEDIUM,
        /**
         * Hard level.
         */
        HARD,
        /**
         * Custom level.
         */
        CUSTOM
    }

    //each level has a different board size (number of rows/columns) 
    //and a different number of mines

    /**
     * The constant ROWS_EASY.
     */
    private static int ROWS_EASY = 9;
    /**
     * The constant COLS_EASY.
     */
    private static int COLS_EASY = 9;
    /**
     * The constant MINES_EASY.
     */
    private static int MINES_EASY = 10;
    /**
     * The constant ROWS_TINY.
     */
    private static int ROWS_TINY = 5;
    /**
     * The constant COLS_TINY.
     */
    private static int COLS_TINY = 5;
    /**
     * The constant MINES_TINY.
     */
    private static int MINES_TINY = 3;
    /**
     * The constant ROWS_MEDIUM.
     */
    private static int ROWS_MEDIUM = 16;
    /**
     * The constant COLS_MEDIUM.
     */
    private static int COLS_MEDIUM = 16;
    /**
     * The constant MINES_MEDIUM.
     */
    private static int MINES_MEDIUM = 40;
    /**
     * The constant ROWS_HARD.
     */
    private static int ROWS_HARD = 16;
    /**
     * The constant COLS_HARD.
     */
    private static int COLS_HARD = 30;
    /**
     * The constant MINES_HARD.
     */
    private static int MINES_HARD = 99;

    //the 2d board of cells
    /**
     * The Board.
     */
    private DynGrid310<Cell> board;

    //number of rows of the board
    /**
     * The Row count.
     */
    private int rowCount;

    //number of columns of the board
    /**
     * The Col count.
     */
    private int colCount;
    /**
     * The total mine count.
     */
    //number of mines in the board
    private int mineTotalCount;

    //number of cells clicked / exposed
    /**
     * The clicked count.
     */
    private int clickedCount;

    //number of cells flagged as a mine
    /**
     * The flagged count.
     */
    private int flaggedCount;


    //game possible status

    /**
     * The enum Status.
     */
    public enum Status {
        /**
         * Init status.
         */
        INIT,
        /**
         * Ingame status.
         */
        INGAME,
        /**
         * Exploded status.
         */
        EXPLODED,
        /**
         * Solved status.
         */
        SOLVED
    }

    /**
     * The Status.
     */
    private Status status;

    //string names of status
    /**
     * The constant Status_STRINGS.
     */
    public final static String[] Status_STRINGS = {
        "INIT", "IN_GAME", "EXPLODED", "SOLVED"
    };


    //constructor
    // initialize game based on a provided seed for random numbers and 
    // the specified level

    /**
     * Instantiates a new Mine sweeper.
     *
     * @param seed  the seed.
     * @param level the level.
     */
    public MineSweeper(int seed, Level level) {

        //if level is customized, need more details (number of rows/columns/mines)
        if (level == Level.CUSTOM)
            throw new IllegalArgumentException("Customized games need more parameters!");

        //set number of rows, columns, mines based on the pre-defined levels
        switch (level) {
            case TINY:
                rowCount = ROWS_TINY;
                colCount = COLS_TINY;
                mineTotalCount = MINES_TINY;
                break;
            case EASY:
                rowCount = ROWS_EASY;
                colCount = COLS_EASY;
                mineTotalCount = MINES_EASY;
                break;
            case MEDIUM:
                rowCount = ROWS_MEDIUM;
                colCount = COLS_MEDIUM;
                mineTotalCount = MINES_MEDIUM;
                break;
            case HARD:
                rowCount = ROWS_HARD;
                colCount = COLS_HARD;
                mineTotalCount = MINES_HARD;
                break;
            default:
                //should not be able to reach here!
                rowCount = ROWS_TINY;
                colCount = COLS_TINY;
                mineTotalCount = MINES_TINY;
        }

        //create an empty board of the needed size
        //TODO: you implement this method
        board = genEmptyBoard(rowCount, colCount);

        //place mines, and initialize cells
        //TODO: you implement part of this method
        initBoard(seed);
    }

    //constructor: should only be used for customized games

    /**
     * Instantiates a new Mine sweeper.
     *
     * @param seed      the seed.
     * @param level     the level.
     * @param rowCount  the row count.
     * @param colCount  the col count.
     * @param mineCount the mine count.
     */
    public MineSweeper(int seed, Level level, int rowCount, int colCount, int mineCount) {

        if (level != Level.CUSTOM)
            throw new IllegalArgumentException("Only customized games need more parameters!");

        //set number of rows/columns/mines
        //assume all numbers are valid (check MineGUI for additional checking code)	
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.mineTotalCount = mineCount;


        //create an empty board of the needed size: you implement this method
        board = genEmptyBoard(rowCount, colCount);

        //place mines, and initialize cells: you implement part of this method
        initBoard(seed);

    }

    //method to initialize the game, including placing mines.
    //- assume it is invoked only after an empty board (rowCount x colCount) 
    //  has been created and set. check code above for examples.

    //TODO: you implement some important steps of this method

    /**
     * Init board.
     *
     * @param seed the seed.
     */
    public void initBoard(int seed) {

        //use seed to initialize a random number sequence
        Random random = new Random(seed);

        //randomly place mines on board
        int mineNum = 0;
        for (; mineNum < mineTotalCount; ) {

            //generate next (row, col)
            int row = random.nextInt(rowCount);
            int col = random.nextInt(colCount);


            //cell already has a mine: try again
            if (hasMine(row, col)) {
                continue;
            }

            //place mine
            board.get(row, col).setMine();
            mineNum++;
        }
        //System.out.println(board);

        //calculate nbr counts for each cell
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {

                //TODO: you implement countNbrMines()
                int count = countNbrMines(row, col);
                board.get(row, col).setCount(count);
            }
        }

        //initialize other game settings   
        status = Status.INIT;

        flaggedCount = 0;
        clickedCount = 0;

    }

    //report number of rows

    /**
     * Row count int.
     *
     * @return the int.
     */
    public int rowCount() {
        return rowCount;
    }

    //report number of columns

    /**
     * Col count int.
     *
     * @return the int.
     */
    public int colCount() {
        return colCount;
    }

    //report whether board is solved

    /**
     * Is solved boolean.
     *
     * @return the boolean.
     */
    public boolean isSolved() {
        return status == Status.SOLVED;
    }

    //report whether a mine has exploded

    /**
     * Is exploded boolean.
     *
     * @return the boolean.
     */
    public boolean isExploded() {
        return status == Status.EXPLODED;
    }

    //display board
    //use this for debugging

    /**
     * Board to string string.
     *
     * @return the string.
     */
    public String boardToString() {
        StringBuilder sb = new StringBuilder();

        //header of column indexes
        sb.append("- |");
        for (int j = 0; j < board.getNumCol(); j++) {
            sb.append(j + "|");
        }
        sb.append("\n");

        for (int i = 0; i < board.getNumRow(); i++) {
            sb.append(i + " |");
            for (int j = 0; j < board.getNumCol(); j++) {
                sb.append(board.get(i, j).toString());
                sb.append("|");
            }
            sb.append("\n");
        }
        return sb.toString().trim();

    }

    //display the game status and board
    //use this for debugging

    /**
     * To string string.
     *
     * @return the string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board Size: " + rowCount() + " x " + colCount() + "\n");
        sb.append("Total mines: " + mineTotalCount + "\n");
        sb.append("Remaining mines: " + mineLeft() + "\n");
        sb.append("Game status: " + getStatus() + "\n");

        sb.append(boardToString());
        return sb.toString().trim();
    }

    //******************************************************
    //*******      Methods to report cell details    *******
    //*******     These are used by GUI for display  *******
    //******* Check Cell class for helpful operations*******
    //******************************************************

    /**
     * Is flagged boolean.
     *
     * @param row the row.
     * @param col the col.
     * @return the boolean.
     */
    public boolean isFlagged(int row, int col) {
        // return true if cell at (row,col) is flagged
        // false otherwise
        // return false for invalid cell indexes

        if (!board.isValidCell(row, col)) {
            return false;
        }

        Cell cell = board.get(row, col);
        return (cell.isFlagged());
    }

    /**
     * Is visible boolean.
     *
     * @param row the row.
     * @param col the col.
     * @return the boolean.
     */
    public boolean isVisible(int row, int col) {
        // return true if cell at (row,col) is not hidden
        // false otherwise
        // return false for invalid cell indexes

        if (!board.isValidCell(row, col)) {
            return false;
        }

        Cell cell = board.get(row, col);
        return (cell.visible());
    }

    /**
     * Has mine boolean.
     *
     * @param row the row.
     * @param col the col.
     * @return the boolean.
     */
    public boolean hasMine(int row, int col) {
        // return true if cell at (row,col) has a mine,
        // regardless whether it has been flagged or not;
        // false otherwise
        // return false for invalid cell indexes

        if (!board.isValidCell(row, col)) {
            return false;
        }

        Cell cell = board.get(row, col);
        return (cell.hasMine());
    }

    /**
     * Gets count.
     *
     * @param row the row.
     * @param col the col.
     * @return the count.
     */
    public int getCount(int row, int col) {
        // return the count associated with cell at (row,col) has a mine
        // return -2 for invalid cell indexes

        if (!board.isValidCell(row, col)) {
            return -2;
        }

        Cell cell = board.get(row, col);
        return (cell.getCount());
    }

    //******************************************************
    //*******      Methods to report game status     *******
    //*******     These are used by GUI for display  *******
    //******************************************************

    /**
     * Mine left int.
     *
     * @return the int.
     */
    public int mineLeft() {
        // report how many mines have not be flagged
        return mineTotalCount - flaggedCount;

    }

    /**
     * Gets status.
     *
     * @return the status.
     */
    public String getStatus() {
        // report current game status
        return Status_STRINGS[status.ordinal()];

    }


    //******************************************************
    //*******  Methods reserved for testing/grading  *******
    //******************************************************

    //return the game board

    /**
     * Gets board.
     *
     * @return the board.
     */
    public DynGrid310<Cell> getBoard() {
        return board;
    }

    //set game board

    /**
     * Sets board.
     *
     * @param newBoard  the new board.
     * @param mineCount the mine count.
     */
    public void setBoard(DynGrid310<Cell> newBoard, int mineCount) {
        //set board
        this.board = newBoard;

        //set size
        rowCount = board.getNumRow();
        colCount = board.getNumCol();


        //set other features
        status = Status.INIT;

        flaggedCount = 0;
        clickedCount = 0;
        mineTotalCount = mineCount;
    }

    //******************************************************
    //*******       END of PROVIDED code             *******
    //******************************************************


    //******************************************************
    //*******        Code you need to implement      *******
    //*******		   Remember to add JavaDoc		 *******
    //******************************************************

    // ADD MORE PRIVATE MEMBERS HERE IF NEEDED!


    //*******************************************************
    //******* Methods to support board initialization *******
    //*******************************************************

    /**
     * Gen empty board dyn grid 310.
     *
     * @param rowNum the row num.
     * @param colNum the col num.
     * @return the dyn grid 310.
     */
    public static DynGrid310<Cell> genEmptyBoard(int rowNum, int colNum) {

        //create and return a grid with rowNum x colNum individual cells in it
        // - all cells are default cell objects (no mines)
        // - if rowNum or colNum is not positive, return null

        //amortized O(rowCount x colCount)
        if (rowNum <= 0 || colNum <= 0) {
            return null;
        }

        DynGrid310<Cell> game = new DynGrid310<>();

        for (int i = 0; i < rowNum; i++) {
            DynArr310<Cell> row = new DynArr310<>();
            for (int j = 0; j < colNum; j++) {
                row.add(new Cell());
            }
            game.addRow(i, row);
        }
        return game;
    }


    /**
     * Count nbr mines int.
     *
     * @param row the row.
     * @param col the col.
     * @return the int.
     */
    public int countNbrMines(int row, int col) {
        // count the number of mines in the neighbor cells of cell (row, col)
        // return -2 for invalid row / col indexes
        // return -1 if cell at (row, col) has a mine underneath it

        // O(1)
        if (!board.isValidCell(row, col)) {
            return -2;
        } else if (board.get(row, col).hasMine()) {
            return -1;
        } else {
            return helperForCountMine(row, col);
        }
    }

    /**
     * Helper for count mine int, find the count in neighbor cells.
     *
     * @param row the row.
     * @param col the col.
     * @return the int.
     */
    private int helperForCountMine(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (board.isValidCell(i, j) && board.get(i, j).hasMine()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Helper 2 for count mine int, find the count in neighbor cells.
     *
     * @param row the row.
     * @param col the col.
     * @return the int.
     */
    private int helper2ForCountMine(int row, int col) {
        int[][] arr = new int[board.getNumRow()][board.getNumCol()];
        for (int i = 0; i < board.getNumRow(); i++) {
            for (int j = 0; j < board.getNumCol(); j++) {
                arr[i][j] = helperForCountMine(i, j);
            }
        }
        return arr[row][col];
    }


    //******************************************************
    //*******   Methods to support game operations   *******
    //******************************************************

    /**
     * Click at int.
     *
     * @param row the row.
     * @param col the col.
     * @return the int.
     */
    public int clickAt(int row, int col) {
        // open cell located at (row,col)
        // for a valid cell location:
        //	- no change if cell is already flagged or exposed, return -2
        //  - if cell has a mine, open it would explode the mine,
        //		update game status accordingly and return -1
        //  - otherwise, open this cell and return number of mines adjacent to it
        //		- if the cell is not adjacent to any mines (i.e. a zero-count cell),
        //			also open all zero-count cells that are connected to this cell,
        //			as well as all cells that are orthogonally or diagonally adjacent
        //			to those zero-count cells.
        //		- HINT: recursion can really help! Consider define private helper methods.
        //  - update game status as needed
        //	- update other game features as needed
        //
        // for an invalid cell location:
        //	- no change and return -2


        if (board.isValidCell(row, col)) {
            if (board.get(row, col).isFlagged() || board.get(row, col).visible()) {
                return -2;
            } else if (board.get(row, col).hasMine()) {
                board.get(row, col).setVisible();
                status = Status.EXPLODED;
                return -1;
            } else {

                if (board.get(row, col).getCount() == 0) {
                    helperForClickAt(row, col);
                }else{
                    clickedCount++;
                }

                board.get(row, col).setVisible(); //open cell

                if (clickedCount == rowCount * colCount - mineTotalCount) {
                    status = Status.SOLVED;
                }
                return board.get(row, col).getCount();
            }
        }
        return -2; //default return, remove or change as needed
    }

    /**
     * Helper for click at.
     *
     * @param row the row.
     * @param col the col.
     */
    private void helperForClickAt(int row, int col) {
        if (!board.isValidCell(row, col)) {
            return; // Return if the cell is invalid.
        }

        Cell cell = board.get(row, col);
        if (cell.visible() || cell.isFlagged() || cell.hasMine()) {
            return; // Return if the cell is already visible, flagged, or contains a mine.
        }

        // Open the cell
        cell.setVisible();
        clickedCount++;


        if (board.get(row, col).getCount() == 0) {
            helperForClickAt(row - 1, col - 1);
            helperForClickAt(row - 1, col);
            helperForClickAt(row - 1, col + 1);
            helperForClickAt(row, col - 1);
            helperForClickAt(row, col + 1);
            helperForClickAt(row + 1, col - 1);
            helperForClickAt(row + 1, col);
            helperForClickAt(row + 1, col + 1);
        }

    }

    /**
     * Flag at boolean.
     *
     * @param row the row.
     * @param col the col.
     * @return the boolean.
     */
    public boolean flagAt(int row, int col) {
        //flag at cell located at (row,col),
        //return whether the cell is flagged or not
        //
        //	- no change if cell is not hidden (already open), return false
        //	- otherwise, flag the cell as needed and update relevant game features
        //  - update game status as needed
        //
        // - return false for an invalid cell location
        // O(1)
        if (board.isValidCell(row, col)) {
            Cell cell = board.get(row, col);
            if (cell.isFlagged()) {
                return true;
            }
            if (cell.visible()) {
                return false;
            } else {
                cell.setFlagged();
                flaggedCount++;
                if (status == Status.INIT) {
                    status = Status.INGAME;
                }
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Un flag at boolean.
     *
     * @param row the row.
     * @param col the col.
     * @return the boolean.
     */
    public boolean unFlagAt(int row, int col) {
        //Un-flag at cell located at (row,col),
        //return whether the cell is updated from flagged to unflagged
        //
        //	- no change if cell is not flagged before, return false
        //	- otherwise, unflag the cell and update relevant game features

        // - return false for an invalid cell location
        // O(1)
        if (board.isValidCell(row, col)) {
            Cell cell = board.get(row, col);
            if (!cell.visible() && cell.isFlagged()) {
                cell.unFlagged();
                flaggedCount--;
                return true;
            }
        }
        return false;

    }


    //******************************************************
    //*******     BELOW THIS LINE IS TESTING CODE    *******
    //*******      Edit it as much as you'd like!    *******
    //*******		Remember to add JavaDoc			 *******
    //******************************************************

    /**
     * The entry point of application.
     *
     * @param args the input arguments.
     */
    public static void main(String args[]) {
        //basic: get an empty board with no mines
        DynGrid310<Cell> myBoard = MineSweeper.genEmptyBoard(3, 4);

        //board size, all 12 cells should be in the default state, no mines
        if (myBoard.getNumRow() == 3 && myBoard.getNumCol() == 4 &&
                !myBoard.get(0, 0).hasMine() && !myBoard.get(1, 3).visible() &&
                !myBoard.get(2, 2).isFlagged() && myBoard.get(2, 1).getCount() == -1) {
            System.out.println("Yay 0");
        }

        //init a game at TINY level
        //use the same random number sequence as GUI  - 
        //	this will create the same board as Table 2 of p1 spec PDF.
        // you can change this for your own testing.

        Random random = new Random(10);
        MineSweeper game = new MineSweeper(random.nextInt(), Level.TINY);

        //print out the initial board and verify game setting
        //System.out.println(game);
        //expected board:
        //- |0|1|2|3|4|
        //0 |?|?|?|?|?|
        //1 |?|?|?|?|?|
        //2 |?|?|?|?|?|
        //3 |?|?|?|?|?|
        //4 |?|?|?|?|?|

        //countNbrMines 
        if (game.countNbrMines(0, 0) == 0 && game.countNbrMines(4, 2) == 1 &&
                game.countNbrMines(3, 3) == 3 && game.countNbrMines(2, 3) == -1 &&
                game.countNbrMines(5, 5) == -2) {
            System.out.println("Yay 1");
        }

        //first click at (3,3)
        if (game.clickAt(-1, 0) == -2 && game.clickAt(3, 3) == 3 &&
                game.isVisible(3, 3) && !game.isVisible(0, 0) &&
                game.getStatus().equals("IN_GAME") && game.mineLeft() == 3) {
            System.out.println("Yay 2");
        }
        //System.out.println(game);
        //expected board:
        //- |0|1|2|3|4|
        //0 |?|?|?|?|?|
        //1 |?|?|?|?|?|
        //2 |?|?|?|?|?|
        //3 |?|?|?|3|?|
        //4 |?|?|?|?|?|

        //click at a mine cell
        if (game.clickAt(2, 3) == -1 && game.isVisible(2, 3) &&
                game.getStatus().equals("EXPLODED")) {
            System.out.println("Yay 3");
        }
        //System.out.println(game);
        //expected board:
        //- |0|1|2|3|4|
        //0 |?|?|?|?|?|
        //1 |?|?|?|?|?|
        //2 |?|?|?|X|?|
        //3 |?|?|?|3|?|
        //4 |?|?|?|?|?|

        //start over with the same board
        random = new Random(10);
        game = new MineSweeper(random.nextInt(), Level.TINY);
        game.clickAt(3, 3);

        //flag and unflag
        if (game.flagAt(2, 3) && !game.isVisible(2, 3) &&
                game.isFlagged(2, 3) && game.flagAt(2, 4) &&
                game.mineLeft() == 1 && game.unFlagAt(2, 3) &&
                !game.isFlagged(2, 3) && game.mineLeft() == 2) {
            System.out.println("Yay 4");
        }

        //cell state & operations
        // - a flagged cell can not be clicked
        // - flag a cell already flagged does not change anything but still returns true
        // - an opened cell cannot be flagged or unflagged
        // - a hidden cell not flagged cannot be unflagged
        if (game.clickAt(2, 4) == -2 && game.flagAt(2, 4) &&
                !game.flagAt(3, 3) && !game.unFlagAt(3, 3) &&
                !game.unFlagAt(2, 3)) {
            System.out.println("Yay 5");
        }

        //clicking on a zero-count cell
        if (game.clickAt(0, 0) == 0 && game.isVisible(0, 0) && game.isVisible(4, 0) &&
                game.isVisible(0, 4) && game.isVisible(3, 2) && !game.isVisible(3, 4) &&
                !game.isVisible(4, 3)) {
            System.out.println("Yay 6");
        }
        //System.out.println(game);
        //expected board:
        //- |0|1|2|3|4|
        //0 | | | | | |
        //1 | | |1|2|2|
        //2 | | |1|?|F|
        //3 | | |2|3|?|
        //4 | | |1|?|?|

        //open all none-mine cells without any explosion solve the game!
        if (game.clickAt(4, 4) == 1 && game.clickAt(3, 4) == 3 &&
                game.getStatus().equals("SOLVED")) {
            System.out.println("Yay 7");
        }
        //System.out.println(game);
        //expected board:
        //- |0|1|2|3|4|
        //0 | | | | | |
        //1 | | |1|2|2|
        //2 | | |1|?|F|
        //3 | | |2|3|3|
        //4 | | |1|?|1|
    }

}