import java.sql.*;
import data.Role;
import db.DBConnection;
import myExceptions.LoginException;
import session.Session;
public class Login {
    public static void login(String username,String password) throws SQLException,LoginException{
        Connection connection=DBConnection.getConnection();
        PreparedStatement statement=connection.prepareStatement("SELECT id,password,is_active,role FROM users WHERE username=?");
        statement.setString(1,username);
        ResultSet rs=statement.executeQuery();
        if(!rs.next()){
            throw new LoginException(1);
        }
        String pass=rs.getString("password");
        if(!pass.equals(password)){
            throw new LoginException(2);
        }
        if(!rs.getBoolean("is_active")){
            throw new LoginException(3);
        }
        Role r=Role.valueOf(rs.getString("role"));
        int id=rs.getInt("id");
        Session.start(id, r);
    }
}
