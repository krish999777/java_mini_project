import data.*;
import session.Session;
import myExceptions.*;
import java.sql.*;
import db.DBConnection;

public class Mentor{
    public static StudentProfileModel[] getStudents() throws RoleException,SQLException{
        Role role=Session.getRole();
        if(role!=Role.MENTOR){
            throw new RoleException("Mentor");
        }
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement(
                "SELECT * FROM students s "
                // +
                // "LEFT JOIN student_skills ss ON s.id=ss.student_id "+
                // "RIGHT JOIN skills sk ON ss.skill_id=sk.id "+
                // "LEFT JOIN achievements a ON a.student_id=s.id "+
                // "LEFT JOIN education e ON e.student_id=s.id "+
                // "LEFT JOIN projects p ON p.student_id=s.id "+
                // "LEFT JOIN internships i ON i.student_id=s.id "+
                // "LEFT JOIN certifications c ON c.student_id=s.id "
                ,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY
            );
            rs=statement.executeQuery();
            int rowCount=rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            StudentProfileModel s[]=new StudentProfileModel[rowCount];
            int i=0;
            while(rs.next()){
                int id=rs.getInt("id");
                String fullName=rs.getString("full_name");
                String email=rs.getString("email");
                String phone=rs.getString("phone");
                String college=rs.getString("college");
                String course=rs.getString("course");
                int year=rs.getInt("year");
                String bio=rs.getString("bio");
                String githubUrl=rs.getString("github_url");
                String linkedinUrl=rs.getString("linkedin_url");
                s[i]=new StudentProfileModel(id,fullName,email,phone,college,course,year,bio,githubUrl,linkedinUrl);
                i++;
            }
            return s;
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