package data;

public class EducationModel {
    public int id;
    public int studentId;
    public String institution;
    public String degree;
    public int startYear;
    public int endYear;
    public String grade;
    public EducationModel(int i,int s,String inst,String d,int sy,int ey,String g){
        id=i;
        studentId=s;
        institution=inst;
        degree=d;
        startYear=sy;
        endYear=ey;
        grade=g;
    }
    public String toString(){
        return "id:"+id+"\n"+"student_id:"+studentId+"\n"+"institution:"+institution+"\n"+"degree:"+degree+"\n"+"start_year:"+startYear+"\n"+"end_year:"+endYear+"\n"+"grade:"+grade+"\n";
    }
}
