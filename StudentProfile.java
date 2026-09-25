import java.sql.*;
import session.Session;
import db.DBConnection;
import data.*;
import myExceptions.*;
public class StudentProfile {
    public static void createProfile(String fullName,String email,String phone,String college,String course,int year,String bio,String githubUrl,String linkedinUrl) throws RoleException,SQLException,MissingFieldException{
        Role role=Session.getRole();
        int userId=Session.getId();
        fullName=fullName.trim();
        email=email.trim();
        if(fullName.length()==0){
            throw new MissingFieldException("fullName");
        }
        if(email.length()==0){
            throw new MissingFieldException("email");
        }
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=DBConnection.getConnection();
            String fields[]=new String[12];
            String values[]=new String[12];
            fields[0]="user_id";
            values[0]="_"+userId;
            fields[1]="full_name";
            values[1]=fullName;
            fields[2]="email";
            values[2]=email;
            int cur=3;
            if(phone.length()!=0){
                fields[cur]="phone";
                values[cur]=phone;
                cur++;
            }
            if(college.length()!=0){
                fields[cur]="college";
                values[cur]=college;
                cur++;
            }
            if(course.length()!=0){
                fields[cur]="course";
                values[cur]=course;
                cur++;
            }
            if(year!=-1){
                fields[cur]="year";
                values[cur]="_"+year;
                cur++;
            }
            if(bio.length()!=0){
                fields[cur]="bio";
                values[cur]=bio;
                cur++;
            }
            if(githubUrl.length()!=0){
                fields[cur]="github_url";
                values[cur]=githubUrl;
                cur++;
            }
            if(linkedinUrl.length()!=0){
                fields[cur]="linkedin_url";
                values[cur]=linkedinUrl;
                cur++;
            }
            String placeholders="";
            for(int i=0;i<cur;i++){
                placeholders+="?,";
            }
            String finalFields[]=new String[cur];
            String finalValues[]=new String[cur];
            for(int i=0;i<cur;i++){
                finalFields[i]=fields[i];
                finalValues[i]=values[i];
            }
            placeholders=placeholders.substring(0,placeholders.length()-1);
            String query="INSERT INTO students ("+String.join(",",finalFields)+") VALUES ("+placeholders+")";

            statement=connection.prepareStatement(query);

            for(int i=0;i<cur;i++){
                String val=finalValues[i];
                if(val.charAt(0)=='_'){
                    statement.setInt(i+1,Integer.parseInt(val.substring(1)));
                }else{
                    statement.setString(i+1,val);
                }
            }
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
    public static StudentProfileModel getProfile() throws RoleException,SQLException,GeneralException{// this is not complete this should include all the other things like skills also
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        Connection connection=null;
        Statement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.createStatement();
            rs=statement.executeQuery("SELECT * FROM students WHERE user_id="+userId);
            if(!rs.next()){
                return null;//User profile does not exist
            }
            int year=rs.getInt("year");
            return new StudentProfileModel(
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("college"),
                rs.getString("course"),
                year==0?-1:year,
                rs.getString("bio"),
                rs.getString("github_url"),
                rs.getString("linkedin_url")
            );
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
    public static void updateProfile(String fullName,String email,String phone,String college,String course,int year,String bio,String githubUrl,String linkedinUrl) throws MissingFieldException,RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        fullName=fullName.trim();
        email=email.trim();
        if(fullName.length()==0){
            throw new MissingFieldException("fullName");
        }
        if(email.length()==0){
            throw new MissingFieldException("email");
        }
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT 1 FROM students WHERE user_id="+userId);
            rs=statement.executeQuery();
            if(!rs.next()){
                throw new GeneralException("User profile does not exist");
            }
            
            String query="UPDATE students SET full_name = ?,email = ?,phone = ?,college = ?,course = ?,year = ?,bio = ?,github_url = ?,linkedin_url = ? WHERE user_id = "+userId;
            statement=connection.prepareStatement(query);
            setValue(statement,1,fullName);
            setValue(statement,2,email);
            setValue(statement,3,phone);
            setValue(statement,4,college);
            setValue(statement,5,course);
            setValue(statement,6,year);
            setValue(statement,7,bio);
            setValue(statement,8,githubUrl);
            setValue(statement,9,linkedinUrl);

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
    private static void setValue(PreparedStatement s,int index,int value) throws SQLException{
        if(value==-1){
            s.setNull(index,Types.VARCHAR);
        }else{
            s.setInt(index,value);
        }
    }
    private static void setValue(PreparedStatement s,int index,String value) throws SQLException{
        if(value.length()==0){
            s.setNull(index,Types.INTEGER);
        }else{
            s.setString(index,value);
        }
    }
    public static int getStudentId(int userId) throws SQLException{
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT id FROM students WHERE user_id=?");
            statement.setInt(1,userId);
            rs=statement.executeQuery();
            if(!rs.next()){
                return -1;
            }
            return rs.getInt("id");
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
