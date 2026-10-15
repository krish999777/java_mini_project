import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import data.*;

public class StudentGUI extends JFrame implements ActionListener{
    JButton b1,b2,b3,b4;
    StudentGUI(){
        setSize(500,500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1,2,10,10));
        b1=new JButton("View details");
        b2=new JButton("Edit details");
        b1.addActionListener(this);
        b2.addActionListener(this);
        add(b1);
        add(b2);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){
            // new viewDetailsGUI();
            dispose();
        }else{

        }
    }
}

