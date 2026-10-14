import data.Role;
import myExceptions.*;
import session.Session;
import java.sql.*;
import db.DBConnection;

public class Admin {
    public static void createUser(Role r,String username,String password)throws RoleException,SQLException,GeneralException,MissingFieldException{
        Role r2=Session.getRole();
        if(r2!=Role.ADMIN){
            throw new RoleException("admin");
        }
        if(username==null||password==null||username.length()==0||password.length()==0){
            throw new MissingFieldException("username or password");
        }
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT 1 FROM users WHERE username=?");
            statement.setString(1,username);
            rs=statement.executeQuery();
            if(rs.next()){
                throw new GeneralException("Username already exists");
            }
            statement=connection.prepareStatement("INSERT INTO users(username,password,role) VALUES (?,?,?)");
            statement.setString(1,username);
            statement.setString(2,password);
            statement.setString(3,r.toString());
            statement.executeUpdate();
        }finally{
            if(connection!=null){
                connection.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(rs!=null){
                rs.close();
            }
        }
    }
}
