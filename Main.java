//javac -cp ".:/Users/krishshah/all coding stuff/java_mini_project/lib/postgresql-42.7.13.jar:/Users/krishshah/all coding stuff/java_mini_project" Main.java
//java -cp ".:/Users/krishshah/all coding stuff/java_mini_project/lib/postgresql-42.7.13.jar:/Users/krishshah/all coding stuff/java_mini_project" Main
import myExceptions.LoginException;
import data.StudentProfileModel;

class Main{
    public static void main(String args[]){
        try{
            Login.login("krish2","krish123");
            StudentProfileModel s=StudentProfile.getProfile();
            System.out.println(s);
        }catch(LoginException e){
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }
        

    }
}