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
}
