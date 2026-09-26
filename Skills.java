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
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
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
            statement=connection.prepareStatement("INSERT INTO student_skills (student_id,skill_id) VALUES (?,?)");
            statement.setInt(1,studentId);
            statement.setInt(2,id);
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
    public static void removeSkill(String skill) throws RoleException,SQLException,GeneralException{
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        int userId=Session.getId();
        Role role=Session.getRole();
        skill=skill.trim();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT id FROM skills WHERE LOWER(name)=?");
            statement.setString(1,skill.toLowerCase());
            rs=statement.executeQuery();
            if(!rs.next()){
                throw new GeneralException("Skill does not exist");
            }
            int id=rs.getInt("id");
            statement=connection.prepareStatement("DELETE FROM student_skills WHERE student_id=? AND skill_id=?");
            statement.setInt(1,studentId);
            statement.setInt(2,id);
            int rows=statement.executeUpdate();
            if(rows==0){
                throw new GeneralException("Skill not associated with student");
            }
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
    public static String[] getSkills() throws RoleException,SQLException{
        int userId=Session.getId();
        Role role=Session.getRole();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        Connection connection=null;
        Statement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=statement.executeQuery("SELECT skills.name as skill FROM users RIGHT JOIN students ON students.user_id=users.id RIGHT JOIN student_skills ON students.id=student_skills.student_id LEFT JOIN skills on student_skills.skill_id=skills.id WHERE users.id="+userId);
            int rowCount=rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            String skills[]=new String[rowCount];
            int i=0;
            while(rs.next()){
                skills[i]=rs.getString("skill");
                i++;
            }
            return skills;
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
