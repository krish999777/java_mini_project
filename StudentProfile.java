import java.sql.*;
import session.Session;
import db.DBConnection;
import data.*;
import myExceptions.*;
public class StudentProfile {
    public static void createProfile(String fullName,String email,String phone,String college,String course,int year,String bio,String githubUrl,String linkedinUrl,
            String hobbies, String dob, String fatherName, String fatherPhone, String fatherEmail, String fatherOccupation,
            String motherName, String motherPhone, String motherEmail, String motherOccupation) throws RoleException,SQLException,MissingFieldException{
        Role role=Session.getRole();
        int userId=Session.getId();
        fullName=fullName==null?"":fullName.trim();
        email=email==null?"":email.trim();
        phone=phone==null?"":phone.trim();
        college=college==null?"":college.trim();
        course=course==null?"":course.trim();
        bio=bio==null?"":bio.trim();
        githubUrl=githubUrl==null?"":githubUrl.trim();
        linkedinUrl=linkedinUrl==null?"":linkedinUrl.trim();
        hobbies=hobbies==null?"":hobbies.trim();
        dob=dob==null?"":dob.trim();
        fatherName=fatherName==null?"":fatherName.trim();
        fatherPhone=fatherPhone==null?"":fatherPhone.trim();
        fatherEmail=fatherEmail==null?"":fatherEmail.trim();
        fatherOccupation=fatherOccupation==null?"":fatherOccupation.trim();
        motherName=motherName==null?"":motherName.trim();
        motherPhone=motherPhone==null?"":motherPhone.trim();
        motherEmail=motherEmail==null?"":motherEmail.trim();
        motherOccupation=motherOccupation==null?"":motherOccupation.trim();
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
            String fields[]=new String[25];
            String values[]=new String[25];
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
            if(hobbies.length()!=0){
                fields[cur]="hobbies";
                values[cur]=hobbies;
                cur++;
            }
            if(dob.length()!=0){
                fields[cur]="dob";
                values[cur]=dob;
                cur++;
            }
            if(fatherName.length()!=0){
                fields[cur]="father_name";
                values[cur]=fatherName;
                cur++;
            }
            if(fatherPhone.length()!=0){
                fields[cur]="father_phone";
                values[cur]=fatherPhone;
                cur++;
            }
            if(fatherEmail.length()!=0){
                fields[cur]="father_email";
                values[cur]=fatherEmail;
                cur++;
            }
            if(fatherOccupation.length()!=0){
                fields[cur]="father_occupation";
                values[cur]=fatherOccupation;
                cur++;
            }
            if(motherName.length()!=0){
                fields[cur]="mother_name";
                values[cur]=motherName;
                cur++;
            }
            if(motherPhone.length()!=0){
                fields[cur]="mother_phone";
                values[cur]=motherPhone;
                cur++;
            }
            if(motherEmail.length()!=0){
                fields[cur]="mother_email";
                values[cur]=motherEmail;
                cur++;
            }
            if(motherOccupation.length()!=0){
                fields[cur]="mother_occupation";
                values[cur]=motherOccupation;
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

    public static void createProfile(String fullName,String email,String phone,String college,String course,int year,String bio,String githubUrl,String linkedinUrl) throws RoleException,SQLException,MissingFieldException{
        createProfile(fullName, email, phone, college, course, year, bio, githubUrl, linkedinUrl, "", "", "", "", "", "", "", "", "", "");
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
            int currentSemester=rs.getInt("current_semester");
            int id=rs.getInt("id");
            String fullName=rs.getString("full_name");
            String email=rs.getString("email");
            String phone=rs.getString("phone");
            String college=rs.getString("college");
            String course=rs.getString("course");
            String bio=rs.getString("bio");
            String githubUrl=rs.getString("github_url");
            String linkedinUrl=rs.getString("linkedin_url");
            String hobbies=rs.getString("hobbies");
            String dob=rs.getString("dob");
            String fatherName=rs.getString("father_name");
            String fatherPhone=rs.getString("father_phone");
            String fatherEmail=rs.getString("father_email");
            String fatherOccupation=rs.getString("father_occupation");
            String motherName=rs.getString("mother_name");
            String motherPhone=rs.getString("mother_phone");
            String motherEmail=rs.getString("mother_email");
            String motherOccupation=rs.getString("mother_occupation");
            String skills[]=Skills.getSkills();
            ProjectModel projects[]=Projects.getProjects();
            AchievementModel achievements[]=Achievements.getAchievements();
            CertificationModel certifications[]=Certifications.getCertifications();
            InternshipModel internships[]=Internships.getInternships();
            EducationModel education[]=Education.getEducation();
            SemesterMarkModel semesterMarks[]=SemesterMarks.getEnteredMarks();
            return new StudentProfileModel(
                id,
                fullName,
                email,
                phone,
                college,
                course,
                year==0?-1:year,
                currentSemester<=0?1:currentSemester,
                bio,
                githubUrl,
                linkedinUrl,
                hobbies,
                dob,
                fatherName,
                fatherPhone,
                fatherEmail,
                fatherOccupation,
                motherName,
                motherPhone,
                motherEmail,
                motherOccupation,
                skills,
                projects,
                achievements,
                certifications,
                internships,
                education,
                semesterMarks
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


    public static void setCurrentSemester(int semester) throws RoleException,SQLException,GeneralException{
        SemesterMarks.setCurrentSemester(semester);
    }


    public static int getCurrentSemester() throws RoleException,SQLException,GeneralException{
        return SemesterMarks.getCurrentSemester();
    }


    public static void updateProfile(String fullName,String email,String phone,String college,String course,int year,int currentSemester,String bio,String githubUrl,String linkedinUrl,
            String hobbies, String dob, String fatherName, String fatherPhone, String fatherEmail, String fatherOccupation,
            String motherName, String motherPhone, String motherEmail, String motherOccupation) throws MissingFieldException,RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        fullName=fullName==null?"":fullName.trim();
        email=email==null?"":email.trim();
        phone=phone==null?"":phone.trim();
        college=college==null?"":college.trim();
        course=course==null?"":course.trim();
        bio=bio==null?"":bio.trim();
        githubUrl=githubUrl==null?"":githubUrl.trim();
        linkedinUrl=linkedinUrl==null?"":linkedinUrl.trim();
        hobbies=hobbies==null?"":hobbies.trim();
        dob=dob==null?"":dob.trim();
        fatherName=fatherName==null?"":fatherName.trim();
        fatherPhone=fatherPhone==null?"":fatherPhone.trim();
        fatherEmail=fatherEmail==null?"":fatherEmail.trim();
        fatherOccupation=fatherOccupation==null?"":fatherOccupation.trim();
        motherName=motherName==null?"":motherName.trim();
        motherPhone=motherPhone==null?"":motherPhone.trim();
        motherEmail=motherEmail==null?"":motherEmail.trim();
        motherOccupation=motherOccupation==null?"":motherOccupation.trim();
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
            
            String query="UPDATE students SET full_name = ?,email = ?,phone = ?,college = ?,course = ?,year = ?,current_semester = ?,bio = ?,github_url = ?,linkedin_url = ?,"
                    + "hobbies = ?,dob = ?,father_name = ?,father_phone = ?,father_email = ?,father_occupation = ?,mother_name = ?,mother_phone = ?,mother_email = ?,mother_occupation = ? WHERE user_id = "+userId;
            statement=connection.prepareStatement(query);
            setValue(statement,1,fullName);
            setValue(statement,2,email);
            setValue(statement,3,phone);
            setValue(statement,4,college);
            setValue(statement,5,course);
            setValue(statement,6,year);
            setValue(statement,7,currentSemester);
            setValue(statement,8,bio);
            setValue(statement,9,githubUrl);
            setValue(statement,10,linkedinUrl);
            setValue(statement,11,hobbies);
            setValue(statement,12,dob);
            setValue(statement,13,fatherName);
            setValue(statement,14,fatherPhone);
            setValue(statement,15,fatherEmail);
            setValue(statement,16,fatherOccupation);
            setValue(statement,17,motherName);
            setValue(statement,18,motherPhone);
            setValue(statement,19,motherEmail);
            setValue(statement,20,motherOccupation);

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

    public static void updateProfile(String fullName,String email,String phone,String college,String course,int year,int currentSemester,String bio,String githubUrl,String linkedinUrl) throws MissingFieldException,RoleException,SQLException,GeneralException{
        updateProfile(fullName, email, phone, college, course, year, currentSemester, bio, githubUrl, linkedinUrl, "", "", "", "", "", "", "", "", "", "");
    }


    // public static void updateProfile(String fullName,String email,String phone,String college,String course,int year,String bio,String githubUrl,String linkedinUrl) throws MissingFieldException,RoleException,SQLException,GeneralException{
    //     Role role=Session.getRole();
    //     int userId=Session.getId();
    //     fullName=fullName.trim();
    //     email=email.trim();
    //     if(fullName.length()==0){
    //         throw new MissingFieldException("fullName");
    //     }
    //     if(email.length()==0){
    //         throw new MissingFieldException("email");
    //     }
    //     if(role!=Role.STUDENT){
    //         throw new RoleException("Student");
    //     }
    //     Connection connection=null;
    //     PreparedStatement statement=null;
    //     ResultSet rs=null;
    //     try{
    //         connection=DBConnection.getConnection();
    //         statement=connection.prepareStatement("SELECT 1 FROM students WHERE user_id="+userId);
    //         rs=statement.executeQuery();
    //         if(!rs.next()){
    //             throw new GeneralException("User profile does not exist");
    //         }
            
    //         String query="UPDATE students SET full_name = ?,email = ?,phone = ?,college = ?,course = ?,year = ?,bio = ?,github_url = ?,linkedin_url = ? WHERE user_id = "+userId;
    //         statement=connection.prepareStatement(query);
    //         setValue(statement,1,fullName);
    //         setValue(statement,2,email);
    //         setValue(statement,3,phone);
    //         setValue(statement,4,college);
    //         setValue(statement,5,course);
    //         setValue(statement,6,year);
    //         setValue(statement,7,bio);
    //         setValue(statement,8,githubUrl);
    //         setValue(statement,9,linkedinUrl);

    //         statement.executeUpdate();
    //     }finally{
    //         if(connection!=null){
    //             connection.close();
    //         }
    //         if(statement!=null){
    //             statement.close();
    //         }
    //         if(rs!=null){
    //             rs.close();
    //         }
    //     }
    // }
    private static void setValue(PreparedStatement s,int index,int value) throws SQLException{
        if(value==-1){
            s.setNull(index,Types.INTEGER);
        }else{
            s.setInt(index,value);
        }
    }
    private static void setValue(PreparedStatement s,int index,String value) throws SQLException{
        if(value==null || value.length()==0){
            s.setNull(index,Types.VARCHAR);
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
