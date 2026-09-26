package data;

public class CertificationModel {
    public int id;
    public int studentId;
    public String name;
    public String issuingOrganization;
    public String credentialId;
    public String credentialUrl;
    public CertificationModel(int i,int s,String n,String o,String ci,String cu){
        id=i;
        studentId=s;
        name=n;
        issuingOrganization=o;
        credentialId=ci;
        credentialUrl=cu;
    }
    public String toString(){
        return "id:"+id+"\n"+"student_id:"+studentId+"\n"+"name:"+name+"\n"+"issuing_organization:"+issuingOrganization+"\n"+"credential_id:"+credentialId+"\n"+"credential_url:"+credentialUrl+"\n";
    }
}
