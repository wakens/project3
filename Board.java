import javax.swing.JOptionPane;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.Random;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Action;
import javax.swing.Timer;
import javax.swing.AbstractAction;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener
{
    // INSTANCE VARIABLES
    private static final String TAG = "Board: ";
    private static final long serialVersionUID = 1L;
    private static final int BOARD_BORDER_WIDTH = 20;
    private static final int MAX_NUM_OF_CARDS = 24;
    private static final int MIN_NUM_OF_CARDS = 1;
    private static final int NUMBER_OF_ROWS = 4;
    private static final int NUMBER_OF_COLUMNS = 6;
    private static final int NUMBER_OF_PAIRS = 12;
    private static final int MAX_SELECTED_CARDS = 2;
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int VISIBLE_DELAY = 2000;
    private static final int PEEK_DELAY = 2000;
    private static final int EMPTY_CELL_TYPE = 0;
    private static final int HIDDEN_CARD_TYPE = 26;
    private static final int EMPTY_CARD_TYPE = 25;
    private static final String DEFAULT_IMAGE_FILENAME_SUFFIX = ".png";
    private static final String DEFAULT_IMAGE_FILENAME_PREFIX = "img-";
    private static final String DEFAULT_IMAGE_FOLDER = "/images/";
    private static final String HIDDEN_IMAGE_PATH = "/images/img-26.png";
    private static final String EMPTY_IMAGE_PATH = "/images/img-25.png";
    private static ArrayList<Cell> chosenCards;
    private static int numOfMatchedPairs;
    private static int numOfFailedAttempts;
    private static int selectedCards;
    private Cell[][] mBoard;
    private String[] mCardStorage;
    private Cell[] mCardChecker;
    
    // This is just a way to have all the variables inside be static.
    static {
        Board.chosenCards = new ArrayList<Cell>();
        Board.numOfMatchedPairs = 0;
        Board.numOfFailedAttempts = 0;
        Board.selectedCards = 0;
    }
    
    // GETTERS
    private Point getCellLocation(final Cell aCell) {
        if (aCell == null) {
            error("getCellLocation(Cell) received null", true);
            return null;
        }
        final Point p = new Point();
        for (int column = 0; column < 4; ++column) {
            for (int row = 0; row < 6; ++row) {
                if (this.mBoard[column][row] == aCell) {
                    p.setLocation(column, row);
                    return p;
                }
            }
        }
        return null;
    }
    private Cell getCellAtLoc(final Point point) {
        if (point == null) {
            error("getCellAtLoc( Point ) received null", true);
            return null;
        }
        return this.mBoard[point.x][point.y];
    }
    
    private Cell getCellAtLoc(final Point point) {
        if (point == null) {
            error("getCellAtLoc( Point ) received null", true);
            return null;
        }
        return this.mBoard[point.x][point.y];
    }
    
    // SETTERS
    private void setImages() {
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 6; ++column) {
                final URL file = this.getClass().getResource("/images/img-" + this.mCardStorage[column + 6 * row] + ".png");
                if (file == null) {
                    System.err.println("Board: setImages() reported error \"File not found\".");
                    System.exit(-1);
                }
                final ImageIcon anImage = new ImageIcon(file);
                this.mBoard[row][column].setIcon(anImage);
            }
        }
    }
    
    private void setCardToVisible(final int x, final int y) {
        this.mBoard[x][y].setSelected(true);
        this.showCardImages();
    }
    
    // CONSTRUCTOR
    public Board() {
        this.mBoard = null;
        this.mCardStorage = this.initCardStorage();
        this.mCardChecker = new Cell[2];
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setLayout(new GridLayout(4, 6));
        this.mBoard = new Cell[4][6];
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 6; ++column) {
                (this.mBoard[row][column] = new Cell(0)).addActionListener(this);
                this.add(this.mBoard[row][column]);
            }
        }
        this.init();
    }
    
    /*
     * This method initializes the board with all the card, allowing the user
     * to take a peek at the cards before the game starts.
     */
    public void init() {
        this.resetMatchedImages();
        resetBoardParam();
        this.peek();
        this.mCardStorage = this.initCardStorage();
        this.setImages();
    }
    
    /*
     * This method resets the board without randomizing it, allowing the user
     * to take another peek at the cards but restarting their progress.
     */
    public void reInit() {
        this.resetMatchedImages();
        resetBoardParam();
        this.peek();
        this.setImages();
    }
    
    /*
     * This method checks if the user has gotten all the pairs and has finished the game. 
     */
    public boolean isSolved() {
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 6; ++column) {
                if (!this.mBoard[row][column].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /*
     * This private method adds the latest chosen card to chosenCards.
     */
    private void addToChose(final Cell aCard) {
        if (aCard != null) {
            if (!Board.chosenCards.contains(aCard)) {
                Board.chosenCards.add(aCard);
            }
        }
        else {
            error("addToChose( Cell ) received null.", true);
        }
    }
    
    /*
     * This method is used whenever an action is performed, overriding the method to select that cell
     * and show that image until it is compared to a second image, when it is then compared to that
     * second image to see if they're the same and the user has finished a pair.
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e == null) {
            error("actionPermormed(ActionEvent) received null", false);
            return;
        }
        if (!(e.getSource() instanceof Cell)) {
            return;
        }
        if (!this.isCardValid((Cell)e.getSource())) {
            return;
        }
        ++Board.selectedCards;
        if (Board.selectedCards <= 2) {
            final Point gridLoc = this.getCellLocation((Cell)e.getSource());
            this.setCardToVisible(gridLoc.x, gridLoc.y);
            this.mCardChecker[Board.selectedCards - 1] = this.getCellAtLoc(gridLoc);
            this.addToChose(this.getCellAtLoc(gridLoc));
        }
        if (Board.selectedCards == 2) {
            if (!this.sameCellPosition(this.mCardChecker[0].getLocation(), this.mCardChecker[1].getLocation())) {
                this.setSelectedCards(this.mCardChecker[0], this.mCardChecker[1]);
            }
            else {
                --Board.selectedCards;
            }
        }
    }
    
    /*
     * This private method is used at the start of the game that allows the user to see all the images
     * for a brief moment before beginning the game.
     */
    private void peek() {
        final Action showImagesAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void actionPerformed(final ActionEvent e) {
                Board.this.showCardImages();
            }
        };
        final Timer timer = new Timer(2000, showImagesAction);
        timer.setRepeats(false);
        timer.start();
    }
    
    /*
     * This private method is an easy way to get each image, as it picks up an image
     * and adds it to the board.
     */
    private void showImage(final int x, final int y) {
        final URL file = this.getClass().getResource("/images/img-" + this.mCardStorage[y + 6 * x] + ".png");
        if (file == null) {
            System.err.println("Board: showImage(int, int) reported error \"File not found\".");
            System.exit(-1);
        }
        final ImageIcon anImage = new ImageIcon(file);
        this.mBoard[x][y].setIcon(anImage);
    }
    
    /*
     * This private method shows the card images, setting up the board so that
     * the user can start playing.
     */
    private void showCardImages() {
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 6; ++column) {
                if (!this.mBoard[row][column].isSelected()) {
                    if (this.mBoard[row][column].isMatched()) {
                        this.mBoard[row][column].setIcon(new ImageIcon(this.getClass().getResource("/images/img-25.png")));
                        this.mBoard[row][column].setType(25);
                    }
                    else {
                        this.mBoard[row][column].setIcon(new ImageIcon(this.getClass().getResource("/images/img-26.png")));
                        this.mBoard[row][column].setType(26);
                    }
                }
                else {
                    this.showImage(row, column);
                    final String type = this.mCardStorage[column + 6 * row];
                    final int parsedType = Integer.parseInt(type);
                    this.mBoard[row][column].setType(parsedType);
                }
            }
        }
    }
    
    /*
     * This private method generates a random file name, and since all the images have uniform names,
     * it means a random image is picked to be on the board.
     */
    private String generateRandomImageFilename(final int max, final int min) {
        final Random random = new Random();
        final Integer aNumber = min + random.nextInt(max);
        if (aNumber > 0 && aNumber < 10) {
            return "0" + aNumber;
        }
        return aNumber.toString();
    }
    
    /*
     * This private method returns a String array containing cards that are shuffled onto the board.
     */
    private String[] initCardStorage() {
        final String[] cardStorage = new String[24];
        String[] firstPair = new String[12];
        final String[] secondPair = new String[12];
        firstPair = this.randomListWithoutRep();
        for (int i = 0; i < 12; ++i) {
            cardStorage[i] = firstPair[i];
        }
        Collections.shuffle(Arrays.asList(firstPair));
        for (int j = 0; j < 12; ++j) {
            secondPair[j] = firstPair[j];
        }
        for (int k = 12; k < 24; ++k) {
            cardStorage[k] = secondPair[k - 12];
        }
        return cardStorage;
    }
    
    /*
     * This private method goes through a random list to generate an array.
     */
    private String[] randomListWithoutRep() {
        final String[] generatedArray = new String[12];
        final ArrayList<String> generated = new ArrayList<String>();
        for (int i = 0; i < 12; ++i) {
            String next;
            do {
                next = this.generateRandomImageFilename(24, 1);
            } while (generated.contains(next));
            generated.add(next);
            generatedArray[i] = generated.get(i);
        }
        return generatedArray;
    }
    
    /*
     * This private method checks if the user has selected the same cell,
     * rather than them selecting a different one to compare the first to.
     */
    private boolean sameCellPosition(final Point firstCell, final Point secondCell) {
        if (firstCell != null && secondCell != null) {
            return firstCell.equals(secondCell);
        }
        if (secondCell == firstCell) {
            return true;
        }
        if (firstCell == null) {
            error("sameCellPosition(Point, Point) received (null, ??)", true);
        }
        if (secondCell == null) {
            error("sameCellPosition(Point, Point) received (??, null)", true);
        }
        return false;
    }
    
    /*
     * This private method uses the two selected cards and checks if they match. If they don't,
     * it adds to the amount of failed attempts. 
     */
    private void setSelectedCards(final Cell firstCell, final Cell secondCell) {
        if (firstCell == null || secondCell == null) {
            if (firstCell == null) {
                error("setSelectedCards(Cell, Cell) received (null, ??)", true);
            }
            if (secondCell == null) {
                error("setSelectedCards(Cell, Cell) received (??, null)", true);
            }
            return;
        }
        if (firstCell.sameType(secondCell)) {
            firstCell.setMatched(true);
            secondCell.setMatched(true);
            firstCell.setSelected(false);
            secondCell.setSelected(false);
            this.showImage(this.getCellLocation(secondCell).x, this.getCellLocation(secondCell).y);
            this.peek();
            ++Board.numOfMatchedPairs;
            this.finalMessage();
        }
        else {
            firstCell.setMatched(false);
            secondCell.setMatched(false);
            firstCell.setSelected(false);
            secondCell.setSelected(false);
            this.showImage(this.getCellLocation(secondCell).x, this.getCellLocation(secondCell).y);
            this.peek();
            ++Board.numOfFailedAttempts;
        }
        resetSelectedCards();
    }
    
    /*
     * This private method checks whether the card is null. If it isn't it returns true. 
     */
    private boolean isCardValid(final Cell aCard) {
        if (aCard == null) {
            error("isCardValid(Cell) received null", false);
            return false;
        }
        return !aCard.isEmpty();
    }
    
    /*
     * This private method is the final message the user sees after completing the memory game. 
     * It displays the failed attempts and error percentage.
     */
    private void finalMessage() {
        final Action showImagesAction = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (Board.this.isSolved()) {
                    final Float numeralScore = Board.numOfFailedAttempts / 24.0f * 100.0f;
                    final String textualScore = numeralScore.toString();
                    JOptionPane.showMessageDialog(null, "Solved!! Your results:\n Failed Attempts: " + Board.numOfFailedAttempts + "\n Error percentage : " + textualScore + " %", "RESULTS", 1);
                }
            }
        };
        final Timer timer = new Timer(2000, showImagesAction);
        timer.setRepeats(false);
        timer.start();
    }
    
    /*
     * This private method resets the matched images.
     */
    private void resetMatchedImages() {
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 6; ++column) {
                if (this.mBoard[row][column].isMatched()) {
                    this.mBoard[row][column].setMatched(false);
                }
            }
        }
    }
    
    /*
     * This private method checks if there's an error in the board.
     */
    private static void error(final String message, final boolean crash) {
        System.err.println("Board: " + message);
        if (crash) {
            System.exit(-1);
        }
    }
    
    /*
     * This private method resets the cards that have been selected.
     */
    private static void resetSelectedCards() {
        Board.selectedCards = 0;
    }
    
    /*
     * This private method resets the number of pairs that have already been matched.
     */
    private static void resetNumMatchedCards() {
        Board.numOfMatchedPairs = 0;
    }
    
    /*
     * This private method resets the amount of attempts.
     */
    private static void resetFailedAttempts() {
        Board.numOfFailedAttempts = 0;
    }
    
    /*
     * This private method resets the board completely.
     */
    private static void resetBoardParam() {
        resetFailedAttempts();
        resetNumMatchedCards();
    }
}
