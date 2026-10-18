import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import data.*;
import session.Session;
import myExceptions.*;

public class StudentGUI extends JFrame implements ActionListener{
    JButton b1,b2,b3,b4;

    public StudentGUI(){
        try{
            if(StudentProfile.getProfile()==null){
                new CreateProfileGUI();
                return;
            }
        }catch(Exception e){
            System.out.println(e);
        }

        setTitle("Student Dashboard");
        setSize(480, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(248, 250, 252));

        // Header with clear title at the top
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Student Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);

        // Center button grid with generous spacing and styling
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(new Color(248, 250, 252));
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel center = new JPanel(new GridLayout(2, 2, 16, 16));
        center.setOpaque(false);

        b1 = createMenuButton("View Details", new Color(37, 99, 235));
        b2 = createMenuButton("Edit Details", new Color(79, 70, 229));
        b3 = createMenuButton("Download Resume", new Color(13, 148, 136));
        b4 = createMenuButton("Logout", new Color(220, 38, 38));

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        center.add(b1);
        center.add(b2);
        center.add(b3);
        center.add(b4);

        centerWrapper.add(center, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createMenuButton(String text, Color bg){
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bg.darker(), 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        return btn;
    }

    public void actionPerformed(ActionEvent ae){
        JButton b = (JButton)ae.getSource();
        if(b == b1){
            new ViewDetailsGUI();
            dispose();
        }else if(b == b2){
            new EditDetailsGUI();
            dispose();
        }else if(b == b3){
            try{
                ResumeGenerator.generate();
                JOptionPane.showMessageDialog(this, "Resume generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.toString(), "Resume Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            Session.logout();
            new LoginGUI();
            dispose();
        }
    }
}

class CreateProfileGUI extends JFrame implements ActionListener{
    JLabel l1;
    JTextField tf1,tf2,tf3,tf4,tf5,tf6,tf7,tf8,tf9;
    JButton b1,b2;
    JPanel header,footer,form;

    public CreateProfileGUI(){
        setTitle("Create Student Profile");
        setSize(560, 640);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(248, 250, 252));

        // Header with clear title at the top
        header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        l1 = new JLabel("Create Student Profile", SwingConstants.CENTER);
        l1.setFont(new Font("SansSerif", Font.BOLD, 20));
        l1.setForeground(Color.WHITE);
        header.add(l1, BorderLayout.CENTER);

        // Footer buttons
        footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        footer.setBackground(new Color(248, 250, 252));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        b1 = new JButton("Create Profile");
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

        footer.add(b1);
        footer.add(b2);

        form = new JPanel(new GridLayout(0, 2, 12, 14));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        tf1 = addField(form, "Name*");
        tf2 = addField(form, "Email*");
        tf3 = addField(form, "Phone");
        tf4 = addField(form, "College");
        tf5 = addField(form, "Course");
        tf6 = addField(form, "Year");
        tf7 = addField(form, "Bio");
        tf8 = addField(form, "Github url");
        tf9 = addField(form, "Linkedin url");

        b1.addActionListener(this);
        b2.addActionListener(this);

        JScrollPane scrollPane = new JScrollPane(form);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        JButton b = (JButton)ae.getSource();
        if(b == b1){
            try{
                String fullName = tf1.getText().trim();
                String email = tf2.getText().trim();
                String phone = tf3.getText().trim();
                String college = tf4.getText().trim();
                String course = tf5.getText().trim();
                int year = -1;
                String yStr = tf6.getText().trim();
                if(yStr.length() != 0){
                    try{
                        year = Integer.parseInt(yStr);
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(this, "Year must be a number", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String bio = tf7.getText().trim();
                String githubUrl = tf8.getText().trim();
                String linkedinUrl = tf9.getText().trim();

                StudentProfile.createProfile(fullName, email, phone, college, course, year, bio, githubUrl, linkedinUrl);
                JOptionPane.showMessageDialog(this, "Profile created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new StudentGUI();
                dispose();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.toString(), "Error Creating Profile", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            Session.logout();
            new LoginGUI();
            dispose();
        }
    }

    private JTextField addField(Container c, String label){
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(new Color(30, 41, 59));
        c.add(lbl);

        JTextField tf = new JTextField();
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c.add(tf);
        return tf;
    }
}

class ViewDetailsGUI extends JFrame implements ActionListener{
    JLabel l1;
    JButton b1,b2;
    JTabbedPane pane;
    JPanel header,footer,profile,skills,education,projects,internships,certifications,achievements,semesterMarks;

    public ViewDetailsGUI(){
        setTitle("Student Profile - View Details");
        setSize(720, 620);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(248, 250, 252));

        // Header with clear title at the top
        header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        l1 = new JLabel("My Profile Details", SwingConstants.CENTER);
        l1.setFont(new Font("SansSerif", Font.BOLD, 20));
        l1.setForeground(Color.WHITE);
        header.add(l1, BorderLayout.CENTER);

        // Footer buttons
        footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        footer.setBackground(new Color(248, 250, 252));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        b1 = new JButton("Download Resume");
        b1.setFont(new Font("SansSerif", Font.BOLD, 13));
        b1.setBackground(new Color(37, 99, 235));
        b1.setForeground(Color.WHITE);
        b1.setFocusPainted(false);
        b1.setOpaque(true);
        b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(29, 78, 216), 1),
            BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));

        b2 = new JButton("Back to Home");
        b2.setFont(new Font("SansSerif", Font.BOLD, 13));
        b2.setBackground(new Color(100, 116, 139));
        b2.setForeground(Color.WHITE);
        b2.setFocusPainted(false);
        b2.setOpaque(true);
        b2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(71, 85, 105), 1),
            BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));

        footer.add(b1);
        footer.add(b2);

        pane = new JTabbedPane();
        pane.setFont(new Font("SansSerif", Font.PLAIN, 13));

        profile = createTabPanel();
        skills = createTabPanel();
        education = createTabPanel();
        projects = createTabPanel();
        internships = createTabPanel();
        certifications = createTabPanel();
        achievements = createTabPanel();
        semesterMarks = createTabPanel();

        try{
            StudentProfileModel s = StudentProfile.getProfile();
            if(s == null){
                JOptionPane.showMessageDialog(this, "Student profile does not exist", "Profile Not Found", JOptionPane.ERROR_MESSAGE);
                JLabel noProf = new JLabel("No profile found.");
                noProf.setFont(new Font("SansSerif", Font.ITALIC, 13));
                profile.add(noProf);
                JButton bCreate = new JButton("Create Profile");
                bCreate.setFont(new Font("SansSerif", Font.BOLD, 13));
                bCreate.setBackground(new Color(37, 99, 235));
                bCreate.setForeground(Color.WHITE);
                bCreate.setFocusPainted(false);
                bCreate.setOpaque(true);
                bCreate.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        new CreateProfileGUI();
                        dispose();
                    }
                });
                profile.add(bCreate);
            }else{
                addProfileField(profile, s.fullName, "Name");
                addProfileField(profile, s.email, "Email");
                addProfileField(profile, s.phone, "Phone");
                addProfileField(profile, s.college, "College");
                addProfileField(profile, s.course, "Course");
                addProfileField(profile, s.year, "Year");
                addProfileField(profile, s.currentSemester, "Semester");
                addProfileField(profile, s.bio, "Bio");
                addProfileField(profile, s.githubUrl, "Github url");
                addProfileField(profile, s.linkedinUrl, "Linkedin url");

                if(!checkEmpty(s.skills, skills, "No skills added")){
                    for(int i = 0; i < s.skills.length; i++){
                        addProfileField(skills, s.skills[i], "Skill " + (i + 1));
                    }
                }

                if(!checkEmpty(s.education, education, "No education details added")){
                    for(int i = 0; i < s.education.length; i++){
                        EducationModel ed = s.education[i];
                        if(s.education.length > 1){
                            education.add(createHeaderLabel("--- Education " + (i + 1) + " ---"));
                            education.add(new JLabel(""));
                        }
                        addProfileField(education, ed.school, "School");
                        addProfileField(education, ed.board, "Board");
                        addProfileField(education, ed.startYear, "Start year");
                        addProfileField(education, ed.endYear, "End year");
                        addProfileField(education, ed.grade, "Grade");
                    }
                }

                if(!checkEmpty(s.projects, projects, "No projects added")){
                    for(int i = 0; i < s.projects.length; i++){
                        ProjectModel pr = s.projects[i];
                        if(s.projects.length > 1){
                            projects.add(createHeaderLabel("--- Project " + (i + 1) + " ---"));
                            projects.add(new JLabel(""));
                        }
                        addProfileField(projects, pr.title, "Title");
                        addProfileField(projects, pr.description, "Description");
                        addProfileField(projects, pr.githubUrl, "Github url");
                        addProfileField(projects, pr.liveUrl, "Live url");
                    }
                }

                if(!checkEmpty(s.internships, internships, "No internships added")){
                    for(int i = 0; i < s.internships.length; i++){
                        InternshipModel in = s.internships[i];
                        if(s.internships.length > 1){
                            internships.add(createHeaderLabel("--- Internship " + (i + 1) + " ---"));
                            internships.add(new JLabel(""));
                        }
                        addProfileField(internships, in.company, "Company");
                        addProfileField(internships, in.role, "Role");
                        addProfileField(internships, in.description, "Description");
                        addProfileField(internships, in.startDate, "Start date");
                        addProfileField(internships, in.endDate, "End date");
                    }
                }

                if(!checkEmpty(s.certifications, certifications, "No certifications added")){
                    for(int i = 0; i < s.certifications.length; i++){
                        CertificationModel c = s.certifications[i];
                        if(s.certifications.length > 1){
                            certifications.add(createHeaderLabel("--- Certification " + (i + 1) + " ---"));
                            certifications.add(new JLabel(""));
                        }
                        addProfileField(certifications, c.name, "Name");
                        addProfileField(certifications, c.issuingOrganization, "Organization");
                        addProfileField(certifications, c.credentialId, "Credential ID");
                        addProfileField(certifications, c.credentialUrl, "Credential url");
                    }
                }

                if(!checkEmpty(s.achievements, achievements, "No achievements added")){
                    for(int i = 0; i < s.achievements.length; i++){
                        AchievementModel a = s.achievements[i];
                        if(s.achievements.length > 1){
                            achievements.add(createHeaderLabel("--- Achievement " + (i + 1) + " ---"));
                            achievements.add(new JLabel(""));
                        }
                        addProfileField(achievements, a.title, "Title");
                        addProfileField(achievements, a.description, "Description");
                    }
                }

                if(!checkEmpty(s.semesterMarks, semesterMarks, "No semester marks added")){
                    for(int i = 0; i < s.semesterMarks.length; i++){
                        SemesterMarkModel sm = s.semesterMarks[i];
                        addProfileField(semesterMarks, sm.marks, "Semester " + sm.semester);
                    }
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.toString(), "Error Loading Profile", JOptionPane.ERROR_MESSAGE);
        }

        pane.addTab("Profile", wrapScroll(profile));
        pane.addTab("Skills", wrapScroll(skills));
        pane.addTab("Education", wrapScroll(education));
        pane.addTab("Projects", wrapScroll(projects));
        pane.addTab("Internships", wrapScroll(internships));
        pane.addTab("Certifications", wrapScroll(certifications));
        pane.addTab("Achievements", wrapScroll(achievements));
        pane.addTab("Semester Marks", wrapScroll(semesterMarks));

        b1.addActionListener(this);
        b2.addActionListener(this);
        add(header, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createTabPanel(){
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        return panel;
    }

    private JScrollPane wrapScroll(JPanel p){
        JScrollPane sp = new JScrollPane(p);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    private JLabel createHeaderLabel(String text){
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(new Color(37, 99, 235));
        return lbl;
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == b1){
            try{
                ResumeGenerator.generate();
                JOptionPane.showMessageDialog(this, "Resume generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.toString(), "Resume Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            new StudentGUI();
            dispose();
        }
    }

    private boolean checkEmpty(Object arr[], Container c, String msg){
        if(arr == null || arr.length == 0){
            JLabel empty = new JLabel(msg);
            empty.setFont(new Font("SansSerif", Font.ITALIC, 13));
            empty.setForeground(new Color(100, 116, 139));
            c.add(empty);
            c.add(new JLabel(""));
            return true;
        }
        return false;
    }

    private void addProfileField(Container c, String value, String field){
        if(value != null && value.trim().length() != 0){
            JLabel lblField = new JLabel(field);
            lblField.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblField.setForeground(new Color(30, 41, 59));

            JLabel lblVal = new JLabel(value);
            lblVal.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblVal.setForeground(new Color(51, 65, 85));

            c.add(lblField);
            c.add(lblVal);
        }
    }

    private void addProfileField(Container c, int value, String field){
        if(value != -1){
            JLabel lblField = new JLabel(field);
            lblField.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblField.setForeground(new Color(30, 41, 59));

            JLabel lblVal = new JLabel("" + value);
            lblVal.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblVal.setForeground(new Color(51, 65, 85));

            c.add(lblField);
            c.add(lblVal);
        }
    }

    private void addProfileField(Container c, double value, String field){
        if(value >= 0){
            JLabel lblField = new JLabel(field);
            lblField.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblField.setForeground(new Color(30, 41, 59));

            JLabel lblVal = new JLabel("" + value);
            lblVal.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblVal.setForeground(new Color(51, 65, 85));

            c.add(lblField);
            c.add(lblVal);
        }
    }
}

class EditDetailsGUI extends JFrame implements ActionListener{
    JLabel l1;
    JButton bBack;
    JTabbedPane pane;
    JPanel header,footer;
    StudentProfileModel s;

    // Profile
    JPanel profile;
    JTextField tf1,tf2,tf3,tf4,tf5,tf6,tf7,tf8,tf9,tf10;
    JButton bSaveProfile,bCreateProfile;

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

    // Semester Marks
    JPanel semesterMarks;
    JTextField tfCurSem,tfSemNum,tfSemMarks;
    JComboBox<String> cbMarks;
    JButton bSetCurSem,bSaveMarks,bRemoveMarks;

    public EditDetailsGUI(){
        this(0);
    }

    public EditDetailsGUI(int tabIndex){
        setTitle("Student Profile - Edit Details");
        setSize(740, 650);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(248, 250, 252));

        // Header with clear title at the top
        header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        l1 = new JLabel("Edit Profile Details", SwingConstants.CENTER);
        l1.setFont(new Font("SansSerif", Font.BOLD, 20));
        l1.setForeground(Color.WHITE);
        header.add(l1, BorderLayout.CENTER);

        // Footer
        footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        footer.setBackground(new Color(248, 250, 252));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        bBack = new JButton("Back to Home");
        styleBtn(bBack, new Color(100, 116, 139));
        bBack.addActionListener(this);
        footer.add(bBack);

        pane = new JTabbedPane();
        pane.setFont(new Font("SansSerif", Font.PLAIN, 13));

        profile = createEditPanel();
        skills = createEditPanel();
        education = createEditPanel();
        projects = createEditPanel();
        internships = createEditPanel();
        certifications = createEditPanel();
        achievements = createEditPanel();
        semesterMarks = createEditPanel();

        try{
            s = StudentProfile.getProfile();
            if(s == null){
                JOptionPane.showMessageDialog(this, "Student profile does not exist", "Profile Not Found", JOptionPane.ERROR_MESSAGE);
                bCreateProfile = new JButton("Create Profile");
                styleBtn(bCreateProfile, new Color(37, 99, 235));
                bCreateProfile.addActionListener(this);
                JLabel noProf = new JLabel("No profile found.");
                noProf.setFont(new Font("SansSerif", Font.ITALIC, 13));
                profile.add(noProf);
                profile.add(bCreateProfile);
            }else{
                // Profile
                tf1 = addEditField(profile, s.fullName, "Name");
                tf2 = addEditField(profile, s.email, "Email");
                tf3 = addEditField(profile, s.phone, "Phone");
                tf4 = addEditField(profile, s.college, "College");
                tf5 = addEditField(profile, s.course, "Course");
                tf6 = addEditField(profile, s.year, "Year");
                tf7 = addEditField(profile, s.currentSemester <= 0 ? 1 : s.currentSemester, "Semester");
                tf8 = addEditField(profile, s.bio, "Bio");
                tf9 = addEditField(profile, s.githubUrl, "Github url");
                tf10 = addEditField(profile, s.linkedinUrl, "Linkedin url");

                bSaveProfile = new JButton("Save Profile");
                styleBtn(bSaveProfile, new Color(37, 99, 235));
                bSaveProfile.addActionListener(this);
                profile.add(bSaveProfile);
                profile.add(new JLabel(""));

                // Skills
                addLabel(skills, "Select Skill to Remove");
                String skillItems[];
                if(s.skills != null && s.skills.length > 0){
                    skillItems = s.skills;
                }else{
                    skillItems = new String[]{"No skills added"};
                }
                cbSkills = new JComboBox<>(skillItems);
                cbSkills.setFont(new Font("SansSerif", Font.PLAIN, 13));
                skills.add(cbSkills);

                bRemoveSkill = new JButton("Remove Skill");
                styleBtn(bRemoveSkill, new Color(220, 38, 38));
                bRemoveSkill.addActionListener(this);
                if(s.skills == null || s.skills.length == 0){
                    bRemoveSkill.setEnabled(false);
                }
                skills.add(bRemoveSkill);
                skills.add(new JLabel(""));

                addLabel(skills, "New Skill Name");
                tfNewSkill = new JTextField();
                tfNewSkill.setFont(new Font("SansSerif", Font.PLAIN, 13));
                skills.add(tfNewSkill);

                bAddSkill = new JButton("Add Skill");
                styleBtn(bAddSkill, new Color(37, 99, 235));
                bAddSkill.addActionListener(this);
                skills.add(bAddSkill);
                skills.add(new JLabel(""));

                // Education
                addLabel(education, "Select Education");
                String edItems[] = new String[(s.education != null ? s.education.length : 0) + 1];
                edItems[0] = "--- Add New Education ---";
                if(s.education != null){
                    for(int i = 0; i < s.education.length; i++){
                        edItems[i + 1] = "Ed " + (i + 1) + ": " + s.education[i].school;
                    }
                }
                cbEducation = new JComboBox<>(edItems);
                cbEducation.setFont(new Font("SansSerif", Font.PLAIN, 13));
                education.add(cbEducation);

                tfEdSchool = addEditField(education, "", "School");
                tfEdBoard = addEditField(education, "", "Board");
                tfEdStartYear = addEditField(education, "", "Start Year");
                tfEdEndYear = addEditField(education, "", "End Year");
                tfEdGrade = addEditField(education, "", "Grade");

                bSaveEd = new JButton("Add Education");
                styleBtn(bSaveEd, new Color(37, 99, 235));
                bSaveEd.addActionListener(this);
                education.add(bSaveEd);

                bRemoveEd = new JButton("Remove Education");
                styleBtn(bRemoveEd, new Color(220, 38, 38));
                bRemoveEd.setEnabled(false);
                bRemoveEd.addActionListener(this);
                education.add(bRemoveEd);

                cbEducation.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange() == ItemEvent.SELECTED){
                            int idx = cbEducation.getSelectedIndex();
                            if(idx <= 0){
                                tfEdSchool.setText("");
                                tfEdBoard.setText("");
                                tfEdStartYear.setText("");
                                tfEdEndYear.setText("");
                                tfEdGrade.setText("");
                                bSaveEd.setText("Add Education");
                                bRemoveEd.setEnabled(false);
                            }else{
                                EducationModel ed = s.education[idx - 1];
                                tfEdSchool.setText(ed.school == null ? "" : ed.school);
                                tfEdBoard.setText(ed.board == null ? "" : ed.board);
                                tfEdStartYear.setText(ed.startYear == -1 || ed.startYear == 0 ? "" : ("" + ed.startYear));
                                tfEdEndYear.setText(ed.endYear == -1 || ed.endYear == 0 ? "" : ("" + ed.endYear));
                                tfEdGrade.setText(ed.grade == null ? "" : ed.grade);
                                bSaveEd.setText("Update Education");
                                bRemoveEd.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.education != null && s.education.length > 0){
                    cbEducation.setSelectedIndex(1);
                }

                // Projects
                addLabel(projects, "Select Project");
                String projItems[] = new String[(s.projects != null ? s.projects.length : 0) + 1];
                projItems[0] = "--- Add New Project ---";
                if(s.projects != null){
                    for(int i = 0; i < s.projects.length; i++){
                        projItems[i + 1] = "Proj " + (i + 1) + ": " + s.projects[i].title;
                    }
                }
                cbProjects = new JComboBox<>(projItems);
                cbProjects.setFont(new Font("SansSerif", Font.PLAIN, 13));
                projects.add(cbProjects);

                tfProjTitle = addEditField(projects, "", "Title");
                tfProjDesc = addEditField(projects, "", "Description");
                tfProjGit = addEditField(projects, "", "Github url");
                tfProjLive = addEditField(projects, "", "Live url");

                bSaveProj = new JButton("Add Project");
                styleBtn(bSaveProj, new Color(37, 99, 235));
                bSaveProj.addActionListener(this);
                projects.add(bSaveProj);

                bRemoveProj = new JButton("Remove Project");
                styleBtn(bRemoveProj, new Color(220, 38, 38));
                bRemoveProj.setEnabled(false);
                bRemoveProj.addActionListener(this);
                projects.add(bRemoveProj);

                cbProjects.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange() == ItemEvent.SELECTED){
                            int idx = cbProjects.getSelectedIndex();
                            if(idx <= 0){
                                tfProjTitle.setText("");
                                tfProjDesc.setText("");
                                tfProjGit.setText("");
                                tfProjLive.setText("");
                                bSaveProj.setText("Add Project");
                                bRemoveProj.setEnabled(false);
                            }else{
                                ProjectModel pr = s.projects[idx - 1];
                                tfProjTitle.setText(pr.title == null ? "" : pr.title);
                                tfProjDesc.setText(pr.description == null ? "" : pr.description);
                                tfProjGit.setText(pr.githubUrl == null ? "" : pr.githubUrl);
                                tfProjLive.setText(pr.liveUrl == null ? "" : pr.liveUrl);
                                bSaveProj.setText("Update Project");
                                bRemoveProj.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.projects != null && s.projects.length > 0){
                    cbProjects.setSelectedIndex(1);
                }

                // Internships
                addLabel(internships, "Select Internship");
                String inItems[] = new String[(s.internships != null ? s.internships.length : 0) + 1];
                inItems[0] = "--- Add New Internship ---";
                if(s.internships != null){
                    for(int i = 0; i < s.internships.length; i++){
                        inItems[i + 1] = "In " + (i + 1) + ": " + s.internships[i].company;
                    }
                }
                cbInternships = new JComboBox<>(inItems);
                cbInternships.setFont(new Font("SansSerif", Font.PLAIN, 13));
                internships.add(cbInternships);

                tfInCompany = addEditField(internships, "", "Company");
                tfInRole = addEditField(internships, "", "Role");
                tfInDesc = addEditField(internships, "", "Description");
                tfInStartDate = addEditField(internships, "", "Start Date");
                tfInEndDate = addEditField(internships, "", "End Date");

                bSaveIn = new JButton("Add Internship");
                styleBtn(bSaveIn, new Color(37, 99, 235));
                bSaveIn.addActionListener(this);
                internships.add(bSaveIn);

                bRemoveIn = new JButton("Remove Internship");
                styleBtn(bRemoveIn, new Color(220, 38, 38));
                bRemoveIn.setEnabled(false);
                bRemoveIn.addActionListener(this);
                internships.add(bRemoveIn);

                cbInternships.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange() == ItemEvent.SELECTED){
                            int idx = cbInternships.getSelectedIndex();
                            if(idx <= 0){
                                tfInCompany.setText("");
                                tfInRole.setText("");
                                tfInDesc.setText("");
                                tfInStartDate.setText("");
                                tfInEndDate.setText("");
                                bSaveIn.setText("Add Internship");
                                bRemoveIn.setEnabled(false);
                            }else{
                                InternshipModel in = s.internships[idx - 1];
                                tfInCompany.setText(in.company == null ? "" : in.company);
                                tfInRole.setText(in.role == null ? "" : in.role);
                                tfInDesc.setText(in.description == null ? "" : in.description);
                                tfInStartDate.setText(in.startDate == null ? "" : in.startDate);
                                tfInEndDate.setText(in.endDate == null ? "" : in.endDate);
                                bSaveIn.setText("Update Internship");
                                bRemoveIn.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.internships != null && s.internships.length > 0){
                    cbInternships.setSelectedIndex(1);
                }

                // Certifications
                addLabel(certifications, "Select Certification");
                String certItems[] = new String[(s.certifications != null ? s.certifications.length : 0) + 1];
                certItems[0] = "--- Add New Certification ---";
                if(s.certifications != null){
                    for(int i = 0; i < s.certifications.length; i++){
                        certItems[i + 1] = "Cert " + (i + 1) + ": " + s.certifications[i].name;
                    }
                }
                cbCertifications = new JComboBox<>(certItems);
                cbCertifications.setFont(new Font("SansSerif", Font.PLAIN, 13));
                certifications.add(cbCertifications);

                tfCertName = addEditField(certifications, "", "Name");
                tfCertOrg = addEditField(certifications, "", "Organization");
                tfCertId = addEditField(certifications, "", "Credential ID");
                tfCertUrl = addEditField(certifications, "", "Credential URL");

                bSaveCert = new JButton("Add Certification");
                styleBtn(bSaveCert, new Color(37, 99, 235));
                bSaveCert.addActionListener(this);
                certifications.add(bSaveCert);

                bRemoveCert = new JButton("Remove Certification");
                styleBtn(bRemoveCert, new Color(220, 38, 38));
                bRemoveCert.setEnabled(false);
                bRemoveCert.addActionListener(this);
                certifications.add(bRemoveCert);

                cbCertifications.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange() == ItemEvent.SELECTED){
                            int idx = cbCertifications.getSelectedIndex();
                            if(idx <= 0){
                                tfCertName.setText("");
                                tfCertOrg.setText("");
                                tfCertId.setText("");
                                tfCertUrl.setText("");
                                bSaveCert.setText("Add Certification");
                                bRemoveCert.setEnabled(false);
                            }else{
                                CertificationModel c = s.certifications[idx - 1];
                                tfCertName.setText(c.name == null ? "" : c.name);
                                tfCertOrg.setText(c.issuingOrganization == null ? "" : c.issuingOrganization);
                                tfCertId.setText(c.credentialId == null ? "" : c.credentialId);
                                tfCertUrl.setText(c.credentialUrl == null ? "" : c.credentialUrl);
                                bSaveCert.setText("Update Certification");
                                bRemoveCert.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.certifications != null && s.certifications.length > 0){
                    cbCertifications.setSelectedIndex(1);
                }

                // Achievements
                addLabel(achievements, "Select Achievement");
                String achItems[] = new String[(s.achievements != null ? s.achievements.length : 0) + 1];
                achItems[0] = "--- Add New Achievement ---";
                if(s.achievements != null){
                    for(int i = 0; i < s.achievements.length; i++){
                        achItems[i + 1] = "Ach " + (i + 1) + ": " + s.achievements[i].title;
                    }
                }
                cbAchievements = new JComboBox<>(achItems);
                cbAchievements.setFont(new Font("SansSerif", Font.PLAIN, 13));
                achievements.add(cbAchievements);

                tfAchTitle = addEditField(achievements, "", "Title");
                tfAchDesc = addEditField(achievements, "", "Description");

                bSaveAch = new JButton("Add Achievement");
                styleBtn(bSaveAch, new Color(37, 99, 235));
                bSaveAch.addActionListener(this);
                achievements.add(bSaveAch);

                bRemoveAch = new JButton("Remove Achievement");
                styleBtn(bRemoveAch, new Color(220, 38, 38));
                bRemoveAch.setEnabled(false);
                bRemoveAch.addActionListener(this);
                achievements.add(bRemoveAch);

                cbAchievements.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange() == ItemEvent.SELECTED){
                            int idx = cbAchievements.getSelectedIndex();
                            if(idx <= 0){
                                tfAchTitle.setText("");
                                tfAchDesc.setText("");
                                bSaveAch.setText("Add Achievement");
                                bRemoveAch.setEnabled(false);
                            }else{
                                AchievementModel a = s.achievements[idx - 1];
                                tfAchTitle.setText(a.title == null ? "" : a.title);
                                tfAchDesc.setText(a.description == null ? "" : a.description);
                                bSaveAch.setText("Update Achievement");
                                bRemoveAch.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.achievements != null && s.achievements.length > 0){
                    cbAchievements.setSelectedIndex(1);
                }

                // Semester Marks
                addLabel(semesterMarks, "Current Semester");
                tfCurSem = new JTextField("" + (s.currentSemester <= 0 ? 1 : s.currentSemester));
                tfCurSem.setFont(new Font("SansSerif", Font.PLAIN, 13));
                semesterMarks.add(tfCurSem);

                bSetCurSem = new JButton("Update Current Semester");
                styleBtn(bSetCurSem, new Color(37, 99, 235));
                bSetCurSem.addActionListener(this);
                semesterMarks.add(bSetCurSem);
                semesterMarks.add(new JLabel(""));

                addLabel(semesterMarks, "Select Semester Mark");
                String markItems[] = new String[(s.semesterMarks != null ? s.semesterMarks.length : 0) + 1];
                markItems[0] = "--- Add / Set Semester Marks ---";
                if(s.semesterMarks != null){
                    for(int i = 0; i < s.semesterMarks.length; i++){
                        markItems[i + 1] = "Semester " + s.semesterMarks[i].semester + ": " + s.semesterMarks[i].marks;
                    }
                }
                cbMarks = new JComboBox<>(markItems);
                cbMarks.setFont(new Font("SansSerif", Font.PLAIN, 13));
                semesterMarks.add(cbMarks);

                tfSemNum = addEditField(semesterMarks, "", "Semester Number");
                tfSemMarks = addEditField(semesterMarks, "", "Marks");

                bSaveMarks = new JButton("Add Marks");
                styleBtn(bSaveMarks, new Color(37, 99, 235));
                bSaveMarks.addActionListener(this);
                semesterMarks.add(bSaveMarks);

                bRemoveMarks = new JButton("Remove Marks");
                styleBtn(bRemoveMarks, new Color(220, 38, 38));
                bRemoveMarks.setEnabled(false);
                bRemoveMarks.addActionListener(this);
                semesterMarks.add(bRemoveMarks);

                cbMarks.addItemListener(new ItemListener(){
                    public void itemStateChanged(ItemEvent ie){
                        if(ie.getStateChange() == ItemEvent.SELECTED){
                            int idx = cbMarks.getSelectedIndex();
                            if(idx <= 0){
                                tfSemNum.setText("");
                                tfSemNum.setEditable(true);
                                tfSemMarks.setText("");
                                bSaveMarks.setText("Add Marks");
                                bRemoveMarks.setEnabled(false);
                            }else{
                                SemesterMarkModel sm = s.semesterMarks[idx - 1];
                                tfSemNum.setText("" + sm.semester);
                                tfSemNum.setEditable(false);
                                tfSemMarks.setText("" + sm.marks);
                                bSaveMarks.setText("Update Marks");
                                bRemoveMarks.setEnabled(true);
                            }
                        }
                    }
                });
                if(s.semesterMarks != null && s.semesterMarks.length > 0){
                    cbMarks.setSelectedIndex(1);
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.toString(), "Error Loading Profile", JOptionPane.ERROR_MESSAGE);
        }

        pane.addTab("Profile", wrapScroll(profile));
        pane.addTab("Skills", wrapScroll(skills));
        pane.addTab("Education", wrapScroll(education));
        pane.addTab("Projects", wrapScroll(projects));
        pane.addTab("Internships", wrapScroll(internships));
        pane.addTab("Certifications", wrapScroll(certifications));
        pane.addTab("Achievements", wrapScroll(achievements));
        pane.addTab("Semester Marks", wrapScroll(semesterMarks));

        if(tabIndex >= 0 && tabIndex < pane.getTabCount()){
            pane.setSelectedIndex(tabIndex);
        }

        add(header, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createEditPanel(){
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 26, 18, 26));
        return panel;
    }

    private JScrollPane wrapScroll(JPanel p){
        JScrollPane sp = new JScrollPane(p);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    private JLabel addLabel(Container c, String text){
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(new Color(30, 41, 59));
        c.add(lbl);
        return lbl;
    }

    private void styleBtn(JButton btn, Color bg){
        if(btn == null) return;
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bg.darker(), 1),
            BorderFactory.createEmptyBorder(7, 14, 7, 14)
        ));
    }

    public void actionPerformed(ActionEvent ae){
        Object src = ae.getSource();
        if(src == bBack){
            new StudentGUI();
            dispose();
            return;
        }
        if(src == bCreateProfile){
            new CreateProfileGUI();
            dispose();
            return;
        }
        try{
            if(src == bSaveProfile){
                String fullName = tf1.getText();
                String email = tf2.getText();
                String phone = tf3.getText();
                String college = tf4.getText();
                String course = tf5.getText();
                int year = -1;
                String yStr = tf6.getText().trim();
                if(yStr.length() != 0){
                    try{
                        year = Integer.parseInt(yStr);
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(this, "Year must be a number", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                int sem = 1;
                String semStr = tf7.getText().trim();
                if(semStr.length() != 0){
                    try{
                        sem = Integer.parseInt(semStr);
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(this, "Semester must be a number", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String bio = tf8.getText();
                String githubUrl = tf9.getText();
                String linkedinUrl = tf10.getText();
                StudentProfile.updateProfile(fullName, email, phone, college, course, year, sem, bio, githubUrl, linkedinUrl);
                JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }else if(src == bAddSkill){
                String sk = tfNewSkill.getText().trim();
                if(sk.length() == 0){
                    throw new MissingFieldException("skill");
                }
                Skills.addSkill(sk);
                new EditDetailsGUI(1);
                dispose();
            }else if(src == bRemoveSkill){
                if(cbSkills.getSelectedItem() != null && !cbSkills.getSelectedItem().equals("No skills added")){
                    Skills.removeSkill((String)cbSkills.getSelectedItem());
                    new EditDetailsGUI(1);
                    dispose();
                }
            }else if(src == bSaveEd){
                int idx = cbEducation.getSelectedIndex();
                String school = tfEdSchool.getText().trim();
                String board = tfEdBoard.getText().trim();
                int startYear = -1;
                String sy = tfEdStartYear.getText().trim();
                if(sy.length() != 0){
                    try{
                        startYear = Integer.parseInt(sy);
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(this, "Start Year must be a number", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                int endYear = -1;
                String ey = tfEdEndYear.getText().trim();
                if(ey.length() != 0){
                    try{
                        endYear = Integer.parseInt(ey);
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(this, "End Year must be a number", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String grade = tfEdGrade.getText().trim();
                if(idx <= 0){
                    Education.addEducation(school, board, startYear, endYear, grade);
                }else{
                    Education.updateEducation(s.education[idx - 1].id, school, board, startYear, endYear, grade);
                }
                new EditDetailsGUI(2);
                dispose();
            }else if(src == bRemoveEd){
                int idx = cbEducation.getSelectedIndex();
                if(idx > 0){
                    Education.removeEducation(s.education[idx - 1].id);
                    new EditDetailsGUI(2);
                    dispose();
                }
            }else if(src == bSaveProj){
                int idx = cbProjects.getSelectedIndex();
                String title = tfProjTitle.getText().trim();
                String desc = tfProjDesc.getText().trim();
                String git = tfProjGit.getText().trim();
                String live = tfProjLive.getText().trim();
                if(idx <= 0){
                    Projects.addProject(title, desc, git, live);
                }else{
                    Projects.updateProject(s.projects[idx - 1].id, title, desc, git, live);
                }
                new EditDetailsGUI(3);
                dispose();
            }else if(src == bRemoveProj){
                int idx = cbProjects.getSelectedIndex();
                if(idx > 0){
                    Projects.removeProject(s.projects[idx - 1].id);
                    new EditDetailsGUI(3);
                    dispose();
                }
            }else if(src == bSaveIn){
                int idx = cbInternships.getSelectedIndex();
                String company = tfInCompany.getText().trim();
                String role = tfInRole.getText().trim();
                String desc = tfInDesc.getText().trim();
                String start = tfInStartDate.getText().trim();
                String end = tfInEndDate.getText().trim();
                if(idx <= 0){
                    Internships.addInternship(company, role, desc, start, end);
                }else{
                    Internships.updateInternship(s.internships[idx - 1].id, company, role, desc, start, end);
                }
                new EditDetailsGUI(4);
                dispose();
            }else if(src == bRemoveIn){
                int idx = cbInternships.getSelectedIndex();
                if(idx > 0){
                    Internships.removeInternship(s.internships[idx - 1].id);
                    new EditDetailsGUI(4);
                    dispose();
                }
            }else if(src == bSaveCert){
                int idx = cbCertifications.getSelectedIndex();
                String name = tfCertName.getText().trim();
                String org = tfCertOrg.getText().trim();
                String credId = tfCertId.getText().trim();
                String credUrl = tfCertUrl.getText().trim();
                if(idx <= 0){
                    Certifications.addCertification(name, org, credId, credUrl);
                }else{
                    Certifications.updateCertification(s.certifications[idx - 1].id, name, org, credId, credUrl);
                }
                new EditDetailsGUI(5);
                dispose();
            }else if(src == bRemoveCert){
                int idx = cbCertifications.getSelectedIndex();
                if(idx > 0){
                    Certifications.removeCertification(s.certifications[idx - 1].id);
                    new EditDetailsGUI(5);
                    dispose();
                }
            }else if(src == bSaveAch){
                int idx = cbAchievements.getSelectedIndex();
                String title = tfAchTitle.getText().trim();
                String desc = tfAchDesc.getText().trim();
                if(idx <= 0){
                    Achievements.addAchievement(title, desc);
                }else{
                    Achievements.updateAchievement(s.achievements[idx - 1].id, title, desc);
                }
                new EditDetailsGUI(6);
                dispose();
            }else if(src == bRemoveAch){
                int idx = cbAchievements.getSelectedIndex();
                if(idx > 0){
                    Achievements.removeAchievement(s.achievements[idx - 1].id);
                    new EditDetailsGUI(6);
                    dispose();
                }
            }else if(src == bSetCurSem){
                int sem = Integer.parseInt(tfCurSem.getText().trim());
                SemesterMarks.setCurrentSemester(sem);
                JOptionPane.showMessageDialog(this, "Current semester updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new EditDetailsGUI(7);
                dispose();
            }else if(src == bSaveMarks){
                int idx = cbMarks.getSelectedIndex();
                double marks = Double.parseDouble(tfSemMarks.getText().trim());
                if(idx <= 0){
                    int sem = Integer.parseInt(tfSemNum.getText().trim());
                    SemesterMarks.setMarks(sem, marks);
                }else{
                    int sem = s.semesterMarks[idx - 1].semester;
                    SemesterMarks.updateMarks(sem, marks);
                }
                JOptionPane.showMessageDialog(this, "Semester marks saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new EditDetailsGUI(7);
                dispose();
            }else if(src == bRemoveMarks){
                int idx = cbMarks.getSelectedIndex();
                if(idx > 0){
                    SemesterMarks.removeMarks(s.semesterMarks[idx - 1].semester);
                    JOptionPane.showMessageDialog(this, "Semester marks removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new EditDetailsGUI(7);
                    dispose();
                }
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTextField addEditField(Container c, String value, String field){
        JLabel lbl = new JLabel(field);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(new Color(30, 41, 59));
        c.add(lbl);

        JTextField tf = new JTextField(value == null ? "" : value);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c.add(tf);
        return tf;
    }

    private JTextField addEditField(Container c, int value, String field){
        JLabel lbl = new JLabel(field);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(new Color(30, 41, 59));
        c.add(lbl);

        JTextField tf = new JTextField(value == -1 || value == 0 ? "" : ("" + value));
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c.add(tf);
        return tf;
    }
}
