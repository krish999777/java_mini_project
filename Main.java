//javac -cp ".:/Users/krishshah/all coding stuff/java_mini_project/lib/postgresql-42.7.13.jar:/Users/krishshah/all coding stuff/java_mini_project" Main.java
//java -cp ".:/Users/krishshah/all coding stuff/java_mini_project/lib/postgresql-42.7.13.jar:/Users/krishshah/all coding stuff/java_mini_project" Main
import myExceptions.LoginException;
// import data.StudentProfileModel;

class Main{
    public static void main(String args[]){
        try{
            Login.login("krish2","krish123");
            StudentProfile.updateProfile("krish","krish2@gmail.com","9988776655","SBMP","IT",2,"Full stack dev with experience in ai engineering,cybersecurity,devops and iot","https://github.com/krish999777","https://www.linkedin.com/in/krish-shah09");
        }catch(LoginException e){
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }
        

    }
}