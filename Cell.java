import javax.swing.JButton;


public class Cell extends JButton
{
    // INSTANCE VARIABLES
    private static final String TAG = "Cell: ";
    private static final long serialVersionUID = 1L;
    private static final int MAX_TYPE_RANGE = 26;
    private static final int MIN_TYPE_RANGE = 0;
    private static final int EMPTY_CELL_TYPE = 25;
    private boolean mIsSelected;
    private boolean mIsMatched;
    private int mType;
    
    // GETTERS
    public int getType() { return this.mType; }
    @Override
    public boolean isSelected() { return this.mIsSelected; }
    public boolean isMatched() { return this.mIsMatched; }
    public boolean isEmpty() { return this.mType == 25; }
    
    // SETTER
    public void setType(final int aType) {
        if (aType > 26 || aType < 0) {
            error("setType(int) reported \"Invalid type code\"", true);
        }
        this.mType = aType;
    }
    @Override
    public void setSelected(final boolean selected) { this.mIsSelected = selected; }
    @Override
    public void setSelected(final boolean selected) { this.mIsSelected = selected; }
    public void setMatched(final boolean matched) { this.mIsMatched = matched; }
    
    
    // CONSTRUCTOR
    public Cell(final int aType) {
        this.mIsSelected = false;
        this.mIsMatched = false;
        this.mType = 25;
        this.mType = aType;
    }
    
    /*
     * This method compares the 'other' parameter to see if they are the same type of Cell.
     * If they are, it is returned true, if they aren't or other is null it returns false.
     */
    public boolean sameType(final Cell other) {
        if (other == null) {
            error("sameType(Cell) received null", false);
            return false;
        }
        return this.getType() == other.getType();
    }
    
    /*
     * This method checks if there is an error in the Cell, and if it crashes then it exits out of the program.
     */
    private static void error(final String message, final boolean crash) {
        System.err.println("Cell: " + message);
        if (crash) {
            System.exit(-1);
        }
    }
}
