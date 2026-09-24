package data;

public class StudentProfileModel {
    int id;
    String fullName;
    String email;
    String phone;
    String college;
    String course;
    int year;
    String bio;
    String githubUrl;
    String linkedinUrl;
    public StudentProfileModel(int i,String f,String e,String p,String col,String cou,int y,String b,String g,String l){
        id=i;
        fullName=f;
        email=e;
        phone=p;
        college=col;
        course=cou;
        year=y;
        bio=b;
        githubUrl=g;
        linkedinUrl=l;
    }
    public String toString(){
        return "id"+id+"\n"+"full name"+fullName+"\n"+"email"+email+"\n"+"phone"+phone+"\n"+"college"+college+"\n"+"course"+course+"\n"+"year"+year+"\n"+"bio"+bio+"\n"+"githubUrl"+githubUrl+"\n"+"linkedinUrl"+linkedinUrl+"\n";
    }
}
