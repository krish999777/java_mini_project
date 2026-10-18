import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import session.Session;
import data.*;

public class MentorGUI extends JFrame implements ActionListener {
    JLabel l1;
    JButton b1;
    JPanel students[],header,center,top,searchPanel;
    JButton viewButtons[];
    StudentProfileModel p[];
    JTextField tfName,tfCollege,tfCourse,tfYear,tfSkill;
    JButton bSearch,bReset;

    public MentorGUI(){
        setTitle("Mentor Page");
        setSize(920, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(248, 250, 252));

        // Header with clear title at the top
        header = new JPanel(new BorderLayout(15, 0));
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        l1 = new JLabel("Mentor Portal - Students Directory");
        l1.setFont(new Font("SansSerif", Font.BOLD, 20));
        l1.setForeground(Color.WHITE);
        header.add(l1, BorderLayout.WEST);

        b1 = new JButton("Logout");
        b1.setFont(new Font("SansSerif", Font.BOLD, 13));
        b1.setBackground(new Color(220, 38, 38));
        b1.setForeground(Color.WHITE);
        b1.setFocusPainted(false);
        b1.setOpaque(true);
        b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(185, 28, 28), 1),
            BorderFactory.createEmptyBorder(6, 16, 6, 16)
        ));
        b1.addActionListener(this);
        header.add(b1, BorderLayout.EAST);

        // Search Panel with clean spacing
        searchPanel = new JPanel(new GridLayout(0, 4, 10, 8));
        searchPanel.setBackground(new Color(241, 245, 249));
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(203, 213, 225)),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        tfName = new JTextField();
        tfCollege = new JTextField();
        tfCourse = new JTextField();
        tfYear = new JTextField();
        tfSkill = new JTextField();

        bSearch = new JButton("Search");
        bSearch.setFont(new Font("SansSerif", Font.BOLD, 12));
        bSearch.setBackground(new Color(37, 99, 235));
        bSearch.setForeground(Color.WHITE);
        bSearch.setFocusPainted(false);
        bSearch.setOpaque(true);
        bSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bSearch.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        bReset = new JButton("Reset");
        bReset.setFont(new Font("SansSerif", Font.BOLD, 12));
        bReset.setBackground(new Color(100, 116, 139));
        bReset.setForeground(Color.WHITE);
        bReset.setFocusPainted(false);
        bReset.setOpaque(true);
        bReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bReset.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        bSearch.addActionListener(this);
        bReset.addActionListener(this);

        searchPanel.add(createSearchLabel("Name"));
        searchPanel.add(tfName);
        searchPanel.add(createSearchLabel("College"));
        searchPanel.add(tfCollege);
        searchPanel.add(createSearchLabel("Course"));
        searchPanel.add(tfCourse);
        searchPanel.add(createSearchLabel("Year"));
        searchPanel.add(tfYear);
        searchPanel.add(createSearchLabel("Skill"));
        searchPanel.add(tfSkill);
        searchPanel.add(bSearch);
        searchPanel.add(bReset);

        top = new JPanel(new BorderLayout());
        top.add(header, BorderLayout.NORTH);
        top.add(searchPanel, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        center = new JPanel(new GridLayout(0, 3, 14, 14));
        center.setBackground(new Color(248, 250, 252));

        JScrollPane scrollPane = new JScrollPane(center);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        try{
            p = Mentor.getStudents();
            loadStudents(p);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.toString(), "Error Loading Students", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }

    private JLabel createSearchLabel(String text){
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(new Color(30, 41, 59));
        return lbl;
    }

    private void loadStudents(StudentProfileModel p[]){
        center.removeAll();
        this.p = p;
        if(p == null || p.length == 0){
            center.setLayout(new BorderLayout());
            JLabel noStudents = new JLabel("No students found matching your criteria.", SwingConstants.CENTER);
            noStudents.setFont(new Font("SansSerif", Font.PLAIN, 15));
            noStudents.setForeground(new Color(100, 116, 139));
            center.add(noStudents, BorderLayout.CENTER);
            students = new JPanel[0];
            viewButtons = new JButton[0];
        }else{
            center.setLayout(new GridLayout(0, 3, 14, 14));
            students = new JPanel[p.length];
            viewButtons = new JButton[p.length];
            for(int i = 0; i < p.length; i++){
                students[i] = new JPanel(new GridLayout(0, 2, 8, 8));
                students[i].setBackground(Color.WHITE);
                students[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                    BorderFactory.createEmptyBorder(14, 14, 14, 14)
                ));

                addProfileField(students[i], p[i].id, "ID");
                addProfileField(students[i], p[i].fullName, "Name");
                addProfileField(students[i], p[i].email, "Email");
                addProfileField(students[i], p[i].phone, "Phone");
                addProfileField(students[i], p[i].college, "College");
                addProfileField(students[i], p[i].course, "Course");
                addProfileField(students[i], p[i].year, "Year");
                addProfileField(students[i], p[i].currentSemester, "Semester");
                addProfileField(students[i], p[i].bio, "Bio");
                addProfileField(students[i], p[i].githubUrl, "Github url");
                addProfileField(students[i], p[i].linkedinUrl, "Linkedin url");

                viewButtons[i] = new JButton("View Student");
                viewButtons[i].setFont(new Font("SansSerif", Font.BOLD, 12));
                viewButtons[i].setBackground(new Color(37, 99, 235));
                viewButtons[i].setForeground(Color.WHITE);
                viewButtons[i].setFocusPainted(false);
                viewButtons[i].setOpaque(true);
                viewButtons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                viewButtons[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(29, 78, 216), 1),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
                viewButtons[i].addActionListener(this);
                students[i].add(viewButtons[i]);
                students[i].add(new JLabel(""));
                center.add(students[i]);
            }
        }
        center.revalidate();
        center.repaint();
    }

    public void actionPerformed(ActionEvent ae){
        JButton source = (JButton)ae.getSource();
        if(source == b1){
            Session.logout();
            new LoginGUI();
            dispose();
        }else if(source == bSearch){
            try{
                String name = tfName.getText().trim();
                String college = tfCollege.getText().trim();
                String course = tfCourse.getText().trim();
                String yearStr = tfYear.getText().trim();
                int year = -1;
                if(yearStr.length() != 0){
                    try{
                        year = Integer.parseInt(yearStr);
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(this, "Year must be a number", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String skill = tfSkill.getText().trim();
                p = Mentor.getStudents(name, college, course, year, skill);
                loadStudents(p);
                if(p == null || p.length == 0){
                    JOptionPane.showMessageDialog(this, "No students found matching your criteria.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.toString(), "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        }else if(source == bReset){
            tfName.setText("");
            tfCollege.setText("");
            tfCourse.setText("");
            tfYear.setText("");
            tfSkill.setText("");
            try{
                p = Mentor.getStudents();
                loadStudents(p);
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            if(viewButtons != null){
                for(int i = 0; i < viewButtons.length; i++){
                    if(source == viewButtons[i]){
                        new StudentDetailGUI(p[i].id);
                        dispose();
                        return;
                    }
                }
            }
        }
    }

    private void addProfileField(Container c, String value, String field){
        if(value != null && value.trim().length() != 0){
            JLabel lblField = new JLabel(field);
            lblField.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblField.setForeground(new Color(30, 41, 59));

            JLabel lblVal = new JLabel(value);
            lblVal.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblVal.setForeground(new Color(51, 65, 85));

            c.add(lblField);
            c.add(lblVal);
        }
    }

    private void addProfileField(Container c, int value, String field){
        if(value != -1 && value != 0){
            JLabel lblField = new JLabel(field);
            lblField.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblField.setForeground(new Color(30, 41, 59));

            JLabel lblVal = new JLabel("" + value);
            lblVal.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblVal.setForeground(new Color(51, 65, 85));

            c.add(lblField);
            c.add(lblVal);
        }
    }
}

class StudentDetailGUI extends JFrame implements ActionListener{
    int studentId;
    JLabel l1;
    JButton b1,b2;
    JTabbedPane pane;
    JPanel header,footer,profile,skills,education,projects,internships,certifications,achievements,semesterMarks;

    StudentDetailGUI(){}

    StudentDetailGUI(int id){
        this.studentId = id;
        setTitle("Student Profile Details");
        setSize(720, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(248, 250, 252));

        // Header with clear title at the top
        header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        l1 = new JLabel("Student Profile Details", SwingConstants.CENTER);
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
            StudentProfileModel p = Mentor.getStudent(id);
            if(p == null){
                JOptionPane.showMessageDialog(this, "Student profile does not exist", "Profile Not Found", JOptionPane.ERROR_MESSAGE);
            }else{
                addProfileField(profile, p.fullName, "Name");
                addProfileField(profile, p.email, "Email");
                addProfileField(profile, p.phone, "Phone");
                addProfileField(profile, p.college, "College");
                addProfileField(profile, p.course, "Course");
                addProfileField(profile, p.year, "Year");
                addProfileField(profile, p.currentSemester, "Semester");
                addProfileField(profile, p.bio, "Bio");
                addProfileField(profile, p.githubUrl, "Github url");
                addProfileField(profile, p.linkedinUrl, "Linkedin url");

                if(!checkEmpty(p.skills, skills, "No skills added")){
                    for(int i = 0; i < p.skills.length; i++){
                        addProfileField(skills, p.skills[i], "Skill " + (i + 1));
                    }
                }

                if(!checkEmpty(p.education, education, "No education details added")){
                    for(int i = 0; i < p.education.length; i++){
                        EducationModel ed = p.education[i];
                        if(p.education.length > 1){
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

                if(!checkEmpty(p.projects, projects, "No projects added")){
                    for(int i = 0; i < p.projects.length; i++){
                        ProjectModel pr = p.projects[i];
                        if(p.projects.length > 1){
                            projects.add(createHeaderLabel("--- Project " + (i + 1) + " ---"));
                            projects.add(new JLabel(""));
                        }
                        addProfileField(projects, pr.title, "Title");
                        addProfileField(projects, pr.description, "Description");
                        addProfileField(projects, pr.githubUrl, "Github url");
                        addProfileField(projects, pr.liveUrl, "Live url");
                    }
                }

                if(!checkEmpty(p.internships, internships, "No internships added")){
                    for(int i = 0; i < p.internships.length; i++){
                        InternshipModel in = p.internships[i];
                        if(p.internships.length > 1){
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

                if(!checkEmpty(p.certifications, certifications, "No certifications added")){
                    for(int i = 0; i < p.certifications.length; i++){
                        CertificationModel c = p.certifications[i];
                        if(p.certifications.length > 1){
                            certifications.add(createHeaderLabel("--- Certification " + (i + 1) + " ---"));
                            certifications.add(new JLabel(""));
                        }
                        addProfileField(certifications, c.name, "Name");
                        addProfileField(certifications, c.issuingOrganization, "Organization");
                        addProfileField(certifications, c.credentialId, "Credential ID");
                        addProfileField(certifications, c.credentialUrl, "Credential url");
                    }
                }

                if(!checkEmpty(p.achievements, achievements, "No achievements added")){
                    for(int i = 0; i < p.achievements.length; i++){
                        AchievementModel a = p.achievements[i];
                        if(p.achievements.length > 1){
                            achievements.add(createHeaderLabel("--- Achievement " + (i + 1) + " ---"));
                            achievements.add(new JLabel(""));
                        }
                        addProfileField(achievements, a.title, "Title");
                        addProfileField(achievements, a.description, "Description");
                    }
                }

                if(!checkEmpty(p.semesterMarks, semesterMarks, "No semester marks added")){
                    for(int i = 0; i < p.semesterMarks.length; i++){
                        SemesterMarkModel sm = p.semesterMarks[i];
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
                Mentor.GenerateResume(studentId);
                JOptionPane.showMessageDialog(this, "Resume generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            new MentorGUI();
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