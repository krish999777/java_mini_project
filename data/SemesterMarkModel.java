package data;

public class SemesterMarkModel {
    public int id;
    public int studentId;
    public int semester;
    public double marks;
    public SemesterMarkModel(int i,int s,int sem,double m){
        id=i;
        studentId=s;
        semester=sem;
        marks=m;
    }
    public String toString(){
        return "id:"+id+"\n"+"student_id:"+studentId+"\n"+"semester:"+semester+"\n"+"marks:"+marks+"\n";
    }
}
