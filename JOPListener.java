import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 
// Decompiled by Procyon v0.5.36
// 

public class JOPListener implements ActionListener
{
    @Override
    public void actionPerformed(final ActionEvent e) {
        JOptionPane.showMessageDialog(null, "You clicked me!");
    }
}
