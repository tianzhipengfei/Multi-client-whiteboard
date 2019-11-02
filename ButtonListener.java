
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    Color c;
    MyDraw md;
    JButton cb;                     //This is the current Color button in the lower left corner

    public ButtonListener(MyDraw mydraw, JButton colorButton) {
        this.md = mydraw;
        this.cb = colorButton;
    }
    public void actionPerformed(ActionEvent e) {
        JButton bt = (JButton) e.getSource();
        c = bt.getBackground();     //to get the clicked button's color
        md.setColor(c);             //set color in MyDraw
        cb.setBackground(c);        //set the current Color button's color
    }
}