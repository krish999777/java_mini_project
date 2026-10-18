import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import session.Session;
import data.*;
import java.util.ArrayList;
import java.util.List;

public class MentorGUI extends JFrame implements ActionListener {
    JLabel l1;
    JButton b1;
    JPanel header, top, searchPanel, centerPanel, bottomPanel;
    JTable studentTable;
    DefaultTableModel tableModel;
    JButton bViewStudent;
    JLabel lblCount;
    StudentProfileModel p[];
    JTextField tfName, tfCollege, tfCourse, tfYear, tfSkill;
    JButton bSearch, bReset;

    public MentorGUI() {
        setTitle("Mentor Page");
        setSize(960, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(248, 250, 252));

        // Header with clear title at the top
        header = new JPanel(new BorderLayout(15, 0));
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        l1 = new JLabel("Mentor Page");
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

        // Search Panel
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

        // Center Table Panel
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(248, 250, 252));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        String columnNames[] = {"ID", "Name", "Email", "Phone", "College", "Course", "Year", "Semester", "Bio"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(28);
        studentTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setGridColor(new Color(226, 232, 240));
        studentTable.setSelectionBackground(new Color(219, 234, 254));
        studentTable.setSelectionForeground(new Color(30, 58, 138));
        studentTable.setShowGrid(true);

        // Adjust column widths
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(6).setPreferredWidth(50);
        studentTable.getColumnModel().getColumn(7).setPreferredWidth(60);
        studentTable.getColumnModel().getColumn(8).setPreferredWidth(180);

        JTableHeader th = studentTable.getTableHeader();
        th.setFont(new Font("SansSerif", Font.BOLD, 13));
        th.setBackground(new Color(30, 41, 59));
        th.setForeground(Color.WHITE);
        th.setPreferredSize(new Dimension(th.getWidth(), 32));

        studentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    viewSelectedStudent();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Action Bar
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(248, 250, 252));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        lblCount = new JLabel("Total Students: 0");
        lblCount.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblCount.setForeground(new Color(71, 85, 105));

        bViewStudent = new JButton("View Selected Student");
        bViewStudent.setFont(new Font("SansSerif", Font.BOLD, 13));
        bViewStudent.setBackground(new Color(37, 99, 235));
        bViewStudent.setForeground(Color.WHITE);
        bViewStudent.setFocusPainted(false);
        bViewStudent.setOpaque(true);
        bViewStudent.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bViewStudent.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(29, 78, 216), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        bViewStudent.addActionListener(this);

        bottomPanel.add(lblCount, BorderLayout.WEST);
        bottomPanel.add(bViewStudent, BorderLayout.EAST);
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        try {
            p = Mentor.getStudents();
            loadStudents(p);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Error Loading Students", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }

    private JLabel createSearchLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(new Color(30, 41, 59));
        return lbl;
    }

    private void loadStudents(StudentProfileModel p[]) {
        this.p = p;
        tableModel.setRowCount(0);
        if (p == null || p.length == 0) {
            lblCount.setText("Total Students: 0");
        } else {
            for (StudentProfileModel s : p) {
                tableModel.addRow(new Object[]{
                    s.id,
                    s.fullName == null ? "" : s.fullName,
                    s.email == null ? "" : s.email,
                    s.phone == null ? "" : s.phone,
                    s.college == null ? "" : s.college,
                    s.course == null ? "" : s.course,
                    s.year == -1 || s.year == 0 ? "" : s.year,
                    s.currentSemester <= 0 ? 1 : s.currentSemester,
                    s.bio == null ? "" : s.bio
                });
            }
            lblCount.setText("Total Students: " + p.length);
        }
    }

