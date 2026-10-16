import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import session.Session;
import data.StudentProfileModel;

public class MentorGUI extends JFrame implements ActionListener {
    JLabel l1,l2;
    JButton b1;
    JPanel students[],header,center;
    JButton viewButtons[];
    StudentProfileModel p[];
    MentorGUI(){
        setSize(500,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        l1=new JLabel("Mentor page");
        l2=new JLabel("");
        l2.setVisible(false);
        b1=new JButton("Logout");
        header=new JPanel(new GridLayout(1,3,10,10));
        header.add(l1);
        header.add(l2);
        header.add(b1);
        b1.addActionListener(this);
        add(header,BorderLayout.NORTH);
        center=new JPanel(new GridLayout(0,3,10,10));

        try{
            p=Mentor.getStudents();
            students=new JPanel[p.length];
            viewButtons=new JButton[p.length];
            for(int i=0;i<p.length;i++){
                students[i]=new JPanel(new GridLayout(0,2,10,10));
                addProfileField(students[i],p[i].id,"ID");
                addProfileField(students[i],p[i].fullName,"Name");
                addProfileField(students[i],p[i].email,"Email");
                addProfileField(students[i],p[i].phone,"Phone");
                addProfileField(students[i],p[i].college,"College");
                addProfileField(students[i],p[i].course,"Course");
                addProfileField(students[i],p[i].year,"Year");
                addProfileField(students[i],p[i].currentSemester,"Semester");
                addProfileField(students[i],p[i].bio,"Bio");
                addProfileField(students[i],p[i].githubUrl,"Github url");
                addProfileField(students[i],p[i].linkedinUrl,"Linkedin url");

                viewButtons[i]=new JButton("View student");
                viewButtons[i].addActionListener(this);
                students[i].add(viewButtons[i]);
                students[i].add(new JLabel(""));
                students[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                center.add(students[i]);
            }
            add(center,BorderLayout.CENTER);
        }catch(Exception e){
            l2.setText(e.toString());
            l2.setVisible(true);
        }

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        JButton source=(JButton)ae.getSource();
        if(source==b1){
            Session.logout();
            new LoginGUI();
            dispose();
        }else{
            for(int i=0;i<viewButtons.length;i++){
                if(source==viewButtons[i]){
                    new StudentDetailGUI(p[i].id);
                    dispose();
                }
            }
        }
    }
    private void addProfileField(Container c,String value,String field){
        if(value!=null&&value.trim().length()!=0){
            c.add(new JLabel(field));
            c.add(new JLabel(value));
        }
    }
    private void addProfileField(Container c,int value,String field){
        if(value!=-1&&value!=0){
            c.add(new JLabel(field));
            c.add(new JLabel(""+value));
        }
    }
}
class StudentDetailGUI extends JFrame{
    StudentDetailGUI(){}
    StudentDetailGUI(int id){

    }
}