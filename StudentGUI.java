import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import data.*;
import session.Session;

public class StudentGUI extends JFrame implements ActionListener{
    JButton b1,b2,b3,b4;
    StudentGUI(){
        setSize(500,500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,2,10,10));
        b1=new JButton("View details");
        b2=new JButton("Edit details");
        b3=new JButton("Generate Resume");
        b4=new JButton("Logout");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        JButton b=(JButton)ae.getSource();
        if(b==b1){
            // new viewDetailsGUI();
            dispose();
        }else if(b==b2){

        }else if(b==b3){

        }else{
            Session.logout();
            new LoginGUI();
            dispose();
        }
    }
}

class viewDetailsGUI extends JFrame implements ActionListener{
    JLabel l1,l2;
    viewDetailsGUI(){
        setSize(500,500);
        setLayout(new GridLayout());
        setLocationRelativeTo(null);
        l1=new JLabel("View Details");
        l2=new JLabel("");
        l2.setVisible(false);
        try{
            StudentProfileModel s=StudentProfile.getProfile();
            System.out.println(s);
        }catch(Exception e){
            l2.setText(e.toString());
            l2.setVisible(true);
        }

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){

    }
}

