import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import session.Session;
import data.*;

public class MentorGUI extends JFrame implements ActionListener {
    JLabel l1,l2;
    JButton b1;
    JPanel students[],header,center,top,searchPanel;
    JButton viewButtons[];
    StudentProfileModel p[];
    JTextField tfName,tfCollege,tfCourse,tfYear,tfSkill;
    JButton bSearch,bReset;

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

        searchPanel=new JPanel(new GridLayout(0,4,5,5));
        tfName=new JTextField();
        tfCollege=new JTextField();
        tfCourse=new JTextField();
        tfYear=new JTextField();
        tfSkill=new JTextField();
        bSearch=new JButton("Search");
        bReset=new JButton("Reset");
        bSearch.addActionListener(this);
        bReset.addActionListener(this);

        searchPanel.add(new JLabel("Name"));
        searchPanel.add(tfName);
        searchPanel.add(new JLabel("College"));
        searchPanel.add(tfCollege);
        searchPanel.add(new JLabel("Course"));
        searchPanel.add(tfCourse);
        searchPanel.add(new JLabel("Year"));
        searchPanel.add(tfYear);
        searchPanel.add(new JLabel("Skill"));
        searchPanel.add(tfSkill);
        searchPanel.add(bSearch);
        searchPanel.add(bReset);

        top=new JPanel(new BorderLayout(5,5));
        top.add(header,BorderLayout.NORTH);
        top.add(searchPanel,BorderLayout.CENTER);
        add(top,BorderLayout.NORTH);

        center=new JPanel(new GridLayout(0,3,10,10));
        add(center,BorderLayout.CENTER);

        try{
            p=Mentor.getStudents();
            loadStudents(p);
        }catch(Exception e){
            l2.setText(e.toString());
            l2.setVisible(true);
        }

