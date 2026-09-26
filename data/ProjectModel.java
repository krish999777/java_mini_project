package data;

public class ProjectModel {
    public int id;
    public int studentId;
    public String title;
    public String description;
    public String githubUrl;
    public String liveUrl;
    public ProjectModel(int i,int s,String t,String d,String g,String l){
        id=i;
        studentId=s;
        title=t;
        description=d;
        githubUrl=g;
        liveUrl=l;
    }
    public String toString(){
        return "id:"+id+"\n"+"student_id:"+studentId+"\n"+"title:"+title+"\n"+"description:"+description+"\n"+"githubUrl:"+githubUrl+"\n"+"liveUrl:"+liveUrl+"\n";
    }
}
