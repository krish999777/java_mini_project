import data.*;
import session.Session;
import myExceptions.*;
import java.sql.*;
import db.DBConnection;
import java.io.IOException;

public class Mentor {
    public static StudentProfileModel[] getStudents() throws RoleException, SQLException {
        return getStudents("", "", "", -1, "");
    }

    public static StudentProfileModel[] getStudents(String name, String college, String course, int year, String skill)
            throws RoleException, SQLException {
        Role role = Session.getRole();
        if (role != Role.MENTOR) {
            throw new RoleException("Mentor");
        }
        name = name == null ? "" : name.trim();
        college = college == null ? "" : college.trim();
        course = course == null ? "" : course.trim();
        skill = skill == null ? "" : skill.trim();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.getConnection();
            String query = "SELECT DISTINCT s.id,s.full_name,s.email,s.phone,s.college,s.course,s.year,s.bio,s.github_url,s.linkedin_url FROM students s ";
            if (skill.length() != 0) {
                query += "JOIN student_skills ss ON ss.student_id=s.id JOIN skills sk ON ss.skill_id=sk.id ";
            }
            String conditions[] = new String[5];
            String values[] = new String[5];
            int cur = 0;
            if (name.length() != 0) {
                conditions[cur] = "LOWER(s.full_name) LIKE ?";
                values[cur] = "%" + name.toLowerCase() + "%";
                cur++;
            }
            if (college.length() != 0) {
                conditions[cur] = "LOWER(s.college) LIKE ?";
                values[cur] = "%" + college.toLowerCase() + "%";
                cur++;
            }
            if (course.length() != 0) {
                conditions[cur] = "LOWER(s.course) LIKE ?";
                values[cur] = "%" + course.toLowerCase() + "%";
                cur++;
            }
            if (year != -1) {
                conditions[cur] = "s.year = ?";
                values[cur] = "_" + year;
                cur++;
            }
            if (skill.length() != 0) {
                conditions[cur] = "LOWER(sk.name) LIKE ?";
                values[cur] = "%" + skill.toLowerCase() + "%";
                cur++;
            }
            if (cur != 0) {
                String finalConditions[] = new String[cur];
                for (int i = 0; i < cur; i++) {
                    finalConditions[i] = conditions[i];
                }
                query += "WHERE " + String.join(" AND ", finalConditions) + " ";
            }
            query += "ORDER BY s.id";

            statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < cur; i++) {
                String val = values[i];
                if (val.charAt(0) == '_') {
                    statement.setInt(i + 1, Integer.parseInt(val.substring(1)));
                } else {
                    statement.setString(i + 1, val);
                }
            }
            rs = statement.executeQuery();
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            StudentProfileModel s[] = new StudentProfileModel[rowCount];
            int i = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String col = rs.getString("college");
                String cou = rs.getString("course");
                int yr = rs.getInt("year");
                String bio = rs.getString("bio");
                String githubUrl = rs.getString("github_url");
                String linkedinUrl = rs.getString("linkedin_url");
                s[i] = new StudentProfileModel(
                        id,
                        fullName,
                        email,
                        phone,
                        col,
                        cou,
                        yr == 0 ? -1 : yr,
                        bio,
                        githubUrl,
                        linkedinUrl);
                i++;
            }
            return s;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    public static StudentProfileModel getStudent(int studentId) throws RoleException, SQLException {
        Role role = Session.getRole();
        if (role != Role.MENTOR) {
            throw new RoleException("Mentor");
        }
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM students WHERE id=" + studentId);
            if (!rs.next()) {
                return null;
            }
            int id = rs.getInt("id");
            String fullName = rs.getString("full_name");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String col = rs.getString("college");
            String cou = rs.getString("course");
            int yr = rs.getInt("year");
            String bio = rs.getString("bio");
            String githubUrl = rs.getString("github_url");
            String linkedinUrl = rs.getString("linkedin_url");
            String skills[] = getStudentSkills(connection, id);
            ProjectModel projects[] = getStudentProjects(connection, id);
            AchievementModel achievements[] = getStudentAchievements(connection, id);
            CertificationModel certifications[] = getStudentCertifications(connection, id);
            InternshipModel internships[] = getStudentInternships(connection, id);
            EducationModel education[] = getStudentEducation(connection, id);
            return new StudentProfileModel(
                    id,
                    fullName,
                    email,
                    phone,
                    col,
                    cou,
                    yr == 0 ? -1 : yr,
                    bio,
                    githubUrl,
                    linkedinUrl,
                    skills,
                    projects,
                    achievements,
                    certifications,
                    internships,
                    education);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    public static void GenerateResume(int studentId) throws RoleException, SQLException, GeneralException, IOException {
        ResumeGenerator.generate(getStudent(studentId));
    }

    private static String[] getStudentSkills(Connection connection, int studentId) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(
                    "SELECT sk.name as skill FROM student_skills ss JOIN skills sk ON ss.skill_id=sk.id WHERE ss.student_id="
                            + studentId);
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            String skills[] = new String[rowCount];
            int i = 0;
            while (rs.next()) {
                skills[i] = rs.getString("skill");
                i++;
            }
            return skills;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    private static ProjectModel[] getStudentProjects(Connection connection, int studentId) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery("SELECT * FROM projects WHERE student_id=" + studentId);
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            ProjectModel projects[] = new ProjectModel[rowCount];
            int i = 0;
            while (rs.next()) {
                projects[i] = new ProjectModel(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("github_url"),
                        rs.getString("live_url"));
                i++;
            }
            return projects;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    private static AchievementModel[] getStudentAchievements(Connection connection, int studentId) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery("SELECT * FROM achievements WHERE student_id=" + studentId);
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            AchievementModel achievements[] = new AchievementModel[rowCount];
            int i = 0;
            while (rs.next()) {
                achievements[i] = new AchievementModel(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getString("title"),
                        rs.getString("description"));
                i++;
            }
            return achievements;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    private static CertificationModel[] getStudentCertifications(Connection connection, int studentId)
            throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery("SELECT * FROM certifications WHERE student_id=" + studentId);
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            CertificationModel certifications[] = new CertificationModel[rowCount];
            int i = 0;
            while (rs.next()) {
                certifications[i] = new CertificationModel(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("issuing_organization"),
                        rs.getString("credential_id"),
                        rs.getString("credential_url"));
                i++;
            }
            return certifications;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    private static InternshipModel[] getStudentInternships(Connection connection, int studentId) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery("SELECT * FROM internships WHERE student_id=" + studentId);
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            InternshipModel internships[] = new InternshipModel[rowCount];
            int i = 0;
            while (rs.next()) {
                internships[i] = new InternshipModel(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getString("company"),
                        rs.getString("role"),
                        rs.getString("description"),
                        rs.getString("start_date"),
                        rs.getString("end_date"));
                i++;
            }
            return internships;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    private static EducationModel[] getStudentEducation(Connection connection, int studentId) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery("SELECT * FROM education WHERE student_id=" + studentId);
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            EducationModel education[] = new EducationModel[rowCount];
            int i = 0;
            while (rs.next()) {
                int startYear = rs.getInt("start_year");
                int endYear = rs.getInt("end_year");
                education[i] = new EducationModel(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getString("school"),
                        rs.getString("board"),
                        startYear == 0 ? -1 : startYear,
                        endYear == 0 ? -1 : endYear,
                        rs.getString("grade"));
                i++;
            }
            return education;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }
}