        setVisible(true);
    }

    private void loadStudents(StudentProfileModel p[]){
        center.removeAll();
        this.p=p;
        if(p==null||p.length==0){
            l2.setText("No students found");
            l2.setVisible(true);
            students=new JPanel[0];
            viewButtons=new JButton[0];
        }else{
            l2.setText("");
            l2.setVisible(false);
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
        }
        center.revalidate();
        center.repaint();
    }

    public void actionPerformed(ActionEvent ae){
        JButton source=(JButton)ae.getSource();
        if(source==b1){
            Session.logout();
            new LoginGUI();
            dispose();
        }else if(source==bSearch){
            try{
                String name=tfName.getText().trim();
                String college=tfCollege.getText().trim();
                String course=tfCourse.getText().trim();
                String yearStr=tfYear.getText().trim();
                int year=-1;
                if(yearStr.length()!=0){
                    try{
                        year=Integer.parseInt(yearStr);
                    }catch(NumberFormatException ex){
                        l2.setText("Year must be a number");
                        l2.setVisible(true);
                        return;
                    }
                }
                String skill=tfSkill.getText().trim();
                p=Mentor.getStudents(name,college,course,year,skill);
                loadStudents(p);
            }catch(Exception e){
                l2.setText(e.toString());
                l2.setVisible(true);
            }
        }else if(source==bReset){
            tfName.setText("");
            tfCollege.setText("");
            tfCourse.setText("");
            tfYear.setText("");
            tfSkill.setText("");
            try{
                p=Mentor.getStudents();
                loadStudents(p);
            }catch(Exception e){
                l2.setText(e.toString());
                l2.setVisible(true);
            }
        }else{
            if(viewButtons!=null){
                for(int i=0;i<viewButtons.length;i++){
                    if(source==viewButtons[i]){
                        new StudentDetailGUI(p[i].id);
                        dispose();
                        return;
                    }
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
class StudentDetailGUI extends JFrame implements ActionListener{
    int studentId;
    JLabel l1,l2;
    JButton b1,b2;
    JTabbedPane pane;
    JPanel header,footer,profile,skills,education,projects,internships,certifications,achievements,semesterMarks;

    StudentDetailGUI(){}

    StudentDetailGUI(int id){
        this.studentId=id;
        setSize(500,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        l1=new JLabel("View Details");
        l2=new JLabel("");
        l2.setVisible(false);

        b1=new JButton("Download resume");
        b2=new JButton("Back to home");

        header=new JPanel();
        header.setLayout(new GridLayout(1,2));
        header.add(l1);
        header.add(l2);

        footer=new JPanel(new GridLayout(1,2));
        footer.add(b1);
        footer.add(b2);

        pane=new JTabbedPane();
        profile=new JPanel(new GridLayout(0,2,10,10));
        skills=new JPanel(new GridLayout(0,2,10,10));
        education=new JPanel(new GridLayout(0,2,10,10));
        projects=new JPanel(new GridLayout(0,2,10,10));
        internships=new JPanel(new GridLayout(0,2,10,10));
        certifications=new JPanel(new GridLayout(0,2,10,10));
        achievements=new JPanel(new GridLayout(0,2,10,10));
        semesterMarks=new JPanel(new GridLayout(0,2,10,10));

        try{
            StudentProfileModel p=Mentor.getStudent(id);
            if(p==null){
                l2.setText("Student profile does not exist");
                l2.setVisible(true);
            }else{
                profile.add(new JLabel("Name"));
                profile.add(new JLabel(p.fullName));
                profile.add(new JLabel("Email"));
                profile.add(new JLabel(p.email));
                addProfileField(profile,p.phone,"Phone");
                addProfileField(profile,p.college,"College");
                addProfileField(profile,p.course,"Course");
                addProfileField(profile,p.year,"Year");
                addProfileField(profile,p.currentSemester,"Semester");
                addProfileField(profile,p.bio,"Bio");
                addProfileField(profile,p.githubUrl,"Github url");
                addProfileField(profile,p.linkedinUrl,"Linkedin url");

                if(!checkEmpty(p.skills,skills,"No skills added")){
                    for(int i=0;i<p.skills.length;i++){
                        addProfileField(skills,p.skills[i],"Skill "+(i+1));
                    }
                }

                if(!checkEmpty(p.education,education,"No education details added")){
                    for(int i=0;i<p.education.length;i++){
                        EducationModel ed=p.education[i];
                        if(p.education.length>1){
                            education.add(new JLabel("--- Education "+(i+1)+" ---"));
                            education.add(new JLabel(""));
                        }
                        addProfileField(education,ed.school,"School");
                        addProfileField(education,ed.board,"Board");
                        addProfileField(education,ed.startYear,"Start year");
                        addProfileField(education,ed.endYear,"End year");
                        addProfileField(education,ed.grade,"Grade");
                    }
                }

                if(!checkEmpty(p.projects,projects,"No projects added")){
                    for(int i=0;i<p.projects.length;i++){
                        ProjectModel pr=p.projects[i];
                        if(p.projects.length>1){
                            projects.add(new JLabel("--- Project "+(i+1)+" ---"));
                            projects.add(new JLabel(""));
                        }
                        addProfileField(projects,pr.title,"Title");
                        addProfileField(projects,pr.description,"Description");
                        addProfileField(projects,pr.githubUrl,"Github url");
                        addProfileField(projects,pr.liveUrl,"Live url");
                    }
                }

                if(!checkEmpty(p.internships,internships,"No internships added")){
                    for(int i=0;i<p.internships.length;i++){
                        InternshipModel in=p.internships[i];
                        if(p.internships.length>1){
                            internships.add(new JLabel("--- Internship "+(i+1)+" ---"));
                            internships.add(new JLabel(""));
                        }
                        addProfileField(internships,in.company,"Company");
                        addProfileField(internships,in.role,"Role");
                        addProfileField(internships,in.description,"Description");
                        addProfileField(internships,in.startDate,"Start date");
                        addProfileField(internships,in.endDate,"End date");
                    }
                }

                if(!checkEmpty(p.certifications,certifications,"No certifications added")){
                    for(int i=0;i<p.certifications.length;i++){
                        CertificationModel c=p.certifications[i];
                        if(p.certifications.length>1){
                            certifications.add(new JLabel("--- Certification "+(i+1)+" ---"));
                            certifications.add(new JLabel(""));
                        }
                        addProfileField(certifications,c.name,"Name");
                        addProfileField(certifications,c.issuingOrganization,"Organization");
                        addProfileField(certifications,c.credentialId,"Credential ID");
                        addProfileField(certifications,c.credentialUrl,"Credential url");
                    }
                }

                if(!checkEmpty(p.achievements,achievements,"No achievements added")){
                    for(int i=0;i<p.achievements.length;i++){
                        AchievementModel a=p.achievements[i];
                        if(p.achievements.length>1){
                            achievements.add(new JLabel("--- Achievement "+(i+1)+" ---"));
                            achievements.add(new JLabel(""));
                        }
                        addProfileField(achievements,a.title,"Title");
                        addProfileField(achievements,a.description,"Description");
                    }
                }

                if(!checkEmpty(p.semesterMarks,semesterMarks,"No semester marks added")){
                    for(int i=0;i<p.semesterMarks.length;i++){
                        SemesterMarkModel sm=p.semesterMarks[i];
                        addProfileField(semesterMarks,sm.marks,"Semester "+sm.semester);
                    }
                }
            }
        }catch(Exception e){
            l2.setText(e.toString());
            l2.setVisible(true);
        }

        pane.addTab("Profile",new JScrollPane(profile));
        pane.addTab("Skills",new JScrollPane(skills));
        pane.addTab("Education",new JScrollPane(education));
        pane.addTab("Projects",new JScrollPane(projects));
        pane.addTab("Internships",new JScrollPane(internships));
        pane.addTab("Certifications",new JScrollPane(certifications));
        pane.addTab("Achievements",new JScrollPane(achievements));
        pane.addTab("Semester Marks",new JScrollPane(semesterMarks));

        b1.addActionListener(this);
        b2.addActionListener(this);
        add(header,BorderLayout.NORTH);
        add(pane,BorderLayout.CENTER);
        add(footer,BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){
            try{
                Mentor.GenerateResume(studentId);
            }catch(Exception e){
                l2.setText(e.toString());
                l2.setVisible(true);
                System.out.println(e);
            }
        }else{
            new MentorGUI();
            dispose();
        }
    }

    private boolean checkEmpty(Object arr[],Container c,String msg){
        if(arr==null||arr.length==0){
            c.add(new JLabel(msg));
            c.add(new JLabel(""));
            return true;
        }
        return false;
    }
    private void addProfileField(Container c,String value,String field){
        if(value!=null&&value.trim().length()!=0){
            c.add(new JLabel(field));
            c.add(new JLabel(value));
        }
    }
    private void addProfileField(Container c,int value,String field){
        if(value!=-1){
            c.add(new JLabel(field));
            c.add(new JLabel(""+value));
        }
    }
    private void addProfileField(Container c,double value,String field){
        if(value>=0){
            c.add(new JLabel(field));
            c.add(new JLabel(""+value));
        }
    }
}