import java.sql.*;
import session.Session;
import db.DBConnection;
import data.*;
import myExceptions.*;

public class SemesterMarks {
    public static void addMarks(int semester,double marks) throws RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        int currentSemester=getCurrentSemester(userId);
        if(semester<1){
            throw new GeneralException("Semester must be greater than or equal to 1");
        }
        if(semester>=currentSemester){
            throw new GeneralException("Can only enter marks for completed semesters (1 to "+(currentSemester-1)+")");
        }
        if(marks<0){
            throw new GeneralException("Marks cannot be negative");
        }
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT 1 FROM semester_marks WHERE student_id=? AND semester=?");
            statement.setInt(1,studentId);
            statement.setInt(2,semester);
            rs=statement.executeQuery();
            if(rs.next()){
                throw new GeneralException("Marks for semester "+semester+" already exist");
            }
            statement.close();
            statement=connection.prepareStatement("INSERT INTO semester_marks (student_id,semester,marks) VALUES (?,?,?)");
            statement.setInt(1,studentId);
            statement.setInt(2,semester);
            statement.setDouble(3,marks);
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


    public static void updateMarks(int semester,double marks) throws RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        int currentSemester=getCurrentSemester(userId);
        if(semester<1){
            throw new GeneralException("Semester must be greater than or equal to 1");
        }
        if(semester>=currentSemester){
            throw new GeneralException("Can only enter marks for completed semesters (1 to "+(currentSemester-1)+")");
        }
        if(marks<0){
            throw new GeneralException("Marks cannot be negative");
        }
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("UPDATE semester_marks SET marks=? WHERE student_id=? AND semester=?");
            statement.setDouble(1,marks);
            statement.setInt(2,studentId);
            statement.setInt(3,semester);
            int rows=statement.executeUpdate();
            if(rows==0){
                throw new GeneralException("Marks for semester "+semester+" do not exist");
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


    public static void setMarks(int semester,double marks) throws RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        int currentSemester=getCurrentSemester(userId);
        if(semester<1){
            throw new GeneralException("Semester must be greater than or equal to 1");
        }
        if(semester>=currentSemester){
            throw new GeneralException("Can only enter marks for completed semesters (1 to "+(currentSemester-1)+")");
        }
        if(marks<0){
            throw new GeneralException("Marks cannot be negative");
        }
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("INSERT INTO semester_marks (student_id,semester,marks) VALUES (?,?,?) ON CONFLICT (student_id,semester) DO UPDATE SET marks=EXCLUDED.marks");
            statement.setInt(1,studentId);
            statement.setInt(2,semester);
            statement.setDouble(3,marks);
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


    public static void removeMarks(int semester) throws RoleException,SQLException,GeneralException{
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
            statement=connection.prepareStatement("DELETE FROM semester_marks WHERE student_id=? AND semester=?");
            statement.setInt(1,studentId);
            statement.setInt(2,semester);
            int rows=statement.executeUpdate();
            if(rows==0){
                throw new GeneralException("Marks for semester "+semester+" do not exist");
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


    public static SemesterMarkModel[] getSemesterMarks() throws RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        int currentSemester=getCurrentSemester(userId);
        int totalExpected=currentSemester-1;
        if(totalExpected<=0){
            return new SemesterMarkModel[0];
        }
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT id,semester,marks FROM semester_marks WHERE student_id=? ORDER BY semester ASC");
            statement.setInt(1,studentId);
            rs=statement.executeQuery();
            SemesterMarkModel result[]=new SemesterMarkModel[totalExpected];
            int ids[]=new int[totalExpected+1];
            double marksArr[]=new double[totalExpected+1];
            boolean exists[]=new boolean[totalExpected+1];
            while(rs.next()){
                int sem=rs.getInt("semester");
                if(sem>=1 && sem<=totalExpected){
                    ids[sem]=rs.getInt("id");
                    marksArr[sem]=rs.getDouble("marks");
                    exists[sem]=true;
                }
            }
            for(int s=1;s<=totalExpected;s++){
                if(exists[s]){
                    result[s-1]=new SemesterMarkModel(ids[s],studentId,s,marksArr[s]);
                }else{
                    result[s-1]=new SemesterMarkModel(-1,studentId,s,-1.0);
                }
            }
            return result;
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


    public static SemesterMarkModel[] getEnteredMarks() throws RoleException,SQLException,GeneralException{
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
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT id,student_id,semester,marks FROM semester_marks WHERE student_id=? ORDER BY semester ASC",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1,studentId);
            rs=statement.executeQuery();
            int rowCount=rs.last()?rs.getRow():0;
            rs.beforeFirst();
            SemesterMarkModel result[]=new SemesterMarkModel[rowCount];
            int i=0;
            while(rs.next()){
                result[i]=new SemesterMarkModel(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getInt("semester"),
                    rs.getDouble("marks")
                );
                i++;
            }
            return result;
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


    public static int[] getMissingSemesters() throws RoleException,SQLException,GeneralException{
        SemesterMarkModel marks[]=getSemesterMarks();
        int missingCount=0;
        for(int i=0;i<marks.length;i++){
            if(marks[i].marks==-1.0){
                missingCount++;
            }
        }
        int missing[]=new int[missingCount];
        int cur=0;
        for(int i=0;i<marks.length;i++){
            if(marks[i].marks==-1.0){
                missing[cur]=marks[i].semester;
                cur++;
            }
        }
        return missing;
    }


    public static int getCurrentSemester() throws RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        return getCurrentSemester(userId);
    }


    public static void setCurrentSemester(int semester) throws RoleException,SQLException,GeneralException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        if(semester<1 || semester>10){
            throw new GeneralException("Semester must be between 1 and 10");
        }
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("UPDATE students SET current_semester=? WHERE user_id=?");
            statement.setInt(1,semester);
            statement.setInt(2,userId);
            int rows=statement.executeUpdate();
            if(rows==0){
                throw new GeneralException("Student profile does not exist");
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


    public static double getAverageMarks() throws RoleException,SQLException,GeneralException{
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
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT AVG(marks) as avg_marks FROM semester_marks WHERE student_id=?");
            statement.setInt(1,studentId);
            rs=statement.executeQuery();
            if(rs.next()){
                return rs.getDouble("avg_marks");
            }
            return -1.0;
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


    private static int getCurrentSemester(int userId) throws SQLException,GeneralException{
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT current_semester FROM students WHERE user_id=?");
            statement.setInt(1,userId);
            rs=statement.executeQuery();
            if(!rs.next()){
                throw new GeneralException("Student profile does not exist");
            }
            int sem=rs.getInt("current_semester");
            return sem<=0?1:sem;
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
