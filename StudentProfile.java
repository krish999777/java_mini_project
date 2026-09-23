import java.sql.*;
import session.Session;
import db.DBConnection;
import data.Role;
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
                fields[cur]="githubUrl";
                values[cur]=githubUrl;
                cur++;
            }
            if(linkedinUrl.length()!=0){
                fields[cur]="linkedinUrl";
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
}
