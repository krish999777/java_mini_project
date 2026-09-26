package data;

public class AchievementModel {
    public int id;
    public int studentId;
    public String title;
    public String description;
    public AchievementModel(int i,int s,String t,String d){
        id=i;
        studentId=s;
        title=t;
        description=d;
    }
    public String toString(){
        return "id:"+id+"\n"+"student_id:"+studentId+"\n"+"title:"+title+"\n"+"description:"+description+"\n";
    }
}
