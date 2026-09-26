import java.sql.*;
import session.Session;
import db.DBConnection;
import data.*;
import myExceptions.*;

public class Achievements {
    public static void addAchievement(String title,String description) throws RoleException,SQLException,GeneralException,MissingFieldException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        title=title==null ? "" : title.trim();
        if(title.length()==0){
            throw new MissingFieldException("title");
        }
        description=description==null ? "" : description.trim();
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("INSERT INTO achievements (student_id,title,description) VALUES (?,?,?)");
            statement.setInt(1,studentId);
            statement.setString(2,title);
            setValue(statement,3,description);
            statement.executeUpdate();
        }finally{
            if(connection!=null){
                connection.close();
            }
            if(statement!=null){
                statement.close();
            }
        }
    }


    public static void removeAchievement(int id) throws RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("DELETE FROM achievements WHERE id=? AND student_id=?");
            statement.setInt(1,id);
            statement.setInt(2,studentId);
            int rows=statement.executeUpdate();
            if(rows==0){
                throw new GeneralException("Achievement does not exist");
            }
        }finally{
            if(connection!=null){
                connection.close();
            }
            if(statement!=null){
                statement.close();
            }
        }
    }


    public static void updateAchievement(int id,String title,String description) throws RoleException,SQLException,GeneralException,MissingFieldException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        title=title==null ? "" : title.trim();
        if(title.length()==0){
            throw new MissingFieldException("title");
        }
        description=description==null ? "" : description.trim();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT 1 FROM achievements WHERE id=? AND student_id=?");
            statement.setInt(1,id);
            statement.setInt(2,studentId);
            rs=statement.executeQuery();
            if(!rs.next()){
                throw new GeneralException("Achievement does not exist");
            }
            statement=connection.prepareStatement("UPDATE achievements SET title = ?,description = ? WHERE id = ? AND student_id = ?");
            setValue(statement,1,title);
            setValue(statement,2,description);
            statement.setInt(3,id);
            statement.setInt(4,studentId);
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


    public static AchievementModel[] getAchievements() throws RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        Connection connection=null;
        Statement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=statement.executeQuery("SELECT * FROM achievements WHERE student_id="+studentId);
            int rowCount=rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            AchievementModel achievements[]=new AchievementModel[rowCount];
            int i=0;
            while(rs.next()){
                achievements[i]=new AchievementModel(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getString("title"),
                    rs.getString("description")
                );
                i++;
            }
            return achievements;
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


    private static void setValue(PreparedStatement s,int index,String value) throws SQLException{
        if(value.length()==0){
            s.setNull(index,Types.VARCHAR);
        }else{
            s.setString(index,value);
        }
    }
}
