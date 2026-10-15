import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import data.*;
import session.Session;
import myExceptions.*;

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
    JButton bBack;
    JTabbedPane pane;
    JPanel header,footer;
    StudentProfileModel s;

    // Profile
    JPanel profile;
    JTextField tf1,tf2,tf3,tf4,tf5,tf6,tf7,tf8,tf9,tf10;
    JButton bSaveProfile;

    // Skills
    JPanel skills;
    JComboBox<String> cbSkills;
    JTextField tfNewSkill;
    JButton bAddSkill,bRemoveSkill;

    // Education
    JPanel education;
    JComboBox<String> cbEducation;
    JTextField tfEdSchool,tfEdBoard,tfEdStartYear,tfEdEndYear,tfEdGrade;
    JButton bSaveEd,bRemoveEd;

    // Projects
    JPanel projects;
    JComboBox<String> cbProjects;
    JTextField tfProjTitle,tfProjDesc,tfProjGit,tfProjLive;
    JButton bSaveProj,bRemoveProj;

    // Internships
    JPanel internships;
    JComboBox<String> cbInternships;
    JTextField tfInCompany,tfInRole,tfInDesc,tfInStartDate,tfInEndDate;
    JButton bSaveIn,bRemoveIn;

    // Certifications
    JPanel certifications;
    JComboBox<String> cbCertifications;
    JTextField tfCertName,tfCertOrg,tfCertId,tfCertUrl;
    JButton bSaveCert,bRemoveCert;

    // Achievements
    JPanel achievements;
    JComboBox<String> cbAchievements;
    JTextField tfAchTitle,tfAchDesc;
    JButton bSaveAch,bRemoveAch;

    JPanel semesterMarks;
    JTextField tfCurSem,tfSemNum,tfSemMarks;
    JComboBox<String> cbMarks;
    JButton bSetCurSem,bSaveMarks,bRemoveMarks;

    EditDetailsGUI(){
        this(0);
    }

    EditDetailsGUI(int tabIndex){
        setSize(500,500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        l1=new JLabel("Edit Details");
        l2=new JLabel("");
        l2.setVisible(false);

        header=new JPanel(new GridLayout(1,2));
        header.add(l1);
        header.add(l2);

        footer=new JPanel(new GridLayout(1,1));
        bBack=new JButton("Back to home");
        bBack.addActionListener(this);
        footer.add(bBack);

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
            s=StudentProfile.getProfile();
            if(s==null){
                l2.setText("Student profile does not exist");
                l2.setVisible(true);
            }else{
                // Profile
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
                bSaveProfile=new JButton("Save Profile");
                bSaveProfile.addActionListener(this);
                profile.add(bSaveProfile);
                profile.add(new JLabel(""));

                // Skills
                skills.add(new JLabel("Select Skill to Remove"));
                String skillItems[];
                if(s.skills!=null && s.skills.length>0){
                    skillItems=s.skills;
                }else{
                    skillItems=new String[]{"No skills added"};
                }
                cbSkills=new JComboBox<>(skillItems);
                skills.add(cbSkills);

                bRemoveSkill=new JButton("Remove Skill");
                bRemoveSkill.addActionListener(this);
                if(s.skills==null || s.skills.length==0){
                    bRemoveSkill.setEnabled(false);
                }
                skills.add(bRemoveSkill);
                skills.add(new JLabel(""));

                skills.add(new JLabel("New Skill Name"));
                tfNewSkill=new JTextField();
                skills.add(tfNewSkill);

                bAddSkill=new JButton("Add Skill");
                bAddSkill.addActionListener(this);
                skills.add(bAddSkill);
                skills.add(new JLabel(""));

                // Education
                education.add(new JLabel("Select Education"));
                String edItems[]=new String[(s.education!=null?s.education.length:0)+1];
                edItems[0]="--- Add New Education ---";
                if(s.education!=null){
                    for(int i=0;i<s.education.length;i++){
                        edItems[i+1]="Ed "+(i+1)+": "+s.education[i].school;
                    }
                }
                cbEducation=new JComboBox<>(edItems);
                education.add(cbEducation);

                tfEdSchool=addEditField(education,"","School");
                tfEdBoard=addEditField(education,"","Board");
                tfEdStartYear=addEditField(education,"","Start Year");
                tfEdEndYear=addEditField(education,"","End Year");
                tfEdGrade=addEditField(education,"","Grade");

                bSaveEd=new JButton("Add Education");
                bSaveEd.addActionListener(this);
                education.add(bSaveEd);

                bRemoveEd=new JButton("Remove Education");
                bRemoveEd.setEnabled(false);
                bRemoveEd.addActionListener(this);
                education.add(bRemoveEd);

                cbEducation.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange()==ItemEvent.SELECTED){
                            int idx=cbEducation.getSelectedIndex();
                            if(idx<=0){
                                tfEdSchool.setText("");
                                tfEdBoard.setText("");
                                tfEdStartYear.setText("");
                                tfEdEndYear.setText("");
                                tfEdGrade.setText("");
                                bSaveEd.setText("Add Education");
                                bRemoveEd.setEnabled(false);
                            }else{
                                EducationModel ed=s.education[idx-1];
                                tfEdSchool.setText(ed.school==null?"":ed.school);
                                tfEdBoard.setText(ed.board==null?"":ed.board);
                                tfEdStartYear.setText(ed.startYear==-1||ed.startYear==0?"":(""+ed.startYear));
                                tfEdEndYear.setText(ed.endYear==-1||ed.endYear==0?"":(""+ed.endYear));
                                tfEdGrade.setText(ed.grade==null?"":ed.grade);
                                bSaveEd.setText("Update Education");
                                bRemoveEd.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.education!=null && s.education.length>0){
                    cbEducation.setSelectedIndex(1);
                }

                // Projects
                projects.add(new JLabel("Select Project"));
                String projItems[]=new String[(s.projects!=null?s.projects.length:0)+1];
                projItems[0]="--- Add New Project ---";
                if(s.projects!=null){
                    for(int i=0;i<s.projects.length;i++){
                        projItems[i+1]="Proj "+(i+1)+": "+s.projects[i].title;
                    }
                }
                cbProjects=new JComboBox<>(projItems);
                projects.add(cbProjects);

                tfProjTitle=addEditField(projects,"","Title");
                tfProjDesc=addEditField(projects,"","Description");
                tfProjGit=addEditField(projects,"","Github url");
                tfProjLive=addEditField(projects,"","Live url");

                bSaveProj=new JButton("Add Project");
                bSaveProj.addActionListener(this);
                projects.add(bSaveProj);

                bRemoveProj=new JButton("Remove Project");
                bRemoveProj.setEnabled(false);
                bRemoveProj.addActionListener(this);
                projects.add(bRemoveProj);

                cbProjects.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange()==ItemEvent.SELECTED){
                            int idx=cbProjects.getSelectedIndex();
                            if(idx<=0){
                                tfProjTitle.setText("");
                                tfProjDesc.setText("");
                                tfProjGit.setText("");
                                tfProjLive.setText("");
                                bSaveProj.setText("Add Project");
                                bRemoveProj.setEnabled(false);
                            }else{
                                ProjectModel pr=s.projects[idx-1];
                                tfProjTitle.setText(pr.title==null?"":pr.title);
                                tfProjDesc.setText(pr.description==null?"":pr.description);
                                tfProjGit.setText(pr.githubUrl==null?"":pr.githubUrl);
                                tfProjLive.setText(pr.liveUrl==null?"":pr.liveUrl);
                                bSaveProj.setText("Update Project");
                                bRemoveProj.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.projects!=null && s.projects.length>0){
                    cbProjects.setSelectedIndex(1);
                }

                // Internships
                internships.add(new JLabel("Select Internship"));
                String inItems[]=new String[(s.internships!=null?s.internships.length:0)+1];
                inItems[0]="--- Add New Internship ---";
                if(s.internships!=null){
                    for(int i=0;i<s.internships.length;i++){
                        inItems[i+1]="In "+(i+1)+": "+s.internships[i].company;
                    }
                }
                cbInternships=new JComboBox<>(inItems);
                internships.add(cbInternships);

                tfInCompany=addEditField(internships,"","Company");
                tfInRole=addEditField(internships,"","Role");
                tfInDesc=addEditField(internships,"","Description");
                tfInStartDate=addEditField(internships,"","Start Date");
                tfInEndDate=addEditField(internships,"","End Date");

                bSaveIn=new JButton("Add Internship");
                bSaveIn.addActionListener(this);
                internships.add(bSaveIn);

                bRemoveIn=new JButton("Remove Internship");
                bRemoveIn.setEnabled(false);
                bRemoveIn.addActionListener(this);
                internships.add(bRemoveIn);

                cbInternships.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange()==ItemEvent.SELECTED){
                            int idx=cbInternships.getSelectedIndex();
                            if(idx<=0){
                                tfInCompany.setText("");
                                tfInRole.setText("");
                                tfInDesc.setText("");
                                tfInStartDate.setText("");
                                tfInEndDate.setText("");
                                bSaveIn.setText("Add Internship");
                                bRemoveIn.setEnabled(false);
                            }else{
                                InternshipModel in=s.internships[idx-1];
                                tfInCompany.setText(in.company==null?"":in.company);
                                tfInRole.setText(in.role==null?"":in.role);
                                tfInDesc.setText(in.description==null?"":in.description);
                                tfInStartDate.setText(in.startDate==null?"":in.startDate);
                                tfInEndDate.setText(in.endDate==null?"":in.endDate);
                                bSaveIn.setText("Update Internship");
                                bRemoveIn.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.internships!=null && s.internships.length>0){
                    cbInternships.setSelectedIndex(1);
                }

                // Certifications
                certifications.add(new JLabel("Select Certification"));
                String certItems[]=new String[(s.certifications!=null?s.certifications.length:0)+1];
                certItems[0]="--- Add New Certification ---";
                if(s.certifications!=null){
                    for(int i=0;i<s.certifications.length;i++){
                        certItems[i+1]="Cert "+(i+1)+": "+s.certifications[i].name;
                    }
                }
                cbCertifications=new JComboBox<>(certItems);
                certifications.add(cbCertifications);

                tfCertName=addEditField(certifications,"","Name");
                tfCertOrg=addEditField(certifications,"","Organization");
                tfCertId=addEditField(certifications,"","Credential ID");
                tfCertUrl=addEditField(certifications,"","Credential URL");

                bSaveCert=new JButton("Add Certification");
                bSaveCert.addActionListener(this);
                certifications.add(bSaveCert);

                bRemoveCert=new JButton("Remove Certification");
                bRemoveCert.setEnabled(false);
                bRemoveCert.addActionListener(this);
                certifications.add(bRemoveCert);

                cbCertifications.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange()==ItemEvent.SELECTED){
                            int idx=cbCertifications.getSelectedIndex();
                            if(idx<=0){
                                tfCertName.setText("");
                                tfCertOrg.setText("");
                                tfCertId.setText("");
                                tfCertUrl.setText("");
                                bSaveCert.setText("Add Certification");
                                bRemoveCert.setEnabled(false);
                            }else{
                                CertificationModel c=s.certifications[idx-1];
                                tfCertName.setText(c.name==null?"":c.name);
                                tfCertOrg.setText(c.issuingOrganization==null?"":c.issuingOrganization);
                                tfCertId.setText(c.credentialId==null?"":c.credentialId);
                                tfCertUrl.setText(c.credentialUrl==null?"":c.credentialUrl);
                                bSaveCert.setText("Update Certification");
                                bRemoveCert.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.certifications!=null && s.certifications.length>0){
                    cbCertifications.setSelectedIndex(1);
                }

                // Achievements
                achievements.add(new JLabel("Select Achievement"));
                String achItems[]=new String[(s.achievements!=null?s.achievements.length:0)+1];
                achItems[0]="--- Add New Achievement ---";
                if(s.achievements!=null){
                    for(int i=0;i<s.achievements.length;i++){
                        achItems[i+1]="Ach "+(i+1)+": "+s.achievements[i].title;
                    }
                }
                cbAchievements=new JComboBox<>(achItems);
                achievements.add(cbAchievements);

                tfAchTitle=addEditField(achievements,"","Title");
                tfAchDesc=addEditField(achievements,"","Description");

                bSaveAch=new JButton("Add Achievement");
                bSaveAch.addActionListener(this);
                achievements.add(bSaveAch);

                bRemoveAch=new JButton("Remove Achievement");
                bRemoveAch.setEnabled(false);
                bRemoveAch.addActionListener(this);
                achievements.add(bRemoveAch);

                cbAchievements.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange()==ItemEvent.SELECTED){
                            int idx=cbAchievements.getSelectedIndex();
                            if(idx<=0){
                                tfAchTitle.setText("");
                                tfAchDesc.setText("");
                                bSaveAch.setText("Add Achievement");
                                bRemoveAch.setEnabled(false);
                            }else{
                                AchievementModel a=s.achievements[idx-1];
                                tfAchTitle.setText(a.title==null?"":a.title);
                                tfAchDesc.setText(a.description==null?"":a.description);
                                bSaveAch.setText("Update Achievement");
                                bRemoveAch.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.achievements!=null && s.achievements.length>0){
                    cbAchievements.setSelectedIndex(1);
                }

                // Semester Marks
                semesterMarks.add(new JLabel("Current Semester"));
                tfCurSem=new JTextField(""+(s.currentSemester<=0?1:s.currentSemester));
                semesterMarks.add(tfCurSem);

                bSetCurSem=new JButton("Update Current Semester");
                bSetCurSem.addActionListener(this);
                semesterMarks.add(bSetCurSem);
                semesterMarks.add(new JLabel(""));

                semesterMarks.add(new JLabel("Select Semester Mark"));
                String markItems[]=new String[(s.semesterMarks!=null?s.semesterMarks.length:0)+1];
                markItems[0]="--- Add / Set Semester Marks ---";
                if(s.semesterMarks!=null){
                    for(int i=0;i<s.semesterMarks.length;i++){
                        markItems[i+1]="Semester "+s.semesterMarks[i].semester+": "+s.semesterMarks[i].marks;
                    }
                }
                cbMarks=new JComboBox<>(markItems);
                semesterMarks.add(cbMarks);

                tfSemNum=addEditField(semesterMarks,"","Semester Number");
                tfSemMarks=addEditField(semesterMarks,"","Marks");

                bSaveMarks=new JButton("Add Marks");
                bSaveMarks.addActionListener(this);
                semesterMarks.add(bSaveMarks);

                bRemoveMarks=new JButton("Remove Marks");
                bRemoveMarks.setEnabled(false);
                bRemoveMarks.addActionListener(this);
                semesterMarks.add(bRemoveMarks);

                cbMarks.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange()==ItemEvent.SELECTED){
                            int idx=cbMarks.getSelectedIndex();
                            if(idx<=0){
                                tfSemNum.setText("");
                                tfSemNum.setEditable(true);
                                tfSemMarks.setText("");
                                bSaveMarks.setText("Add Marks");
                                bRemoveMarks.setEnabled(false);
                            }else{
                                SemesterMarkModel sm=s.semesterMarks[idx-1];
                                tfSemNum.setText(""+sm.semester);
                                tfSemNum.setEditable(false);
                                tfSemMarks.setText(""+sm.marks);
                                bSaveMarks.setText("Update Marks");
                                bRemoveMarks.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.semesterMarks!=null && s.semesterMarks.length>0){
                    cbMarks.setSelectedIndex(1);
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

        if(tabIndex>=0 && tabIndex<pane.getTabCount()){
            pane.setSelectedIndex(tabIndex);
        }

        add(header,BorderLayout.NORTH);
        add(pane,BorderLayout.CENTER);
        add(footer,BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        Object src=ae.getSource();
        if(src==bBack){
            new StudentGUI();
            dispose();
            return;
        }
        try{
            if(src==bSaveProfile){
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
            }else if(src==bAddSkill){
                String sk=tfNewSkill.getText().trim();
                if(sk.length()==0){
                    throw new MissingFieldException("skill");
                }
                Skills.addSkill(sk);
                new EditDetailsGUI(1);
                dispose();
            }else if(src==bRemoveSkill){
                if(cbSkills.getSelectedItem()!=null && !cbSkills.getSelectedItem().equals("No skills added")){
                    Skills.removeSkill((String)cbSkills.getSelectedItem());
                    new EditDetailsGUI(1);
                    dispose();
                }
            }else if(src==bSaveEd){
                int idx=cbEducation.getSelectedIndex();
                String school=tfEdSchool.getText().trim();
                String board=tfEdBoard.getText().trim();
                int startYear=-1;
                String sy=tfEdStartYear.getText().trim();
                if(sy.length()!=0){
                    try{
                        startYear=Integer.parseInt(sy);
                    }catch(NumberFormatException ex){
                        l2.setText("Start Year must be a number");
                        l2.setVisible(true);
                        return;
                    }
                }
                int endYear=-1;
                String ey=tfEdEndYear.getText().trim();
                if(ey.length()!=0){
                    try{
                        endYear=Integer.parseInt(ey);
                    }catch(NumberFormatException ex){
                        l2.setText("End Year must be a number");
                        l2.setVisible(true);
                        return;
                    }
                }
                String grade=tfEdGrade.getText().trim();
                if(idx<=0){
                    Education.addEducation(school,board,startYear,endYear,grade);
                }else{
                    Education.updateEducation(s.education[idx-1].id,school,board,startYear,endYear,grade);
                }
                new EditDetailsGUI(2);
                dispose();
            }else if(src==bRemoveEd){
                int idx=cbEducation.getSelectedIndex();
                if(idx>0){
                    Education.removeEducation(s.education[idx-1].id);
                    new EditDetailsGUI(2);
                    dispose();
                }
            }else if(src==bSaveProj){
                int idx=cbProjects.getSelectedIndex();
                String title=tfProjTitle.getText().trim();
                String desc=tfProjDesc.getText().trim();
                String git=tfProjGit.getText().trim();
                String live=tfProjLive.getText().trim();
                if(idx<=0){
                    Projects.addProject(title,desc,git,live);
                }else{
                    Projects.updateProject(s.projects[idx-1].id,title,desc,git,live);
                }
                new EditDetailsGUI(3);
                dispose();
            }else if(src==bRemoveProj){
                int idx=cbProjects.getSelectedIndex();
                if(idx>0){
                    Projects.removeProject(s.projects[idx-1].id);
                    new EditDetailsGUI(3);
                    dispose();
                }
            }else if(src==bSaveIn){
                int idx=cbInternships.getSelectedIndex();
                String company=tfInCompany.getText().trim();
                String role=tfInRole.getText().trim();
                String desc=tfInDesc.getText().trim();
                String start=tfInStartDate.getText().trim();
                String end=tfInEndDate.getText().trim();
                if(idx<=0){
                    Internships.addInternship(company,role,desc,start,end);
                }else{
                    Internships.updateInternship(s.internships[idx-1].id,company,role,desc,start,end);
                }
                new EditDetailsGUI(4);
                dispose();
            }else if(src==bRemoveIn){
                int idx=cbInternships.getSelectedIndex();
                if(idx>0){
                    Internships.removeInternship(s.internships[idx-1].id);
                    new EditDetailsGUI(4);
                    dispose();
                }
            }else if(src==bSaveCert){
                int idx=cbCertifications.getSelectedIndex();
                String name=tfCertName.getText().trim();
                String org=tfCertOrg.getText().trim();
                String credId=tfCertId.getText().trim();
                String credUrl=tfCertUrl.getText().trim();
                if(idx<=0){
                    Certifications.addCertification(name,org,credId,credUrl);
                }else{
                    Certifications.updateCertification(s.certifications[idx-1].id,name,org,credId,credUrl);
                }
                new EditDetailsGUI(5);
                dispose();
            }else if(src==bRemoveCert){
                int idx=cbCertifications.getSelectedIndex();
                if(idx>0){
                    Certifications.removeCertification(s.certifications[idx-1].id);
                    new EditDetailsGUI(5);
                    dispose();
                }
            }else if(src==bSaveAch){
                int idx=cbAchievements.getSelectedIndex();
                String title=tfAchTitle.getText().trim();
                String desc=tfAchDesc.getText().trim();
                if(idx<=0){
                    Achievements.addAchievement(title,desc);
                }else{
                    Achievements.updateAchievement(s.achievements[idx-1].id,title,desc);
                }
                new EditDetailsGUI(6);
                dispose();
            }else if(src==bRemoveAch){
                int idx=cbAchievements.getSelectedIndex();
                if(idx>0){
                    Achievements.removeAchievement(s.achievements[idx-1].id);
                    new EditDetailsGUI(6);
                    dispose();
                }
            }else if(src==bSetCurSem){
                int sem=Integer.parseInt(tfCurSem.getText().trim());
                SemesterMarks.setCurrentSemester(sem);
                new EditDetailsGUI(7);
                dispose();
            }else if(src==bSaveMarks){
                int idx=cbMarks.getSelectedIndex();
                double marks=Double.parseDouble(tfSemMarks.getText().trim());
                if(idx<=0){
                    int sem=Integer.parseInt(tfSemNum.getText().trim());
                    SemesterMarks.setMarks(sem,marks);
                }else{
                    int sem=s.semesterMarks[idx-1].semester;
                    SemesterMarks.updateMarks(sem,marks);
                }
                new EditDetailsGUI(7);
                dispose();
            }else if(src==bRemoveMarks){
                int idx=cbMarks.getSelectedIndex();
                if(idx>0){
                    SemesterMarks.removeMarks(s.semesterMarks[idx-1].semester);
                    new EditDetailsGUI(7);
                    dispose();
                }
            }
        }catch(Exception ex){
            l2.setText(ex.toString());
            l2.setVisible(true);
        }
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
}


