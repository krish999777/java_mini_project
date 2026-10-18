import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import session.Session;
import data.Role;

public class AdminGUI extends JFrame implements ActionListener{
    JLabel l1;
    JTextField tf1;
    JPasswordField tf2;
    JComboBox<Role> cbRole;
    JButton b1,b2;
    JPanel header,footer,form;

    public AdminGUI(){
        setTitle("Admin Dashboard");
        setSize(500, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(248, 250, 252));

        // Header with clear title at the top
        header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        l1 = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        l1.setFont(new Font("SansSerif", Font.BOLD, 20));
        l1.setForeground(Color.WHITE);
        header.add(l1, BorderLayout.CENTER);

        // Form in the center with card styling and padding
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setBackground(new Color(248, 250, 252));
        formWrapper.setBorder(BorderFactory.createEmptyBorder(25, 40, 15, 40));

        form = new JPanel(new GridLayout(3, 2, 12, 16));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        tf1 = new JTextField();
        tf1.setFont(new Font("SansSerif", Font.PLAIN, 13));

        tf2 = new JPasswordField();
        tf2.setFont(new Font("SansSerif", Font.PLAIN, 13));

        cbRole = new JComboBox<>(Role.values());
        cbRole.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblUser.setForeground(new Color(30, 41, 59));

        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblPass.setForeground(new Color(30, 41, 59));

        JLabel lblRole = new JLabel("Role");
        lblRole.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblRole.setForeground(new Color(30, 41, 59));

        form.add(lblUser);
        form.add(tf1);
        form.add(lblPass);
        form.add(tf2);
        form.add(lblRole);
        form.add(cbRole);

        formWrapper.add(form, BorderLayout.CENTER);

        // Footer buttons
        footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        footer.setBackground(new Color(248, 250, 252));
        footer.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        b1 = new JButton("Create User");
        b1.setFont(new Font("SansSerif", Font.BOLD, 13));
        b1.setBackground(new Color(37, 99, 235));
        b1.setForeground(Color.WHITE);
        b1.setFocusPainted(false);
        b1.setOpaque(true);
        b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(29, 78, 216), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        b2 = new JButton("Logout");
        b2.setFont(new Font("SansSerif", Font.BOLD, 13));
        b2.setBackground(new Color(220, 38, 38));
        b2.setForeground(Color.WHITE);
        b2.setFocusPainted(false);
        b2.setOpaque(true);
        b2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(185, 28, 28), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
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
        JButton source = (JButton)ae.getSource();
        if(source == b1){
            try{
                String username = tf1.getText().trim();
                String password = new String(tf2.getPassword()).trim();
                Role role = (Role)cbRole.getSelectedItem();
                Admin.createUser(role, username, password);
                JOptionPane.showMessageDialog(this, "User created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                tf1.setText("");
                tf2.setText("");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else if(source == b2){
            Session.logout();
            new LoginGUI();
            dispose();
        }
    }
}