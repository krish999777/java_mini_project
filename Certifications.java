import java.sql.*;
import session.Session;
import db.DBConnection;
import data.*;
import myExceptions.*;

public class Certifications {
    public static void addCertification(String name,String issuingOrganization,String credentialId,String credentialUrl) throws RoleException,SQLException,GeneralException,MissingFieldException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        name=name==null ? "" : name.trim();
        if(name.length()==0){
            throw new MissingFieldException("name");
        }
        issuingOrganization=issuingOrganization==null ? "" : issuingOrganization.trim();
        credentialId=credentialId==null ? "" : credentialId.trim();
        credentialUrl=credentialUrl==null ? "" : credentialUrl.trim();
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("INSERT INTO certifications (student_id,name,issuing_organization,credential_id,credential_url) VALUES (?,?,?,?,?)");
            statement.setInt(1,studentId);
            statement.setString(2,name);
            setValue(statement,3,issuingOrganization);
            setValue(statement,4,credentialId);
            setValue(statement,5,credentialUrl);
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


    public static void removeCertification(int id) throws RoleException,SQLException,GeneralException{
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
            statement=connection.prepareStatement("DELETE FROM certifications WHERE id=? AND student_id=?");
            statement.setInt(1,id);
            statement.setInt(2,studentId);
            int rows=statement.executeUpdate();
            if(rows==0){
                throw new GeneralException("Certification does not exist");
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


    public static void updateCertification(int id,String name,String issuingOrganization,String credentialId,String credentialUrl) throws RoleException,SQLException,GeneralException,MissingFieldException{
        Role role=Session.getRole();
        int userId=Session.getId();
        if(role!=Role.STUDENT){
            throw new RoleException("Student");
        }
        int studentId=StudentProfile.getStudentId(userId);
        if(studentId==-1){
            throw new GeneralException("Student profile does not exist");
        }
        name=name==null ? "" : name.trim();
        if(name.length()==0){
            throw new MissingFieldException("name");
        }
        issuingOrganization=issuingOrganization==null ? "" : issuingOrganization.trim();
        credentialId=credentialId==null ? "" : credentialId.trim();
        credentialUrl=credentialUrl==null ? "" : credentialUrl.trim();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try{
            connection=DBConnection.getConnection();
            statement=connection.prepareStatement("SELECT 1 FROM certifications WHERE id=? AND student_id=?");
            statement.setInt(1,id);
            statement.setInt(2,studentId);
            rs=statement.executeQuery();
            if(!rs.next()){
                throw new GeneralException("Certification does not exist");
            }
            statement=connection.prepareStatement("UPDATE certifications SET name = ?,issuing_organization = ?,credential_id = ?,credential_url = ? WHERE id = ? AND student_id = ?");
            setValue(statement,1,name);
            setValue(statement,2,issuingOrganization);
            setValue(statement,3,credentialId);
            setValue(statement,4,credentialUrl);
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


    public static CertificationModel[] getCertifications() throws RoleException,SQLException,GeneralException{
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
            rs=statement.executeQuery("SELECT * FROM certifications WHERE student_id="+studentId);
            int rowCount=rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            CertificationModel certifications[]=new CertificationModel[rowCount];
            int i=0;
            while(rs.next()){
                certifications[i]=new CertificationModel(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("issuing_organization"),
                    rs.getString("credential_id"),
                    rs.getString("credential_url")
                );
                i++;
            }
            return certifications;
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
