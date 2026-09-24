package myExceptions;

public class GeneralException extends Exception{
    String message;
    public GeneralException(String m){
        message=m;
    }
    public String toString(){
        return message;
    }
}
