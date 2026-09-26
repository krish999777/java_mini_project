import java.sql.*;
import session.Session;
import db.DBConnection;
import data.*;
import myExceptions.*;

public class Projects {
    public static void addProject(String title,String description,String githubUrl,String liveUrl) throws RoleException,SQLException,GeneralException,MissingFieldException{
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
        githubUrl=githubUrl==null ? "" : githubUrl.trim();
        liveUrl=liveUrl==null ? "" : liveUrl.trim();
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("INSERT INTO projects (student_id,title,description,github_url,live_url) VALUES (?,?,?,?,?)");
            statement.setInt(1,studentId);
            statement.setString(2,title);
            setValue(statement,3,description);
            setValue(statement,4,githubUrl);
            setValue(statement,5,liveUrl);
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



    public static void removeProject(int id) throws RoleException,SQLException,GeneralException{
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
            statement=connection.prepareStatement("DELETE FROM projects WHERE id=? AND student_id=?");
            statement.setInt(1,id);
            statement.setInt(2,studentId);
            int rows=statement.executeUpdate();
            if(rows==0){
                throw new GeneralException("Project does not exist");
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




    public static void removeProject(String title) throws RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        title=title==null ? "" : title.trim();
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
            statement=connection.prepareStatement("DELETE FROM projects WHERE LOWER(title)=? AND student_id=?");
            statement.setString(1,title.toLowerCase());
            statement.setInt(2,studentId);
            int rows=statement.executeUpdate();
            if(rows==0){
                throw new GeneralException("Project does not exist");
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



    
    public static void updateProject(int id,String title,String description,String githubUrl,String liveUrl) throws RoleException,SQLException,GeneralException,MissingFieldException{
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
        githubUrl=githubUrl==null ? "" : githubUrl.trim();
        liveUrl=liveUrl==null ? "" : liveUrl.trim();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT 1 FROM projects WHERE id=? AND student_id=?");
            statement.setInt(1,id);
            statement.setInt(2,studentId);
            rs=statement.executeQuery();
            if(!rs.next()){
                throw new GeneralException("Project does not exist");
            }
            statement=connection.prepareStatement("UPDATE projects SET title = ?,description = ?,github_url = ?,live_url = ? WHERE id = ? AND student_id = ?");
            setValue(statement,1,title);
            setValue(statement,2,description);
            setValue(statement,3,githubUrl);
            setValue(statement,4,liveUrl);
            statement.setInt(5,id);
            statement.setInt(6,studentId);
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
    public static ProjectModel[] getProjects() throws RoleException,SQLException,GeneralException{
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
            rs=statement.executeQuery("SELECT * FROM projects WHERE student_id="+studentId);
            int rowCount=rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            ProjectModel projects[]=new ProjectModel[rowCount];
            int i=0;
            while(rs.next()){
                projects[i]=new ProjectModel(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("github_url"),
                    rs.getString("live_url")
                );
                i++;
            }
            return projects;
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
