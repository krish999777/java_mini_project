package data;

public class StudentProfileModel {
    public int id;
    public String fullName;
    public String email;
    public String phone;
    public String college;
    public String course;
    public int year;
    public String bio;
    public String githubUrl;
    public String linkedinUrl;
    public String skills[];
    public ProjectModel projects[];
    public AchievementModel achievements[];
    public CertificationModel certifications[];
    public InternshipModel internships[];
    public EducationModel education[];
    public StudentProfileModel(int i,String f,String e,String p,String col,String cou,int y,String b,String g,String l,String s[],ProjectModel pr[],AchievementModel a[],CertificationModel c[],InternshipModel in[],EducationModel ed[]){
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
        skills=s;
        projects=pr;
        achievements=a;
        certifications=c;
        internships=in;
        education=ed;
    }
    public StudentProfileModel(int i,String f,String e,String p,String col,String cou,int y,String b,String g,String l){
        this(i,f,e,p,col,cou,y,b,g,l,new String[0],new ProjectModel[0],new AchievementModel[0],new CertificationModel[0],new InternshipModel[0],new EducationModel[0]);
    }
    public String toString(){
        String str="id:"+id+"\n"+"full name:"+fullName+"\n"+"email:"+email+"\n"+"phone:"+phone+"\n"+"college:"+college+"\n"+"course:"+course+"\n"+"year:"+year+"\n"+"bio:"+bio+"\n"+"githubUrl:"+githubUrl+"\n"+"linkedinUrl:"+linkedinUrl+"\n";
        str+="--- skills ---\n";
        if(skills!=null){
            for(int i=0;i<skills.length;i++){
                str+=skills[i]+"\n";
            }
        }
        str+="--- projects ---\n";
        if(projects!=null){
            for(int i=0;i<projects.length;i++){
                str+=projects[i].toString()+"\n";
            }
        }
        str+="--- achievements ---\n";
        if(achievements!=null){
            for(int i=0;i<achievements.length;i++){
                str+=achievements[i].toString()+"\n";
            }
        }
        str+="--- certifications ---\n";
        if(certifications!=null){
            for(int i=0;i<certifications.length;i++){
                str+=certifications[i].toString()+"\n";
            }
        }
        str+="--- internships ---\n";
        if(internships!=null){
            for(int i=0;i<internships.length;i++){
                str+=internships[i].toString()+"\n";
            }
        }
        str+="--- education ---\n";
        if(education!=null){
            for(int i=0;i<education.length;i++){
                str+=education[i].toString()+"\n";
            }
        }
        return str;
    }
}
