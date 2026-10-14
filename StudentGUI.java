import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentGUI extends JFrame{
    JLabel l1;
    StudentGUI(){
        setSize(500,500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1,1));
        l1=new JLabel("This is student frame");
        add(l1);
        setVisible(true);
    }
}
