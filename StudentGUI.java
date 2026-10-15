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
            new ViewDetailsGUI();
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

class ViewDetailsGUI extends JFrame implements ActionListener{
    JLabel l1,l2;
    JButton b1,b2;
    JTabbedPane pane;
    JPanel header,footer,profile,skills;
    ViewDetailsGUI(){
        setSize(500,500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        l1=new JLabel("View Details");
        l2=new JLabel("");
        l2.setVisible(false);

        b1=new JButton("Print resume");
        b2=new JButton("Back to home");

        header=new JPanel();
        header.setLayout(new GridLayout(1,2));
        header.add(l1);
        header.add(l2);

        footer=new JPanel(new GridLayout(1,2));
        footer.add(b1);
        footer.add(b2);

        pane=new JTabbedPane();
        profile=new JPanel();
        skills=new JPanel();
        profile.add(new JLabel("This is profile panel"));
        skills.add(new JLabel("This is skills panel"));

        pane.addTab("Profile",profile);
        pane.addTab("Skills",skills);


        try{
            StudentProfileModel s=StudentProfile.getProfile();
            System.out.println(s);
        }catch(Exception e){
            l2.setText(e.toString());
            l2.setVisible(true);
        }
        b1.addActionListener(this);
        b2.addActionListener(this);
        add(header,BorderLayout.NORTH);
        
        add(pane,BorderLayout.CENTER);

        add(footer,BorderLayout.SOUTH);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){

        }else{
            new StudentGUI();
            dispose();
        }
    }
}

