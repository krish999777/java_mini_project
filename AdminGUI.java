import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import session.Session;
import data.Role;

public class AdminGUI extends JFrame implements ActionListener{
    JLabel l1,l2;
    JTextField tf1,tf2;
    JComboBox<Role> cbRole;
    JButton b1,b2;
    JPanel header,footer,form;

    AdminGUI(){
        setSize(500,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        l1=new JLabel("Admin Dashboard");
        l2=new JLabel("");
        l2.setVisible(false);

        header=new JPanel(new GridLayout(1,2));
        header.add(l1);
        header.add(l2);

        form=new JPanel(new GridLayout(0,2,10,10));
        tf1=new JTextField();
        tf2=new JTextField();
        cbRole=new JComboBox<>(Role.values());

        form.add(new JLabel("Username"));
        form.add(tf1);
        form.add(new JLabel("Password"));
        form.add(tf2);
        form.add(new JLabel("Role"));
        form.add(cbRole);

        footer=new JPanel(new GridLayout(1,2,10,10));
        b1=new JButton("Create");
        b2=new JButton("Logout");
        b1.addActionListener(this);
        b2.addActionListener(this);
        footer.add(b1);
        footer.add(b2);

        add(header,BorderLayout.NORTH);
        add(form,BorderLayout.CENTER);
        add(footer,BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        JButton source=(JButton)ae.getSource();
        if(source==b1){
            try{
                String username=tf1.getText().trim();
                String password=tf2.getText().trim();
                Role role=(Role)cbRole.getSelectedItem();
                Admin.createUser(role,username,password);
                l2.setText("User created successfully");
                l2.setVisible(true);
                tf1.setText("");
                tf2.setText("");
            }catch(Exception ex){
                l2.setText(ex.toString());
                l2.setVisible(true);
            }
        }else if(source==b2){
            Session.logout();
            new LoginGUI();
            dispose();
        }
    }
}