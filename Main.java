//javac -cp ".:/Users/krishshah/all coding stuff/java_mini_project/lib/postgresql-42.7.13.jar:/Users/krishshah/all coding stuff/java_mini_project" Main.java
//java -cp ".:/Users/krishshah/all coding stuff/java_mini_project/lib/postgresql-42.7.13.jar:/Users/krishshah/all coding stuff/java_mini_project" Main
import data.Role;
import myExceptions.LoginException;

class Main{
    public static void main(String args[]){
        try{
            Role r=Login.login("krish","krish123");
            System.out.println(r);
        }catch(LoginException e){
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }
        

    }
}