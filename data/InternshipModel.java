package data;

public class InternshipModel {
    public int id;
    public int studentId;
    public String company;
    public String role;
    public String description;
    public String startDate;
    public String endDate;
    public InternshipModel(int i,int s,String c,String r,String d,String sd,String ed){
        id=i;
        studentId=s;
        company=c;
        role=r;
        description=d;
        startDate=sd;
        endDate=ed;
    }
    public String toString(){
        return "id:"+id+"\n"+"student_id:"+studentId+"\n"+"company:"+company+"\n"+"role:"+role+"\n"+"description:"+description+"\n"+"start_date:"+startDate+"\n"+"end_date:"+endDate+"\n";
    }
}
