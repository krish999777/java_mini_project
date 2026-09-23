//javac -cp ".:/Users/krishshah/all coding stuff/java_mini_project/lib/postgresql-42.7.13.jar:/Users/krishshah/all coding stuff/java_mini_project" Main.java
//java -cp ".:/Users/krishshah/all coding stuff/java_mini_project/lib/postgresql-42.7.13.jar:/Users/krishshah/all coding stuff/java_mini_project" Main
import myExceptions.LoginException;

class Main{
    public static void main(String args[]){
        try{
            Login.login("krish","krish123");
            StudentProfile.createProfile("krish", "krish@gmail.com", "1234567890", "sbmp", "", 1, "", "", "");
        }catch(LoginException e){
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }
        

    }
}