    private void viewSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table first.", "Selection Required", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int studentId = (Integer) studentTable.getValueAt(selectedRow, 0);
        new StudentDetailGUI(studentId);
        dispose();
    }

    public void actionPerformed(ActionEvent ae) {
        JButton source = (JButton) ae.getSource();
        if (source == b1) {
            Session.logout();
            new LoginGUI();
            dispose();
        } else if (source == bSearch) {
            try {
                String name = tfName.getText().trim();
                String college = tfCollege.getText().trim();
                String course = tfCourse.getText().trim();
                String yearStr = tfYear.getText().trim();
                int year = -1;
                if (yearStr.length() != 0) {
                    try {
                        year = Integer.parseInt(yearStr);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Year must be a number", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String skill = tfSkill.getText().trim();
                p = Mentor.getStudents(name, college, course, year, skill);
                loadStudents(p);
                if (p == null || p.length == 0) {
                    JOptionPane.showMessageDialog(this, "No students found matching your criteria.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.toString(), "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == bReset) {
            tfName.setText("");
            tfCollege.setText("");
            tfCourse.setText("");
            tfYear.setText("");
            tfSkill.setText("");
            try {
                p = Mentor.getStudents();
                loadStudents(p);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == bViewStudent) {
            viewSelectedStudent();
        }
    }
}

class StudentDetailGUI extends JFrame implements ActionListener {
    int studentId;
    JLabel l1;
    JButton b1, b2;
    JTabbedPane pane;
    JPanel header, footer, profile;

    StudentDetailGUI() {}

    StudentDetailGUI(int id) {
        this.studentId = id;
        setTitle("Mentor Page");
        setSize(780, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(248, 250, 252));

        // Header with clear title at the top
        header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        l1 = new JLabel("Mentor Page", SwingConstants.CENTER);
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

        profile = new JPanel(new GridLayout(0, 2, 12, 12));
        profile.setBackground(Color.WHITE);
        profile.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        JScrollPane skillsPane = null;
        JScrollPane educationPane = null;
        JScrollPane projectsPane = null;
        JScrollPane internshipsPane = null;
        JScrollPane certificationsPane = null;
        JScrollPane achievementsPane = null;
        JScrollPane semesterMarksPane = null;

        try {
            StudentProfileModel p = Mentor.getStudent(id);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Student profile does not exist", "Profile Not Found", JOptionPane.ERROR_MESSAGE);
            } else {
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

                // Skills table
                List<Object[]> skillRows = new ArrayList<>();
                if (p.skills != null) {
                    for (int i = 0; i < p.skills.length; i++) {
                        skillRows.add(new Object[]{i + 1, p.skills[i]});
                    }
                }
                skillsPane = createTableTab(new String[]{"#", "Skill Name"}, skillRows, "No skills added");

                // Education table
                List<Object[]> edRows = new ArrayList<>();
                if (p.education != null) {
                    for (EducationModel ed : p.education) {
                        edRows.add(new Object[]{
                            ed.school == null ? "" : ed.school,
                            ed.board == null ? "" : ed.board,
                            ed.startYear == -1 || ed.startYear == 0 ? "" : ed.startYear,
                            ed.endYear == -1 || ed.endYear == 0 ? "" : ed.endYear,
                            ed.grade == null ? "" : ed.grade
                        });
                    }
                }
                educationPane = createTableTab(new String[]{"School", "Board", "Start Year", "End Year", "Grade"}, edRows, "No education details added");

                // Projects table
                List<Object[]> projRows = new ArrayList<>();
                if (p.projects != null) {
                    for (ProjectModel pr : p.projects) {
                        projRows.add(new Object[]{
                            pr.title == null ? "" : pr.title,
                            pr.description == null ? "" : pr.description,
                            pr.githubUrl == null ? "" : pr.githubUrl,
                            pr.liveUrl == null ? "" : pr.liveUrl
                        });
                    }
                }
                projectsPane = createTableTab(new String[]{"Title", "Description", "GitHub URL", "Live URL"}, projRows, "No projects added");

                // Internships table
                List<Object[]> inRows = new ArrayList<>();
                if (p.internships != null) {
                    for (InternshipModel in : p.internships) {
                        inRows.add(new Object[]{
                            in.company == null ? "" : in.company,
                            in.role == null ? "" : in.role,
                            in.description == null ? "" : in.description,
                            in.startDate == null ? "" : in.startDate,
                            in.endDate == null ? "" : in.endDate
                        });
                    }
                }
                internshipsPane = createTableTab(new String[]{"Company", "Role", "Description", "Start Date", "End Date"}, inRows, "No internships added");

                // Certifications table
                List<Object[]> certRows = new ArrayList<>();
                if (p.certifications != null) {
                    for (CertificationModel c : p.certifications) {
                        certRows.add(new Object[]{
                            c.name == null ? "" : c.name,
                            c.issuingOrganization == null ? "" : c.issuingOrganization,
                            c.credentialId == null ? "" : c.credentialId,
                            c.credentialUrl == null ? "" : c.credentialUrl
                        });
                    }
                }
                certificationsPane = createTableTab(new String[]{"Name", "Organization", "Credential ID", "Credential URL"}, certRows, "No certifications added");

                // Achievements table
                List<Object[]> achRows = new ArrayList<>();
                if (p.achievements != null) {
                    for (AchievementModel a : p.achievements) {
                        achRows.add(new Object[]{
                            a.title == null ? "" : a.title,
                            a.description == null ? "" : a.description
                        });
                    }
                }
                achievementsPane = createTableTab(new String[]{"Title", "Description"}, achRows, "No achievements added");

                // Semester Marks table
                List<Object[]> semRows = new ArrayList<>();
                if (p.semesterMarks != null) {
                    for (SemesterMarkModel sm : p.semesterMarks) {
                        semRows.add(new Object[]{"Semester " + sm.semester, sm.marks});
                    }
                }
                semesterMarksPane = createTableTab(new String[]{"Semester", "Marks"}, semRows, "No semester marks added");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Error Loading Profile", JOptionPane.ERROR_MESSAGE);
        }

        JScrollPane profileScroll = new JScrollPane(profile);
        profileScroll.setBorder(null);
        profileScroll.getVerticalScrollBar().setUnitIncrement(16);

        pane.addTab("Profile", profileScroll);
        pane.addTab("Skills", skillsPane != null ? skillsPane : new JScrollPane());
        pane.addTab("Education", educationPane != null ? educationPane : new JScrollPane());
        pane.addTab("Projects", projectsPane != null ? projectsPane : new JScrollPane());
        pane.addTab("Internships", internshipsPane != null ? internshipsPane : new JScrollPane());
        pane.addTab("Certifications", certificationsPane != null ? certificationsPane : new JScrollPane());
        pane.addTab("Achievements", achievementsPane != null ? achievementsPane : new JScrollPane());
        pane.addTab("Semester Marks", semesterMarksPane != null ? semesterMarksPane : new JScrollPane());

        b1.addActionListener(this);
        b2.addActionListener(this);
        add(header, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JScrollPane createTableTab(String[] headers, List<Object[]> rows, String emptyMsg) {
        if (rows == null || rows.isEmpty()) {
            JPanel p = new JPanel(new BorderLayout());
            p.setBackground(Color.WHITE);
            p.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
            JLabel empty = new JLabel(emptyMsg, SwingConstants.CENTER);
            empty.setFont(new Font("SansSerif", Font.ITALIC, 14));
            empty.setForeground(new Color(100, 116, 139));
            p.add(empty, BorderLayout.CENTER);
            return new JScrollPane(p);
        }
        DefaultTableModel model = new DefaultTableModel(headers, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        for (Object[] row : rows) {
            model.addRow(row);
        }
        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setGridColor(new Color(226, 232, 240));
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(new Color(30, 58, 138));

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("SansSerif", Font.BOLD, 13));
        th.setBackground(new Color(30, 41, 59));
        th.setForeground(Color.WHITE);
        th.setPreferredSize(new Dimension(th.getWidth(), 32));

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        sp.getViewport().setBackground(Color.WHITE);
        return sp;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            try {
                Mentor.GenerateResume(studentId);
                JOptionPane.showMessageDialog(this, "Resume generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            new MentorGUI();
            dispose();
        }
    }

    private void addProfileField(Container c, String value, String field) {
        if (value != null && value.trim().length() != 0) {
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

    private void addProfileField(Container c, int value, String field) {
        if (value != -1) {
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