import data.Role;
import myExceptions.*;
import session.Session;
import java.sql.*;
import db.DBConnection;
import java.util.ArrayList;

public class Admin {
    public static class MentorUser {
        public int id;
        public String username;
        public String fullName;

        public MentorUser(int id, String username, String fullName) {
            this.id = id;
            this.username = username;
            this.fullName = fullName;
        }

        public String toString() {
            if (fullName != null && fullName.trim().length() > 0) {
                return fullName + " (" + username + ")";
            }
            return username;
        }
    }

    public static MentorUser[] getMentors() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(
                "SELECT u.id, u.username, m.full_name " +
                "FROM users u " +
                "LEFT JOIN mentors m ON u.id = m.user_id " +
                "WHERE u.role = 'MENTOR' " +
                "ORDER BY u.username"
            );
            rs = statement.executeQuery();
            ArrayList<MentorUser> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new MentorUser(rs.getInt("id"), rs.getString("username"), rs.getString("full_name")));
            }
            return list.toArray(new MentorUser[0]);
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public static void createUser(Role r, String username, String password) throws RoleException, SQLException, GeneralException, MissingFieldException {
        createUser(r, username, password, null);
    }

    public static void createUser(Role r, String username, String password, Integer mentorId) throws RoleException, SQLException, GeneralException, MissingFieldException {
        Role r2 = Session.getRole();
        if (r2 != Role.ADMIN) {
            throw new RoleException("admin");
        }
        if (username == null || password == null || username.length() == 0 || password.length() == 0) {
            throw new MissingFieldException("username or password");
        }
        if (r == Role.STUDENT && (mentorId == null || mentorId <= 0)) {
            throw new MissingFieldException("mentor");
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM users WHERE username=?");
            statement.setString(1, username);
            rs = statement.executeQuery();
            if (rs.next()) {
                throw new GeneralException("Username already exists");
            }
            statement = connection.prepareStatement("INSERT INTO users(username,password,role,mentor_id,mentor) VALUES (?,?,?,?,?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, r.toString());
            if (r == Role.STUDENT && mentorId != null && mentorId > 0) {
                statement.setInt(4, mentorId);
                statement.setInt(5, mentorId);
            } else {
                statement.setNull(4, Types.INTEGER);
                statement.setNull(5, Types.INTEGER);
            }
            statement.executeUpdate();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (rs != null) rs.close();
        }
    }
}

