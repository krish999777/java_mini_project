package data;

public class EducationModel {
    public int id;
    public int studentId;
    public String school;
    public String board;
    public int startYear;
    public int endYear;
    public String grade;
    public EducationModel(int i,int s,String sch,String b,int sy,int ey,String g){
        id=i;
        studentId=s;
        school=sch;
        board=b;
        startYear=sy;
        endYear=ey;
        grade=g;
    }
    public String toString(){
        return "id:"+id+"\n"+"student_id:"+studentId+"\n"+"school:"+school+"\n"+"board:"+board+"\n"+"start_year:"+startYear+"\n"+"end_year:"+endYear+"\n"+"grade:"+grade+"\n";
    }
}
