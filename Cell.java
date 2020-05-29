import javax.swing.JButton;

// 
// Decompiled by Procyon v0.5.36
// 

public class Cell extends JButton
{
    private static final String TAG = "Cell: ";
    private static final long serialVersionUID = 1L;
    private static final int MAX_TYPE_RANGE = 26;
    private static final int MIN_TYPE_RANGE = 0;
    private static final int EMPTY_CELL_TYPE = 25;
    private boolean mIsSelected;
    private boolean mIsMatched;
    private int mType;
    
    public Cell(final int aType) {
        this.mIsSelected = false;
        this.mIsMatched = false;
        this.mType = 25;
        this.mType = aType;
    }
    
    public int getType() {
        return this.mType;
    }
    
    public void setType(final int aType) {
        if (aType > 26 || aType < 0) {
            error("setType(int) reported \"Invalid type code\"", true);
        }
        this.mType = aType;
    }
    
    public boolean sameType(final Cell other) {
        if (other == null) {
            error("sameType(Cell) received null", false);
            return false;
        }
        return this.getType() == other.getType();
    }
    
    public boolean isEmpty() {
        return this.mType == 25;
    }
    
    @Override
    public void setSelected(final boolean selected) {
        this.mIsSelected = selected;
    }
    
    public void setMatched(final boolean matched) {
        this.mIsMatched = matched;
    }
    
    @Override
    public boolean isSelected() {
        return this.mIsSelected;
    }
    
    public boolean isMatched() {
        return this.mIsMatched;
    }
    
    private static void error(final String message, final boolean crash) {
        System.err.println("Cell: " + message);
        if (crash) {
            System.exit(-1);
        }
    }
}
