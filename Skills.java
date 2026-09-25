import session.Session;
import java.sql.*;
import db.DBConnection;
import data.Role;
import myExceptions.*;
public class Skills {
    public static void addSkill(String skill) throws RoleException,SQLException,GeneralException{
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        int userId=Session.getId();
        Role role=Session.getRole();
        skill=skill.trim();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT id FROM skills WHERE LOWER(name)=?");
            statement.setString(1,skill.toLowerCase());
            rs=statement.executeQuery();
            int id;
            if(!rs.next()){
                statement=connection.prepareStatement("INSERT INTO skills (name) VALUES (?)");
                statement.setString(1,skill);
                statement.executeUpdate();
                statement=connection.prepareStatement("SELECT id FROM skills WHERE name=?");
                statement.setString(1,skill);
                rs=statement.executeQuery();
                if(rs.next()){
                    id=rs.getInt("id");
                }else{
                    throw new GeneralException("Unknown error in inserting/retriving skills");
                }
            }else{
                id=rs.getInt("id");
            }
            System.out.println(id);
        }finally{

        }
    }
}
