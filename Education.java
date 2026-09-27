import java.sql.*;
import session.Session;
import db.DBConnection;
import data.*;
import myExceptions.*;

public class Education {
    public static void addEducation(String institution,String degree,int startYear,int endYear,String grade) throws RoleException,SQLException,GeneralException,MissingFieldException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        institution=institution==null ? "" : institution.trim();
        if(institution.length()==0){
            throw new MissingFieldException("institution");
        }
        degree=degree==null ? "" : degree.trim();
        grade=grade==null ? "" : grade.trim();
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("INSERT INTO education (student_id,institution,degree,start_year,end_year,grade) VALUES (?,?,?,?,?,?)");
            statement.setInt(1,studentId);
            statement.setString(2,institution);
            setValue(statement,3,degree);
            setValue(statement,4,startYear);
            setValue(statement,5,endYear);
            setValue(statement,6,grade);
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


    public static void removeEducation(int id) throws RoleException,SQLException,GeneralException{
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
            statement=connection.prepareStatement("DELETE FROM education WHERE id=? AND student_id=?");
            statement.setInt(1,id);
            statement.setInt(2,studentId);
            int rows=statement.executeUpdate();
            if(rows==0){
                throw new GeneralException("Education does not exist");
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


    public static void updateEducation(int id,String institution,String degree,int startYear,int endYear,String grade) throws RoleException,SQLException,GeneralException,MissingFieldException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        institution=institution==null ? "" : institution.trim();
        if(institution.length()==0){
            throw new MissingFieldException("institution");
        }
        degree=degree==null ? "" : degree.trim();
        grade=grade==null ? "" : grade.trim();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT 1 FROM education WHERE id=? AND student_id=?");
            statement.setInt(1,id);
            statement.setInt(2,studentId);
            rs=statement.executeQuery();
            if(!rs.next()){
                throw new GeneralException("Education does not exist");
            }
            statement=connection.prepareStatement("UPDATE education SET institution = ?,degree = ?,start_year = ?,end_year = ?,grade = ? WHERE id = ? AND student_id = ?");
            setValue(statement,1,institution);
            setValue(statement,2,degree);
            setValue(statement,3,startYear);
            setValue(statement,4,endYear);
            setValue(statement,5,grade);
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


    public static EducationModel[] getEducation() throws RoleException,SQLException,GeneralException{
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
            rs=statement.executeQuery("SELECT * FROM education WHERE student_id="+studentId);
            int rowCount=rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            EducationModel education[]=new EducationModel[rowCount];
            int i=0;
            while(rs.next()){
                int startYear=rs.getInt("start_year");
                int endYear=rs.getInt("end_year");
                education[i]=new EducationModel(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getString("institution"),
                    rs.getString("degree"),
                    startYear==0?-1:startYear,
                    endYear==0?-1:endYear,
                    rs.getString("grade")
                );
                i++;
            }
            return education;
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


    public static EducationModel[] getEducations() throws RoleException,SQLException,GeneralException{
        return getEducation();
    }


    private static void setValue(PreparedStatement s,int index,int value) throws SQLException{
        if(value==-1){
            s.setNull(index,Types.INTEGER);
        }else{
            s.setInt(index,value);
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
