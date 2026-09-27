import java.sql.*;
import session.Session;
import db.DBConnection;
import data.*;
import myExceptions.*;

public class Internships {
    public static void addInternship(String company,String role,String description,String startDate,String endDate) throws RoleException,SQLException,GeneralException,MissingFieldException{
        Role roleEnum=Session.getRole();
        int userId=Session.getId();
        if(roleEnum!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        company=company==null ? "" : company.trim();
        if(company.length()==0){
            throw new MissingFieldException("company");
        }
        role=role==null ? "" : role.trim();
        description=description==null ? "" : description.trim();
        startDate=startDate==null ? "" : startDate.trim();
        endDate=endDate==null ? "" : endDate.trim();
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("INSERT INTO internships (student_id,company,role,description,start_date,end_date) VALUES (?,?,?,?,?,?)");
            statement.setInt(1,studentId);
            statement.setString(2,company);
            setValue(statement,3,role);
            setValue(statement,4,description);
            setValue(statement,5,startDate);
            setValue(statement,6,endDate);
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


    public static void removeInternship(int id) throws RoleException,SQLException,GeneralException{
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
            statement=connection.prepareStatement("DELETE FROM internships WHERE id=? AND student_id=?");
            statement.setInt(1,id);
            statement.setInt(2,studentId);
            int rows=statement.executeUpdate();
            if(rows==0){
                throw new GeneralException("Internship does not exist");
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


    public static void updateInternship(int id,String company,String role,String description,String startDate,String endDate) throws RoleException,SQLException,GeneralException,MissingFieldException{
        Role roleEnum=Session.getRole();
        int userId=Session.getId();
        if(roleEnum!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        company=company==null ? "" : company.trim();
        if(company.length()==0){
            throw new MissingFieldException("company");
        }
        role=role==null ? "" : role.trim();
        description=description==null ? "" : description.trim();
        startDate=startDate==null ? "" : startDate.trim();
        endDate=endDate==null ? "" : endDate.trim();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT 1 FROM internships WHERE id=? AND student_id=?");
            statement.setInt(1,id);
            statement.setInt(2,studentId);
            rs=statement.executeQuery();
            if(!rs.next()){
                throw new GeneralException("Internship does not exist");
            }
            statement=connection.prepareStatement("UPDATE internships SET company = ?,role = ?,description = ?,start_date = ?,end_date = ? WHERE id = ? AND student_id = ?");
            setValue(statement,1,company);
            setValue(statement,2,role);
            setValue(statement,3,description);
            setValue(statement,4,startDate);
            setValue(statement,5,endDate);
            statement.setInt(6,id);
            statement.setInt(7,studentId);
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


    public static InternshipModel[] getInternships() throws RoleException,SQLException,GeneralException{
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
            rs=statement.executeQuery("SELECT * FROM internships WHERE student_id="+studentId);
            int rowCount=rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            InternshipModel internships[]=new InternshipModel[rowCount];
            int i=0;
            while(rs.next()){
                internships[i]=new InternshipModel(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getString("company"),
                    rs.getString("role"),
                    rs.getString("description"),
                    rs.getString("start_date"),
                    rs.getString("end_date")
                );
                i++;
            }
            return internships;
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
