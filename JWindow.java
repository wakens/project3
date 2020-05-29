import java.awt.LayoutManager;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class JWindow
{
    private JFrame _frame;
    private JPanel _pan;
    private JPanel _pan2;
    private JLabel _lab;
    private JLabel _lab2;
    private JLabel _lab3;
    private JLabel _lab4;
    private JLabel _lab5;
    private JButton _btn;
    
    public JWindow() {
        this.setupButtons();
        this.setupPanels();
        this.setupFrame();
    }
    
    private void setupFrame() {
        (this._frame = new JFrame("My Program")).setBounds(300, 300, 400, 400);
        this._frame.add(this._pan);
        this._frame.setVisible(true);
    }
    
    public void setupButtons() {
        this._lab = new JLabel("Count: 0");
        (this._btn = new JButton("Click me")).addActionListener(new JOPListener());
    }
    
    private void setupPanels() {
        this._pan = new JPanel();
        this._pan2 = new JPanel();
        this._pan.setLayout(new GridLayout(3, 1));
        this._pan.add(this._lab);
        this._pan.add(this._btn);
        this._pan2.setLayout(new GridLayout(1, 3));
        this._pan.add(this._pan2);
    }
}
