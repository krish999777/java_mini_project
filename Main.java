//javac -cp ".:lib/postgresql-42.7.13.jar" Main.java
//java -cp ".:lib/postgresql-42.7.13.jar" Main
// import myExceptions.LoginException;
// import data.StudentProfileModel;

class Main{
    public static void main(String args[]){
        try{
            Login.login("krish2","krish123");
            // Skills.addSkill("MongoDB");
            // StudentProfile.updateProfile("krish","krish2@gmail.com","","SBMP","IT",-1,"Full stack dev with experience in ai engineering,cybersecurity,devops and iot","https://github.com/krish999777","https://www.linkedin.com/in/krish-shah09");
            // System.out.println(StudentProfile.getProfile());
            String s[]=Skills.getSkills();
            for(int i=0;i<s.length;i++){
                System.out.println(s[i]);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        

    }
}