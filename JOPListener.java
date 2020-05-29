import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JOPListener implements ActionListener
{
    // This method reacts when the JOptionPane is clicked
    @Override
    public void actionPerformed(final ActionEvent e) {
        JOptionPane.showMessageDialog(null, "You clicked me!");
    }
}
