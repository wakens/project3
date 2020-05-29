import javax.swing.Icon;
import java.awt.Component;
import javax.swing.JOptionPane;

public class Window
{
    /*
     * This method creates an easy way to display a String message in JOptionPane.
     */
    public void msg(final String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
    /*
     * This method creates an easy way to display an Object message in JOptionPane.
     */ 
    public void msg(final Object msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
 
    /*
     * This method creates an easy way to display a String message
     * and ask for user input in JOptionPane.
     */
    public String in(final String msg) {
        return JOptionPane.showInputDialog(msg);
    }
    /*
     * This method creates an easy way to display an Object message
     * and ask for user input in JOptionPane.
     */
    public String in(final Object msg) {
        return JOptionPane.showInputDialog(msg);
    }
    
    /*
     * This method creates an easy way to display a String message
     * along with buttons that lead to different options.
     */
    public int option(final String[] options, final String msg) {
        return JOptionPane.showOptionDialog(null, msg, "Click a button", -1, 1, null, options, options[0]);
    }
    /*
     * This method creates an easy way to display an Object message
     * along with buttons that lead to different options.
     */
    public int option(final Object[] options, final Object msg) {
        return JOptionPane.showOptionDialog(null, msg, "Click a button", -1, 1, null, options, options[0]);
    }
}
