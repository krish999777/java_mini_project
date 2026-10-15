import javax.swing.*;
import session.Session;
import java.awt.*;
import java.awt.event.*;
import data.Role;

public class LoginGUI extends JFrame implements ActionListener{
    JLabel title,message,l1,l2;
    JTextField tf1,tf2;
    JButton b1,b2;
    LoginGUI(){
        // setVisible(true);
        setSize(500,500);
        setLayout(new GridLayout(4,2,10,10));
        setLocationRelativeTo(null);
        l1=new JLabel("Username");
        l2=new JLabel("Password");
        title=new JLabel("Login Frame");
        message=new JLabel("");
        message.setVisible(false);
        tf1=new JTextField(7);
        tf2=new JTextField(7);
        b1=new JButton("Login");
        b2=new JButton("Clear");
        b1.addActionListener(this);
        b2.addActionListener(this);
        add(title);
        add(message);
        add(l1);
        add(tf1);
        add(l2);
        add(tf2);
        add(b1);
        add(b2);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){
            try{
                String username=tf1.getText();
                String password=tf2.getText();
                Login.login(username,password);
                Role r=Session.getRole();
                if(r==Role.STUDENT){
                    new StudentGUI();
                    dispose();
                }else if(r==Role.MENTOR){

                }else{

                }
            }catch(Exception ex){
                System.out.println(ex);
                message.setText(ex.toString());
                message.setVisible(true);
            }
        }else{
            tf1.setText("");
            tf2.setText("");
        }
    }
}
