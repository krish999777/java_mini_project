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
        b3=new JButton("Download Resume");
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
            new EditDetailsGUI();
            dispose();
        }else if(b==b3){
            try{
                ResumeGenerator.generate();
            }catch(Exception e){
                System.out.println(e);
            }
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
    JPanel header,footer,profile,skills,education,projects,internships,certifications,achievements,semesterMarks;
    ViewDetailsGUI(){
        setSize(500,500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
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
            StudentProfileModel s=StudentProfile.getProfile();
            if(s==null){
                l2.setText("Student profile does not exist");
                l2.setVisible(true);
            }else{
                profile.add(new JLabel("Name"));
                profile.add(new JLabel(s.fullName));
                profile.add(new JLabel("Email"));
                profile.add(new JLabel(s.email));
                addProfileField(profile,s.phone,"Phone");
                addProfileField(profile,s.college,"College");
                addProfileField(profile,s.course,"Course");
                addProfileField(profile,s.year,"Year");
                addProfileField(profile,s.currentSemester,"Semester");
                addProfileField(profile,s.bio,"Bio");
                addProfileField(profile,s.githubUrl,"Github url");
                addProfileField(profile,s.linkedinUrl,"Linkedin url");

                if(!checkEmpty(s.skills,skills,"No skills added")){
                    for(int i=0;i<s.skills.length;i++){
                        addProfileField(skills,s.skills[i],"Skill "+(i+1));
                    }
                }

                if(!checkEmpty(s.education,education,"No education details added")){
                    for(int i=0;i<s.education.length;i++){
                        EducationModel ed=s.education[i];
                        if(s.education.length>1){
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

                if(!checkEmpty(s.projects,projects,"No projects added")){
                    for(int i=0;i<s.projects.length;i++){
                        ProjectModel pr=s.projects[i];
                        if(s.projects.length>1){
                            projects.add(new JLabel("--- Project "+(i+1)+" ---"));
                            projects.add(new JLabel(""));
                        }
                        addProfileField(projects,pr.title,"Title");
                        addProfileField(projects,pr.description,"Description");
                        addProfileField(projects,pr.githubUrl,"Github url");
                        addProfileField(projects,pr.liveUrl,"Live url");
                    }
                }

                if(!checkEmpty(s.internships,internships,"No internships added")){
                    for(int i=0;i<s.internships.length;i++){
                        InternshipModel in=s.internships[i];
                        if(s.internships.length>1){
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

                if(!checkEmpty(s.certifications,certifications,"No certifications added")){
                    for(int i=0;i<s.certifications.length;i++){
                        CertificationModel c=s.certifications[i];
                        if(s.certifications.length>1){
                            certifications.add(new JLabel("--- Certification "+(i+1)+" ---"));
                            certifications.add(new JLabel(""));
                        }
                        addProfileField(certifications,c.name,"Name");
                        addProfileField(certifications,c.issuingOrganization,"Organization");
                        addProfileField(certifications,c.credentialId,"Credential ID");
                        addProfileField(certifications,c.credentialUrl,"Credential url");
                    }
                }

                if(!checkEmpty(s.achievements,achievements,"No achievements added")){
                    for(int i=0;i<s.achievements.length;i++){
                        AchievementModel a=s.achievements[i];
                        if(s.achievements.length>1){
                            achievements.add(new JLabel("--- Achievement "+(i+1)+" ---"));
                            achievements.add(new JLabel(""));
                        }
                        addProfileField(achievements,a.title,"Title");
                        addProfileField(achievements,a.description,"Description");
                    }
                }

                if(!checkEmpty(s.semesterMarks,semesterMarks,"No semester marks added")){
                    for(int i=0;i<s.semesterMarks.length;i++){
                        SemesterMarkModel sm=s.semesterMarks[i];
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
                ResumeGenerator.generate();
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            new StudentGUI();
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

class EditDetailsGUI extends JFrame implements ActionListener{
    JLabel l1,l2;
    JButton b1,b2;
    JTabbedPane pane;
    JPanel header,footer,profile,skills,education,projects,internships,certifications,achievements,semesterMarks;
    JTextField tf1,tf2,tf3,tf4,tf5,tf6,tf7,tf8,tf9,tf10;
    EditDetailsGUI(){
        setSize(500,500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        l1=new JLabel("Edit Details");
        l2=new JLabel("");
        l2.setVisible(false);

        b1=new JButton("Save profile");
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
            StudentProfileModel s=StudentProfile.getProfile();
            if(s==null){
                l2.setText("Student profile does not exist");
                l2.setVisible(true);
            }else{
                tf1=addEditField(profile,s.fullName,"Name");
                tf2=addEditField(profile,s.email,"Email");
                tf3=addEditField(profile,s.phone,"Phone");
                tf4=addEditField(profile,s.college,"College");
                tf5=addEditField(profile,s.course,"Course");
                tf6=addEditField(profile,s.year,"Year");
                tf7=addEditField(profile,s.currentSemester<=0?1:s.currentSemester,"Semester");
                tf8=addEditField(profile,s.bio,"Bio");
                tf9=addEditField(profile,s.githubUrl,"Github url");
                tf10=addEditField(profile,s.linkedinUrl,"Linkedin url");

                if(!checkEmpty(s.skills,skills,"No skills added")){
                    for(int i=0;i<s.skills.length;i++){
                        addEditField(skills,s.skills[i],"Skill "+(i+1));
                    }
                }

                if(!checkEmpty(s.education,education,"No education details added")){
                    for(int i=0;i<s.education.length;i++){
                        EducationModel ed=s.education[i];
                        if(s.education.length>1){
                            education.add(new JLabel("--- Education "+(i+1)+" ---"));
                            education.add(new JLabel(""));
                        }
                        addEditField(education,ed.school,"School");
                        addEditField(education,ed.board,"Board");
                        addEditField(education,ed.startYear,"Start year");
                        addEditField(education,ed.endYear,"End year");
                        addEditField(education,ed.grade,"Grade");
                    }
                }

                if(!checkEmpty(s.projects,projects,"No projects added")){
                    for(int i=0;i<s.projects.length;i++){
                        ProjectModel pr=s.projects[i];
                        if(s.projects.length>1){
                            projects.add(new JLabel("--- Project "+(i+1)+" ---"));
                            projects.add(new JLabel(""));
                        }
                        addEditField(projects,pr.title,"Title");
                        addEditField(projects,pr.description,"Description");
                        addEditField(projects,pr.githubUrl,"Github url");
                        addEditField(projects,pr.liveUrl,"Live url");
                    }
                }

                if(!checkEmpty(s.internships,internships,"No internships added")){
                    for(int i=0;i<s.internships.length;i++){
                        InternshipModel in=s.internships[i];
                        if(s.internships.length>1){
                            internships.add(new JLabel("--- Internship "+(i+1)+" ---"));
                            internships.add(new JLabel(""));
                        }
                        addEditField(internships,in.company,"Company");
                        addEditField(internships,in.role,"Role");
                        addEditField(internships,in.description,"Description");
                        addEditField(internships,in.startDate,"Start date");
                        addEditField(internships,in.endDate,"End date");
                    }
                }

                if(!checkEmpty(s.certifications,certifications,"No certifications added")){
                    for(int i=0;i<s.certifications.length;i++){
                        CertificationModel c=s.certifications[i];
                        if(s.certifications.length>1){
                            certifications.add(new JLabel("--- Certification "+(i+1)+" ---"));
                            certifications.add(new JLabel(""));
                        }
                        addEditField(certifications,c.name,"Name");
                        addEditField(certifications,c.issuingOrganization,"Organization");
                        addEditField(certifications,c.credentialId,"Credential ID");
                        addEditField(certifications,c.credentialUrl,"Credential url");
                    }
                }

                if(!checkEmpty(s.achievements,achievements,"No achievements added")){
                    for(int i=0;i<s.achievements.length;i++){
                        AchievementModel a=s.achievements[i];
                        if(s.achievements.length>1){
                            achievements.add(new JLabel("--- Achievement "+(i+1)+" ---"));
                            achievements.add(new JLabel(""));
                        }
                        addEditField(achievements,a.title,"Title");
                        addEditField(achievements,a.description,"Description");
                    }
                }

                if(!checkEmpty(s.semesterMarks,semesterMarks,"No semester marks added")){
                    for(int i=0;i<s.semesterMarks.length;i++){
                        SemesterMarkModel sm=s.semesterMarks[i];
                        addEditField(semesterMarks,sm.marks,"Semester "+sm.semester);
                    }
                }
            }
        }catch(Exception e){
            l2.setText(e.toString());
            l2.setVisible(true);
        }

        pane.addTab("Profile",profile);
        pane.addTab("Skills",skills);
        pane.addTab("Education",education);
        pane.addTab("Projects",projects);
        pane.addTab("Internships",internships);
        pane.addTab("Certifications",certifications);
        pane.addTab("Achievements",achievements);
        pane.addTab("Semester Marks",semesterMarks);

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
                if(tf1==null){
                    return;
                }
                String fullName=tf1.getText();
                String email=tf2.getText();
                String phone=tf3.getText();
                String college=tf4.getText();
                String course=tf5.getText();
                int year=-1;
                String yStr=tf6.getText().trim();
                if(yStr.length()!=0){
                    try{
                        year=Integer.parseInt(yStr);
                    }catch(NumberFormatException ex){
                        l2.setText("Year must be a number");
                        l2.setVisible(true);
                        return;
                    }
                }
                int sem=1;
                String semStr=tf7.getText().trim();
                if(semStr.length()!=0){
                    try{
                        sem=Integer.parseInt(semStr);
                    }catch(NumberFormatException ex){
                        l2.setText("Semester must be a number");
                        l2.setVisible(true);
                        return;
                    }
                }
                String bio=tf8.getText();
                String githubUrl=tf9.getText();
                String linkedinUrl=tf10.getText();
                StudentProfile.updateProfile(fullName,email,phone,college,course,year,sem,bio,githubUrl,linkedinUrl);
                l2.setText("Profile updated successfully");
                l2.setVisible(true);
            }catch(Exception ex){
                l2.setText(ex.toString());
                l2.setVisible(true);
            }
        }else{
            new StudentGUI();
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
    private JTextField addEditField(Container c,String value,String field){
        c.add(new JLabel(field));
        JTextField tf=new JTextField(value==null?"":value);
        c.add(tf);
        return tf;
    }
    private JTextField addEditField(Container c,int value,String field){
        c.add(new JLabel(field));
        JTextField tf=new JTextField(value==-1||value==0?"":(""+value));
        c.add(tf);
        return tf;
    }
    private JTextField addEditField(Container c,double value,String field){
        c.add(new JLabel(field));
        JTextField tf=new JTextField(value>=0?(""+value):"");
        c.add(tf);
        return tf;
    }
}


