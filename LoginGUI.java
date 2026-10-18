import javax.swing.*;
import session.Session;
import java.awt.*;
import java.awt.event.*;
import data.Role;

public class LoginGUI extends JFrame implements ActionListener{
    JLabel title,l1,l2;
    JTextField tf1;
    JPasswordField tf2;
    JButton b1,b2;
    JPanel header,form,footer;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    public LoginGUI(){
        setTitle("Student Profile Management System - Login");
        setSize(440, 380);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(248, 250, 252));

        // Header with clear title at the top
        header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        title = new JLabel("System Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);

        // Form in the center with card styling and generous spacing
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setBackground(new Color(248, 250, 252));
        formWrapper.setBorder(BorderFactory.createEmptyBorder(25, 40, 10, 40));

        form = new JPanel(new GridLayout(2, 2, 12, 16));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        l1 = new JLabel("Username");
        l1.setFont(new Font("SansSerif", Font.BOLD, 13));
        l1.setForeground(new Color(30, 41, 59));

        l2 = new JLabel("Password");
        l2.setFont(new Font("SansSerif", Font.BOLD, 13));
        l2.setForeground(new Color(30, 41, 59));

        tf1 = new JTextField(15);
        tf1.setFont(new Font("SansSerif", Font.PLAIN, 13));

        tf2 = new JPasswordField(15);
        tf2.setFont(new Font("SansSerif", Font.PLAIN, 13));

        form.add(l1);
        form.add(tf1);
        form.add(l2);
        form.add(tf2);
        formWrapper.add(form, BorderLayout.CENTER);

        // Footer buttons
        footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 16));
        footer.setBackground(new Color(248, 250, 252));
        footer.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));

        b1 = new JButton("Login");
        b1.setFont(new Font("SansSerif", Font.BOLD, 13));
        b1.setBackground(new Color(37, 99, 235));
        b1.setForeground(Color.WHITE);
        b1.setFocusPainted(false);
        b1.setOpaque(true);
        b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(29, 78, 216), 1),
            BorderFactory.createEmptyBorder(8, 22, 8, 22)
        ));

        b2 = new JButton("Clear");
        b2.setFont(new Font("SansSerif", Font.BOLD, 13));
        b2.setBackground(new Color(100, 116, 139));
        b2.setForeground(Color.WHITE);
        b2.setFocusPainted(false);
        b2.setOpaque(true);
        b2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(71, 85, 105), 1),
            BorderFactory.createEmptyBorder(8, 22, 8, 22)
        ));

        b1.addActionListener(this);
        b2.addActionListener(this);

        footer.add(b1);
        footer.add(b2);

        add(header, BorderLayout.NORTH);
        add(formWrapper, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){
            try{
                String username=tf1.getText();
                String password=new String(tf2.getPassword());
                Login.login(username,password);
                Role r=Session.getRole();
                if(r==Role.STUDENT){
                    new StudentGUI();
                    dispose();
                }else if(r==Role.MENTOR){
                    new MentorGUI();
                    dispose();
                }else{
                    new AdminGUI();
                    dispose();
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.toString(), "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            tf1.setText("");
            tf2.setText("");
        }
    }
}
