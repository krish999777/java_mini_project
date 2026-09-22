package myExceptions;
public class LoginException extends Exception{
    private int code;//1 for does not exist, 2 for wrong password and 3 for not active
    LoginException(int c){
        code=c;
    }
    public String toString(){
        if(code==1){
            return "Username does not exist";
        }else if(code==2){
            return "Incorrect password";
        }else{
            return "Account not active, contact admin";
        }
    }
}
