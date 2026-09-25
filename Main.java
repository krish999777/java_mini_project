//javac -cp ".:lib/postgresql-42.7.13.jar" Main.java
//java -cp ".:lib/postgresql-42.7.13.jar" Main
import myExceptions.LoginException;
// import data.StudentProfileModel;

class Main{
    public static void main(String args[]){
        try{
            Login.login("krish2","krish123");
            StudentProfile.updateProfile("krish","krish2@gmail.com","","SBMP","IT",-1,"Full stack dev with experience in ai engineering,cybersecurity,devops and iot","https://github.com/krish999777","https://www.linkedin.com/in/krish-shah09");
            // System.out.println(StudentProfile.getProfile());
        }catch(LoginException e){
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }
        

    }
}